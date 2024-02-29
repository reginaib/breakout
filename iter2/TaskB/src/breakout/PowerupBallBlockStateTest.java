package breakout;

import breakout.utils.Point;
import breakout.utils.Rect;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;


class PowerupBallBlockStateTest {
	private PowerupBallBlockState block;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@BeforeEach
	void setUp()  {
		block = new PowerupBallBlockState(new Rect(new Point(0, 0), new Point(10, 10)));
	}

	@Test
	void getColorTest() {
		assertEquals(new Color(215, 0, 64), block.getColor());
	}
}
