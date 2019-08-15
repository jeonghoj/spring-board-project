package jeonghoj.boardproject;

import java.time.LocalDateTime;

public class Board {
    private int id;
    private String title;
    private String content;
    private LocalDateTime datetime;
    private int memberId;
    private String memberName;
    private int hit;

//  Get Board Detail

    public Board(int id, String title, String content, LocalDateTime datetime, int memberId, String memberName, int hit) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.datetime = datetime;
        this.memberId = memberId;
        this.memberName = memberName;
        this.hit = hit;
    }

//  Get Board Title Only

    public Board(int id, String title, LocalDateTime datetime, int memberId, String memberName, int hit) {
        this.id = id;
        this.title = title;
        this.datetime = datetime;
        this.memberId = memberId;
        this.memberName = memberName;
        this.hit = hit;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getDatetime() {
        return datetime;
    }

    public int getMemberId() {
        return memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public int getHit() {
        return hit;
    }
}
