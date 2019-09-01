package jeonghoj.boardproject.config;

import jeonghoj.boardproject.config.interceptor.UserLoginCheckInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    private UserLoginCheckInterceptor userLoginCheckInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userLoginCheckInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/admin/**","/static/**");
    }
}
