package jeonghoj.boardproject.repository;

import jeonghoj.boardproject.domain.Board;
import jeonghoj.boardproject.domain.projection.GeneralBoardTitleOnly;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GeneralBoardRepository extends JpaRepository<Board, Long> {
    Page<GeneralBoardTitleOnly> findAllBoardTitleListOnlyProjectionByOrderByCreatedDateDesc(Pageable pageable);
}
