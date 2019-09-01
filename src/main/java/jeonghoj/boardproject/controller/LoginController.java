package jeonghoj.boardproject.controller;

import jeonghoj.boardproject.annotation.SocialUser;
import jeonghoj.boardproject.domain.User;
import jeonghoj.boardproject.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class LoginController {
    private final UserRepository userRepository;

    public LoginController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @GetMapping("/login")
    public String getLoginPage(Model model) {
        if(model.containsAttribute("user")){
            return "redirect:/loginSuccess";
        }
        return "member/loginForm";
    }

    @GetMapping("/loginSuccess")
    public String loginComplete(@SocialUser User user) {
        return "redirect:/general";
    }

    @GetMapping("/facebook")
    public String facebook(){
        return "member/loginForm";
    }

}
