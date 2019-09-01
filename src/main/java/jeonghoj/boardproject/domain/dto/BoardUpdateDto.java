package jeonghoj.boardproject.domain.dto;

import jeonghoj.boardproject.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BoardUpdateDto {
    Long idx;
    String title;
    String content;
    User user;
}
