package breakout;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import breakout.utils.*;

import java.awt.Color;


class ReplicatorBlockStateTest {
	private ReplicatorBlockState block;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@BeforeEach
	void setUp()  {
		block = new ReplicatorBlockState(new Rect(new Point(0, 0), new Point(10, 10)));
	}

	@Test
	void getColorTest() {
		assertEquals(new Color(100, 149, 237), block.getColor());
	}

	@Test
	void paddleStateAfterHitTest() {
		Color color = Color.RED;
		Color[] colors = new Color[] {Color.RED, Color.BLUE, Color.GREEN};

		NormalPaddleState normalPaddle = new NormalPaddleState(new Point(1000, 1000), colors, color);
		ReplicatingPaddleState replicatingPaddle = new ReplicatingPaddleState(new Point(1000, 1000), colors, color, 4);
		ReplicatingPaddleState clone = new ReplicatingPaddleState(new Point(1000, 1000), colors, color, 4);

		assertTrue(clone.equalContent(block.paddleStateAfterHit(normalPaddle)));
		assertNotSame(replicatingPaddle, block.paddleStateAfterHit(replicatingPaddle));
		assertTrue(clone.equalContent(block.paddleStateAfterHit(replicatingPaddle)));
	}
}
