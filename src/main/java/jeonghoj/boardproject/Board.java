package jeonghoj.boardproject;

import java.time.LocalDateTime;

public class Board {
    private int boardId;
    private String boardTitle;
    private String boardContent;
    private LocalDateTime boardDatetime;
    private int boardMemberId;
    private String boardMemberName;

    public Board(String boardTitle, String boardContent, LocalDateTime boardDatetime, int boardMemberId, String boardMemberName) {
        this.boardTitle = boardTitle;
        this.boardContent = boardContent;
        this.boardDatetime = boardDatetime;
        this.boardMemberId = boardMemberId;
        this.boardMemberName = boardMemberName;
    }

    public void setBoardId(int boardId) {
        this.boardId = boardId;
    }

    public String getBoardTitle() {
        return boardTitle;
    }

    public String getBoardContent() {
        return boardContent;
    }

    public LocalDateTime getBoardDatetime() {
        return boardDatetime;
    }

    public int getBoardMemberId() {
        return boardMemberId;
    }

    public String getBoardMemberName() {
        return boardMemberName;
    }

}
