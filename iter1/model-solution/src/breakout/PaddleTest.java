package breakout;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import breakout.utils.Point;

class PaddleTest {
	Point p11;
	PaddleState p1;

	@BeforeEach
	void setUp() throws Exception {
		p11 = new Point(1,1);
		p1 = new PaddleState(p11, Constants.TYPICAL_PADDLE_COLORS());
	}

	@Test
	void testPaddle() {
		assertEquals(p11, p1.getCenter());
	}

	@Test
	void testGetLocation() {
		assertEquals(new Point(1-Constants.PADDLE_WIDTH/2,1- Constants.PADDLE_HEIGHT/2), p1.getLocation().getTopLeft());
		assertEquals(new Point(1+Constants.PADDLE_WIDTH/2,1+Constants.PADDLE_HEIGHT/2), p1.getLocation().getBottomRight());
	}

}
