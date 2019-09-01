package jeonghoj.boardproject.domain.enums;

public enum BoardType {
    notice("공지사항"),
    general("게시판"),
    anonymous("익명게시판");

    private String value;

    BoardType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

}
