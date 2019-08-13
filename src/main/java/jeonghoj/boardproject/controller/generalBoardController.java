package jeonghoj.boardproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class generalBoardController {
    @GetMapping("/general")
    public String general(){
        return "boardGeneral";
    }
}
