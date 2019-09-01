package jeonghoj.boardproject.config.resolver;

import jeonghoj.boardproject.annotation.SocialUser;
import jeonghoj.boardproject.domain.User;

import jeonghoj.boardproject.domain.enums.SocialType;
import jeonghoj.boardproject.repository.UserRepository;

import org.springframework.core.MethodParameter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.Map;

import static jeonghoj.boardproject.domain.enums.SocialType.FACEBOOK;
import static jeonghoj.boardproject.domain.enums.SocialType.GOOGLE;

/*
Strategy Pattern 의 일종

1. 컨트롤러 메서드에서
2. 특정 조건에 해당하는 파라미터가 있으면
3. 생성한 로직을 처리한 후
4. 해당 파라미터에 데이터 바인딩

해주는 전략 인터페이스.
*/

@Component
public class UserArgumentResolver implements HandlerMethodArgumentResolver {

    private UserRepository userRepository;

    public UserArgumentResolver(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //    해당하는 파라미터를 지원할 지 여부 반환
//    true 를 반환하면 수행
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        // `@SocialUser 에노테이션이 붙어있고`, `타입이 User 객체`여야함.
        return
                (parameter.getParameterAnnotation(SocialUser.class) != null) &&
                (parameter.getParameterType().equals(User.class));
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        // 1. 세션에서 User 객체가 존재하는지 확인
        // 세션값을 가져온다
        HttpSession session = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession();
        User user = (User) session.getAttribute("user");
        return getUser(user,session);
        //temp
//        return null;
    }

    // 세션에 User 객체를 바인딩. DB에 없으면 저장하고 정보를 세션에 User 객체를 바인딩
    private User getUser(User user, HttpSession session) {
        if (user == null) {
            try {
                OAuth2AuthenticationToken authentication =
                        (OAuth2AuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
                Map<String, Object> map = authentication.getPrincipal().getAttributes();
                User convertUser = convertUser(authentication.getAuthorizedClientRegistrationId(), map);
                // 저장된 유저가 아니면 저장을한다
                user = userRepository.findByEmail(convertUser.getEmail());
                if (user == null) {
                    user = userRepository.save(convertUser);
                }
                //  Role 설정
                setRoleIfNotSame(user,authentication,map);
                session.setAttribute("user",user);
            } catch (ClassCastException e) {
                return user;
            }
        }
        return user;
    }

    // 소셜 서비스에 따라 다른 방법으로로 User 객체 build
    private User convertUser(String authority, Map<String, Object> map) {
        if(FACEBOOK.getValue().equals(authority)) return getModernUser(FACEBOOK,map);
        if(GOOGLE.getValue().equals(authority)) return getModernUser(GOOGLE,map);
        return null;
    }

    private User getModernUser(SocialType socialType, Map<String, Object> map) {
        return User.builder()
                .name(String.valueOf(map.get("name")))
                .email(String.valueOf(map.get("email")))
                .principal(String.valueOf(map.get("id")))
                .socialType(socialType)
                .createdDate(LocalDateTime.now())
                .build();
    }

    private void setRoleIfNotSame(User user, OAuth2AuthenticationToken authentication, Map<String, Object> map) {
//        인증된 auth가 권한을 갖고있는지 체크
//        저장된 User 권한이 없으면 SecurityContextHolder 로 해당 소셜 미디어 타입으로 권한을 저장.
        if(!authentication
                .getAuthorities()
                .contains(new SimpleGrantedAuthority(user.getSocialType().getRoleType()))) {
            SecurityContextHolder
                    .getContext()
                    .setAuthentication(new UsernamePasswordAuthenticationToken(
                            map,"N/A",AuthorityUtils.createAuthorityList(user.getSocialType().getRoleType())));
        }
    }
}
