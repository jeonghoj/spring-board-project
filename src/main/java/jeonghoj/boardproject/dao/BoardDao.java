package jeonghoj.boardproject.dao;

import jeonghoj.boardproject.Board;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class BoardDao implements IBoardDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public BoardDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Board> selectBoard(int pageNum){
        int pageStart = (pageNum-1)*10;
        List<Board> boards = jdbcTemplate.query(
                "SELECT ID, TITLE, DATETIME, MEMBER_ID, MEMBER_NAME, HIT FROM BOARD LIMIT ? OFFSET ?",
                (resultSet,i) -> {
                    Board board = new Board(
                            resultSet.getInt("ID"),
                            resultSet.getString("TITLE"),
                            resultSet.getTimestamp("DATETIME").toLocalDateTime(),
                            resultSet.getInt("MEMBER_ID"),
                            resultSet.getString("MEMBER_NAME"),
                            resultSet.getInt("HIT"));
                    return board;
                },10,pageStart);
        return boards;
    }

    @Override
    public List<Board> selectTitle(int pageNum) {
        return null;
    }

    @Override
    public Board selectContent(int boardId) {
        return null;
    }

    @Override
    public int insert(Board board) {
        return 0;
    }

    @Override
    public void update(Board board) {

    }

    @Override
    public void delete(Board board) {

    }

//    TODO selectBoardDetail

}
