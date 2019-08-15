package jeonghoj.boardproject.dao;

import jeonghoj.boardproject.Board;

import java.util.List;

public interface IBoardDao {

    public List<Board> selectTitle(int pageNum);

    public Board selectContent(int boardId);

    public int insert(Board board);

    public void update(Board board);

//    TODO 비밀번호로 글 지우게끔 구현
    public void delete(Board board);



}
