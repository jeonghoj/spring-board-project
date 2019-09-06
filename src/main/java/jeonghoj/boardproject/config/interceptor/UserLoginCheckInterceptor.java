package jeonghoj.boardproject.config.interceptor;

import jeonghoj.boardproject.domain.User;
import jeonghoj.boardproject.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Component
public class UserLoginCheckInterceptor implements HandlerInterceptor {

    @Autowired
    private UserRepository userRepository;

    private Logger logger = LoggerFactory.getLogger(UserLoginCheckInterceptor.class);

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object handler,
                           ModelAndView modelAndView){

        logger.info("========Interceptor calls=========");
        if(request.getUserPrincipal() != null){
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
            if(user == null){
                User authUser = userRepository.findByUsername(request.getUserPrincipal().getName());
                session.setAttribute("user",authUser);
                if(modelAndView != null){
                    modelAndView.getModelMap().addAttribute("user", authUser);
                }
            }else {
                if(modelAndView != null){
                    modelAndView.getModelMap().addAttribute("user", user);
                }
            }

        }


    }

}
