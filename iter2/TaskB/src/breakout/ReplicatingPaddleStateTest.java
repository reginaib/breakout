package breakout;

import static org.junit.jupiter.api.Assertions.*;

import breakout.utils.Point;
import java.awt.Color;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReplicatingPaddleStateTest {
	private ReplicatingPaddleState paddle;
	private Color[] colors;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@BeforeEach
	void setUp() {
		Point point = new Point(1, 1);
		colors = new Color[]{Color.RED, Color.BLUE, Color.GREEN};
		paddle = new ReplicatingPaddleState(point, colors, Color.RED, 42);
	}

	@Test
	void replicatingPaddleStateTest() {
		assertEquals(42, paddle.getCount());
	}

	@Test
	void numberOfBallsAfterHitTest() {
		assertEquals(42, paddle.numberOfBallsAfterHit());
	}

	@Test
	void getActualColorsTest() {
		assertEquals(3, paddle.getActualColors().length);
		assertNotSame(paddle.getActualColors(), paddle.getActualColors());
		assertArrayEquals(paddle.getActualColors(), colors);
	}

	@Test
	void reproduceTest() {
		assertTrue(paddle.equalContent(paddle.reproduce()));
		assertNotSame(paddle, paddle.reproduce());
	}
}
