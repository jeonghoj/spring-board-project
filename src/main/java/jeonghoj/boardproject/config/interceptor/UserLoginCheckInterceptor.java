package jeonghoj.boardproject.config.interceptor;

import jeonghoj.boardproject.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import sun.applet.Main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class UserLoginCheckInterceptor implements HandlerInterceptor {

    private Logger logger = LoggerFactory.getLogger(UserLoginCheckInterceptor.class);

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object handler,
                           ModelAndView modelAndView){
//        logger.info("========Interceptor calls=========");
        HttpSession session = request.getSession(false);
        if(session != null){
            User user = (User) session.getAttribute("user");
            if (user != null){
                if (modelAndView != null) {
                    modelAndView.getModelMap().addAttribute("user", user);
                }
            }
        }
    }

}
