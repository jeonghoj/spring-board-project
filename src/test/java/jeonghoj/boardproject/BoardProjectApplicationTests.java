package jeonghoj.boardproject;

import jeonghoj.boardproject.repository.GeneralBoardRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BoardProjectApplicationTests {

	@Autowired
	private GeneralBoardRepository generalBoardRepository;

	@Test
	public void closedProjections() {



	}

}
