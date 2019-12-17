package jeonghoj.boardproject.controller;

import jeonghoj.boardproject.domain.Board;
import jeonghoj.boardproject.domain.User;
import jeonghoj.boardproject.domain.dto.BoardDto;
import jeonghoj.boardproject.domain.dto.BoardUpdateDto;
import jeonghoj.boardproject.domain.projection.GeneralBoardTitleOnly;
import jeonghoj.boardproject.service.GeneralBoardService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class GeneralBoardController {
    private final GeneralBoardService generalBoardService;

    public GeneralBoardController(GeneralBoardService generalBoardService) {
        this.generalBoardService = generalBoardService;
    }

    @GetMapping({"/", "/general"})
    public String getBoards(@PageableDefault Pageable pageable, Model model) {
        Page<GeneralBoardTitleOnly> boards = generalBoardService.findBoardList(pageable);
        if (!boards.isEmpty())
            model.addAttribute("boards", boards);
        return "board/boardGeneral";
    }

    @GetMapping("/general/{idx}")
    public String getBoardDetail(@PathVariable Long idx, Model model, Principal principal) {
        // TODO board 데이터가 없을때의 예외처리
        Board board = generalBoardService.getBoardDetail(idx);
        if (board != null) {
            model.addAttribute("board",board);
            if(principal != null)
                model.addAttribute("author", principal.getName().equals(board.getUser().getUsername()));
            return "board/boardGeneralDetail";
        } else return "errors/404"; // FIXME 404로 redirect
    }

    @GetMapping("/general/create")
    public String createBoardPage(Model model){
        model.addAttribute("board",new Board());
        return "board/boardGeneralWrite";
    }

    // TODO 입력값 검증
    @PostMapping("/general/create")
    public String writeBoard(@ModelAttribute(value = "board") BoardDto boardDto, Authentication authentication){
        User user = (User) authentication.getPrincipal();
        Board board = generalBoardService.createBoard(boardDto,user);
        return "redirect:/general/"+board.getIdx();
    }

    @GetMapping("/general/update/{idx}")
    public String getUpdateBoardInfo(@PathVariable Long idx, Model model, Authentication authentication){
        User user = (User) authentication.getPrincipal();
        Board board = generalBoardService.updateBoardInfo(idx,user);
        if (board == null) {
            // FIXME 접근 권한이 없습니다 페이지로 변경
            return "redirect:/general";
        }else {
            model.addAttribute("board", board);
            model.addAttribute("updateBoard", new BoardUpdateDto());
            return "board/boardGeneralUpdate";
        }
    }

    @PostMapping("/general/update")
    public String updateBoard(@ModelAttribute(value = "updateBoard") BoardUpdateDto boardUpdateDto, Authentication authentication){
        User user = (User) authentication.getPrincipal();
        Board updatedBoard = generalBoardService.updateBoard(boardUpdateDto,user);
        if (updatedBoard == null) {
            // FIXME 접근 권한이 없습니다 페이지로 변경
            return "redirect:/general";
        }
        return "redirect:/general/"+updatedBoard.getIdx();
    }

    @GetMapping("/general/delete/{idx}")
    public String deleteBoard(@PathVariable Long idx){
        generalBoardService.deleteBoard(idx);
        return "redirect:/general";
    }

}
