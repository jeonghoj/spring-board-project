package jeonghoj.boardproject.controller;

import jeonghoj.boardproject.annotation.SocialUser;
import jeonghoj.boardproject.domain.Board;
import jeonghoj.boardproject.domain.User;
import jeonghoj.boardproject.domain.dto.BoardDto;
import jeonghoj.boardproject.domain.dto.BoardUpdateDto;
import jeonghoj.boardproject.repository.GeneralBoardRepository;
import jeonghoj.boardproject.service.GeneralBoardService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class GeneralBoardController {
    private final GeneralBoardService generalBoardService;

    public GeneralBoardController(GeneralBoardService generalBoardService, GeneralBoardRepository generalBoardRepository) {
        this.generalBoardService = generalBoardService;
    }

//    @GetMapping("/test/save")
//    public String addBoardTest() {
//        Board board = Board.builder()
//                .title("test")
//                .content("테스트")
//                .boardType(BoardType.general)
//                .createdDate(LocalDateTime.now())
//                .updatedDate(LocalDateTime.now()).build();
//        generalBoardRepository.save(board);
//        return "saved";
//    }

    @GetMapping({"/", "/general"})
    public String getBoards(@PageableDefault Pageable pageable, Model model) {
        model.addAttribute("boards", generalBoardService.findBoardList(pageable));
        return "board/boardGeneral";
    }

    @GetMapping("/general/{idx}")
    public String getBoardDetail(@PathVariable Long idx, Model model, HttpSession session) {
        // TODO cast string to long
        User currentUser = (User) session.getAttribute("user");
        // TODO board 데이터가 없을때의 예외처리
        Board board = generalBoardService.getBoardDetail(idx);
        System.out.println(currentUser != null);
        if (currentUser != null) {
            System.out.println(currentUser.getIdx());
        }
        if (board != null) {
            model.addAttribute("board",board);
            // 작성자인지 확인
            if(currentUser != null)
                model.addAttribute("author", currentUser.getIdx().equals(board.getUser().getIdx()));
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
    public String writeBoard(@ModelAttribute(value = "board") BoardDto boardDto, @SocialUser User user){
        System.out.println(boardDto.getTitle());
        Board board = generalBoardService.createBoard(boardDto, user);
        System.out.println(board.getIdx());
        return "redirect:/general/"+board.getIdx();
    }

    @GetMapping("/general/update/{idx}")
    public String getUpdateBoardInfo(@PathVariable Long idx, @SocialUser User user, Model model){
        Board board=generalBoardService.updateBoardInfo(idx,user);
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
    public String updateBoard(@ModelAttribute(value = "updateBoard") BoardUpdateDto boardUpdateDto, @SocialUser User user){
        Board updatedBoard = generalBoardService.updateBoard(boardUpdateDto, user);
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
