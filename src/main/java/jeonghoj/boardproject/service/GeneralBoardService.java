package jeonghoj.boardproject.service;

import jeonghoj.boardproject.domain.Board;
import jeonghoj.boardproject.domain.User;
import jeonghoj.boardproject.domain.dto.BoardDto;
import jeonghoj.boardproject.domain.dto.BoardUpdateDto;
import jeonghoj.boardproject.domain.enums.BoardType;
import jeonghoj.boardproject.domain.projection.GeneralBoardTitleOnly;
import jeonghoj.boardproject.repository.GeneralBoardRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class GeneralBoardService {
    private final GeneralBoardRepository generalBoardRepository;

    public GeneralBoardService(GeneralBoardRepository generalBoardRepository) {
        this.generalBoardRepository = generalBoardRepository;
    }

    public Page<GeneralBoardTitleOnly> findBoardList(Pageable pageable) {
        pageable = PageRequest.of(pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() -1, pageable.getPageSize());
        return generalBoardRepository.findAllBoardTitleListOnlyProjectionByOrderByCreatedDateDesc(pageable);
    }

    public Board getBoardDetail(Long id) {
        // findById 는 Optional<T> 로 받는다
        Optional<Board> board = generalBoardRepository.findById(id);
        return board.orElse(null);
    }

    public Board createBoard(BoardDto boardDto, User user) {
        Board board = Board.builder()
                .title(boardDto.getTitle())
                .content(boardDto.getContent())
                .createdDate(LocalDateTime.now())
                .boardType(BoardType.general)
                .user(user)
                .build();
        return generalBoardRepository.save(board);
    }

    public void deleteBoard(Long idx) {
        generalBoardRepository.deleteById(idx);
    }

    public Board updateBoardInfo(Long idx, User user) {
        Board board = generalBoardRepository.getOne(idx);
        if(!board.getUser().getUsername().equals(user.getUsername())){
            return null;
        }
        return board;
    }

    public Board updateBoard(BoardUpdateDto boardUpdateDto, User user) {
        Board updateBoard = generalBoardRepository.getOne(boardUpdateDto.getIdx());
        if(!user.getUsername().equals(updateBoard.getUser().getUsername())){
            return null;
        }
        updateBoard.update(boardUpdateDto);
        return generalBoardRepository.save(updateBoard);
    }
}
