package breakout;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import breakout.utils.*;

import java.awt.Color;


class NormalBlockStateTest {
	private Rect location;
	private NormalBlockState block;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@BeforeEach
	void setUp()  {
		location = new Rect(new Point(0, 0), new Point(10, 10));
		block = new NormalBlockState(location);
	}

	@Test
	void getLocationTest() {
		assertSame(location, block.getLocation());
	}

	@Test
	void getColorTest() {
		assertEquals(new Color(128, 128, 128), block.getColor());
	}

	@Test
	void blockStateAfterHitTest() {
		assertNull(block.blockStateAfterHit(1));
	}

	@Test
	void ballStateAfterHitTest() {
		Circle location = new Circle(new Point(1000, 1000), 200);
		NormalBall ball = new NormalBall(location, new Vector(0, 10));
		NormalBall clone = new NormalBall(location, new Vector(0, 10));

		assertSame(ball, block.ballStateAfterHit(ball));
		assertEquals(clone, block.ballStateAfterHit(ball));
	}

	@Test
	void paddleStateAfterHitTest() {
		Color color = Color.RED;
		Color[] colors = new Color[] {Color.RED, Color.BLUE, Color.GREEN};

		NormalPaddleState paddle = new NormalPaddleState(new Point(1000, 1000), colors, color);
		NormalPaddleState clone = new NormalPaddleState(new Point(1000, 1000), colors, color);

		assertSame(paddle, block.paddleStateAfterHit(paddle));
		assertTrue(clone.equalContent(block.paddleStateAfterHit(paddle)));
	}
}
