package jeonghoj.boardproject.controller;

import jeonghoj.boardproject.Board;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class generalBoardController {

    private JdbcTemplate jdbcTemplate;

    @GetMapping("/general")
    public String general(Model model){
        List<Board> results = jdbcTemplate.query(
                "select * from BOARD",
                (resultSet, i) -> {
                    Board board =new Board(
                            resultSet.getString("TITLE"),
                            resultSet.getString("CONTENT"),
                            resultSet.getTimestamp("DATETIME").toLocalDateTime(),
                            resultSet.getInt("MEMBER_ID"),
                            resultSet.getString("MEMBER_NAME"));
                    board.setBoardId(resultSet.getInt("ID"));
                    return board;
                }
        );

        model.addAttribute("boards",results);
        return "boardGeneral";
    }

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
