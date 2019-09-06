package jeonghoj.boardproject.controller;

import jeonghoj.boardproject.domain.dto.RegisterRequestDto;
import jeonghoj.boardproject.exception.DuplicateUserException;
import jeonghoj.boardproject.service.UserService;
import jeonghoj.boardproject.validator.RegisterRequestValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.security.Principal;


@Controller
public class LoginController {

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "user/loginForm";
    }

    @GetMapping("/register")
    public String userRegisterPage(@ModelAttribute(value = "RegisterRequest") RegisterRequestDto registerRequestDto,Model model) {
        return "user/register";

    }

    @PostMapping("/register")
    public String userRegister(@ModelAttribute(value = "RegisterRequest") RegisterRequestDto registerRequestDto,BindingResult result){
        new RegisterRequestValidator().validate(registerRequestDto,result);
        if(result.hasErrors()){
            return "user/register";
        }
        try {
            userService.registerUser(registerRequestDto);
            return "user/loginForm";
        } catch (DuplicateUserException ex) {
            result.rejectValue("username","duplicateUser","이미 있는 유저입니다.");
            return "user/register";
        }
    }

    @GetMapping("/test")
    public String userLogin(Principal principal){
        System.out.println(principal.toString());
        return "board/boardGeneral";
    }

//    @GetMapping("/loginSuccess")
//    public String loginComplete(@SocialUser User user) {
//        return "redirect:/general";
//    }

}
