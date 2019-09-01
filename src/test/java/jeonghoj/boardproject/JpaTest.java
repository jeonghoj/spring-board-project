package jeonghoj.boardproject;

import jeonghoj.boardproject.domain.Board;
import jeonghoj.boardproject.domain.User;
import jeonghoj.boardproject.domain.enums.BoardType;
import jeonghoj.boardproject.repository.GeneralBoardRepository;
import jeonghoj.boardproject.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class JpaTest {
    private final String boardTestTitle = "테스트";
    private final String email = "test@gmail.com";

    @Autowired
    UserRepository userRepository;

    @Autowired
    GeneralBoardRepository generalBoardRepository;

    @Before
    public void init() {
        User user = userRepository.save(User.builder()
                .name("havi")
                .password("test")
                .email(email)
                .createdDate(LocalDateTime.now())
                .build());

        generalBoardRepository.save(Board.builder()
                .title(boardTestTitle)
                .content("컨텐츠")
                .boardType(BoardType.general)
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
//                .updatedDate(LocalDateTime.now()).build());
                .user(user).build());

        generalBoardRepository.save(Board.builder()
                .title(boardTestTitle)
                .content("컨텐츠")
                .boardType(BoardType.general)
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now()).build());
//                .user().build());
    }

    @Test
    public void 제대로_생성_됐는지_테스트() {
//        User user = userRepository.findByEmail(email);
//        assertThat(user.getName(), is("havi"));
//        assertThat(user.getPassword(), is("test"));
//        assertThat(user.getEmail(), is(email));

//
        Board board = generalBoardRepository.findAll().get(0);
        System.out.println(board.getUser().getIdx());
        System.out.println(board.getUser().getName());
        assertThat(board.getTitle(), is(boardTestTitle));
        assertThat(board.getContent(), is("컨텐츠"));
        assertThat(board.getBoardType(), is(BoardType.general));
    }
}
