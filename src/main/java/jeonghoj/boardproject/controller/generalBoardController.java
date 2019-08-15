package jeonghoj.boardproject.controller;

import jeonghoj.boardproject.Board;
import jeonghoj.boardproject.dao.BoardDao;
import jeonghoj.boardproject.dao.MemberDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class generalBoardController {

    private final BoardDao boardDao;

    @Autowired
    public generalBoardController(BoardDao boardDao) {
        this.boardDao = boardDao;
    }

    @GetMapping("/general")
    public String general(@RequestParam(defaultValue = "1") int pageNum, Model model){
        List<Board> results = boardDao.selectBoard(pageNum);
        model.addAttribute("boards",results);
        return "boardGeneral";
    }

}
