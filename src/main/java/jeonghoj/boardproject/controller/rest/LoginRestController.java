package jeonghoj.boardproject.controller.rest;

import jeonghoj.boardproject.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginRestController {

    private final UserService userService;

    public LoginRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login/checkemail")
    public boolean checkEmail(@RequestParam String email){
        return userService.checkUserEmailAvailable(email);
    }

}
