package jeonghoj.boardproject.domain.projection;

import java.time.LocalDateTime;

public interface GeneralBoardTitleOnly {
    Long getIdx();
    String getTitle();
    LocalDateTime getCreatedDate();
    UserInfo getUser();

    interface UserInfo {
        Long getIdx();
        String getName();
    }
}



