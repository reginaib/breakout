package breakout;
import static org.junit.jupiter.api.Assertions.*;

import breakout.utils.Point;
import breakout.utils.Circle;
import breakout.utils.Vector;
import breakout.utils.Rect;
import java.awt.Color;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BreakoutStateTest {
	private Point rightPoint;

	private Color[] colors;
	private Ball[] balls;
	private BlockState[] blocks;
	private PaddleState paddle;
	private BreakoutState breakout;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@BeforeEach
	void setUp() {
		rightPoint = new Point(100000, 100000);
		balls = new Ball[] {new NormalBall(new Circle(new Point(1000, 1000), 200), new Vector(0, -10)),
							new SuperChargedBall(new Circle(new Point(1000, 1000), 200), new Vector(0, -10), 10)};
		blocks = new BlockState[] {new NormalBlockState(new Rect(new Point(10, 10), new Point(2000, 700))),
								   new SturdyBlockState(new Rect(new Point(10, 10), new Point(2000, 700)), 3),
								   new ReplicatorBlockState(new Rect(new Point(10, 10), new Point(2000, 700))),
								   new PowerupBallBlockState(new Rect(new Point(10, 10), new Point(2000, 700)))};
		colors = new Color[] {Color.RED, Color.BLUE, Color.GREEN};
		paddle = new NormalPaddleState(new Point(3000, 1550), colors, Color.RED);
		breakout = new BreakoutState(balls, blocks, rightPoint, paddle);
	}

	@Test
	void breackoutStateConstructorTest() {
		assertThrows(IllegalArgumentException.class, () -> new BreakoutState(null, blocks, rightPoint, paddle));
		assertThrows(IllegalArgumentException.class, () -> new BreakoutState(balls, null, rightPoint, paddle));
		assertThrows(IllegalArgumentException.class, () -> new BreakoutState(balls, blocks, null, paddle));
		assertThrows(IllegalArgumentException.class, () -> new BreakoutState(balls, blocks, rightPoint, null));

		assertThrows(IllegalArgumentException.class, () -> new BreakoutState(new Ball[] {null}, blocks, rightPoint, paddle));
		assertThrows(IllegalArgumentException.class, () -> new BreakoutState(balls, new BlockState[] {null}, rightPoint, paddle));
		assertThrows(IllegalArgumentException.class, () -> new BreakoutState(balls, blocks, new Point(-1, -1), paddle));
		assertThrows(IllegalArgumentException.class, () -> new BreakoutState(balls, blocks, rightPoint, new NormalPaddleState(new Point(-1, -1), colors, Color.RED)));
		assertThrows(IllegalArgumentException.class, () -> new BreakoutState(new Ball[] {new NormalBall(new Circle(new Point(-1, -1), 100), new Vector(0, 0))}, blocks, rightPoint, paddle));
		assertThrows(IllegalArgumentException.class, () -> new BreakoutState(balls, new BlockState[] {new NormalBlockState(new Rect(new Point(-1, -1), new Point(0, 0)))}, rightPoint, paddle));

		balls[0] = null;
		assertNotNull(breakout.getBalls()[0]);

		blocks[0] = null;
		assertNotNull(breakout.getBlocks()[0]);
	}

	@Test
	void getBallsTest() {
		Ball[] copy = breakout.getBalls();
		assertEquals(balls.length, copy.length);
		for (int i = 0; i < balls.length; i++) {
			assertNotSame(balls[i], copy[i]);
			assertEquals(balls[i], copy[i]);
		}
	}

	@Test
	void getBlocksTest() {
		BlockState[] copy = breakout.getBlocks();
		assertEquals(blocks.length, copy.length);
		for (int i = 0; i < blocks.length; i++) {
			assertSame(blocks[i], copy[i]);
		}
	}

	@Test
	void getPaddleTest() {
		assertSame(paddle, breakout.getPaddle());
	}

	@Test
	void getBottomRightTest() {
		assertSame(rightPoint, breakout.getBottomRight());
	}

	@Test
	void getFieldTest() {
		assertEquals(new Rect(new Point(0, 0), rightPoint), breakout.getField());
	}

	@Test
	void bounceWallsTest() {
		Ball[] balls = new Ball[] {new NormalBall(new Circle(new Point(1100, 1000), 200), new Vector(-50, 0))};
		BreakoutState breakout = new BreakoutState(balls, blocks, rightPoint, paddle);

		breakout.tickDuring(40);
		assertEquals(1100, breakout.getBalls()[0].getCenter().getX());
	}

	@Test
	void removeDeadTest() {
		Ball[] balls = new Ball[] {new NormalBall(new Circle(new Point(1000, 100000 - 200), 200), new Vector(0, 10))};
		BreakoutState breakout = new BreakoutState(balls, blocks, rightPoint, paddle);

		breakout.tickDuring(20);
		assertEquals(0, breakout.getBalls().length);
	}

	@Test
	void clampBallTest() {
		Ball[] balls = new Ball[] {new NormalBall(new Circle(new Point(1000, 1000), 200), new Vector(-100, 0))};
		BreakoutState breakout = new BreakoutState(balls, blocks, rightPoint, paddle);

		breakout.tickDuring(10);
		assertEquals(100, breakout.getBalls()[0].getCenter().getX());
	}

	@Test
	void collideBallBlocksTest() {
		Ball[] balls = new Ball[] {new NormalBall(new Circle(new Point(1000, 1000), 200), new Vector(0, -10))};
		BlockState[] blocks = new BlockState[] {new PowerupBallBlockState(new Rect(new Point(10, 10), new Point(2000, 700)))};
		NormalPaddleState paddle = new NormalPaddleState(new Point(3000, 2550), colors, Color.RED);
		BreakoutState breakout = new BreakoutState(balls, blocks, rightPoint, paddle);
		breakout.tickDuring(20);

		assertSame(paddle, breakout.getPaddle());
		assertEquals(0, breakout.getBlocks().length);
		assertInstanceOf(SuperChargedBall.class, breakout.getBalls()[0]);
		assertEquals(10, breakout.getBalls()[0].getVelocity().getY());
		assertEquals(1000, breakout.getBalls()[0].getCenter().getX());
		assertEquals(800, breakout.getBalls()[0].getCenter().getY());

		blocks = new BlockState[] {new ReplicatorBlockState(new Rect(new Point(10, 10), new Point(2000, 700)))};
		breakout = new BreakoutState(balls, blocks, rightPoint, paddle);
		breakout.tickDuring(20);

		assertInstanceOf(NormalBall.class, breakout.getBalls()[0]);
		assertInstanceOf(ReplicatingPaddleState.class, breakout.getPaddle());
	}
}
