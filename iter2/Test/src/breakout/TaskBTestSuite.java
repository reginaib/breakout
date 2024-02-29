package breakout;


import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import breakout.utils.*;

/**
 * Those tests should fail on the provided bad implementation, succeed on the model solution.
 */
class TaskBTestSuite {
	private Point rightPoint;
	private Color[] colors;
	private BlockState[] blocks;
	private PaddleState paddle;

	private Point point;

	private Circle location;
	private Vector velocity_slow;
	private Vector velocity_fast;
	private SuperChargedBall fastBall;
	private SuperChargedBall slowBall;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@BeforeEach
	/**
	 * some basic values to help us write tests
	 */
	void setUp() {
		rightPoint = new Point(100000, 100000);
		blocks = new BlockState[] {new NormalBlockState(new Rect(new Point(10, 10), new Point(2000, 700))),
				new SturdyBlockState(new Rect(new Point(10, 10), new Point(2000, 700)), 3),
				new ReplicatorBlockState(new Rect(new Point(10, 10), new Point(2000, 700))),
				new PowerupBallBlockState(new Rect(new Point(10, 10), new Point(2000, 700)))};
		colors = new Color[] {Color.RED, Color.BLUE, Color.GREEN};
		paddle = new NormalPaddleState(new Point(3000, 1550), colors, Color.RED);

		point = new Point(1,1);

		location = new Circle(new Point(1000, 1000), 800);
		velocity_fast = new Vector(0, 10);
		velocity_slow = new Vector(0, 2);
		fastBall = new SuperChargedBall(location, velocity_fast, 10);
		slowBall = new SuperChargedBall(location, velocity_slow, 1);
	}

	@Test
	void breakoutStateCollideBallPaddleTest() {
		Ball[] balls = new Ball[] {new SuperChargedBall(new Circle(new Point(1000, 1000), 200), new Vector(0, 10), 30)};
		ReplicatingPaddleState paddle = new ReplicatingPaddleState(new Point(3000, 1550), colors, Color.RED, 2);
		BreakoutState breakout = new BreakoutState(balls, blocks, rightPoint, paddle);

		breakout.tick(-1, 20);

		assertEquals(2, breakout.getBalls().length);

		assertInstanceOf(SuperChargedBall.class, breakout.getBalls()[0]);
		assertInstanceOf(SuperChargedBall.class, breakout.getBalls()[1]);

		assertEquals(150, breakout.getBalls()[0].getLocation().getRadius());
		assertEquals(150, breakout.getBalls()[1].getLocation().getRadius());

		assertEquals(-10, breakout.getBalls()[0].getVelocity().getY());
		assertEquals(-3, breakout.getBalls()[0].getVelocity().getX());
		assertEquals(-10, breakout.getBalls()[1].getVelocity().getY());
		assertEquals(-3, breakout.getBalls()[1].getVelocity().getX());

		assertEquals(1000, breakout.getBalls()[0].getCenter().getX());
		assertEquals(1200, breakout.getBalls()[0].getCenter().getY());
		assertEquals(25000, breakout.getBalls()[1].getCenter().getX());
		assertEquals(19750, breakout.getBalls()[1].getCenter().getY());

		assertInstanceOf(NormalPaddleState.class, breakout.getPaddle());
	}

	@Test
	void breakoutStateNoLongerSuperChargedTest() {
		Ball[] balls = new Ball[] {new SuperChargedBall(new Circle(new Point(1000, 1000), 200), new Vector(0, 0), 10)};
		BreakoutState breakout = new BreakoutState(balls, blocks, rightPoint, paddle);

		breakout.tickDuring(10);
		assertSame(NormalBall.class, breakout.getBalls()[0].getClass());
	}

	@Test
	void powerupBallBlockStateBallStateAfterHitTest() {
		PowerupBallBlockState block = new PowerupBallBlockState(new Rect(new Point(0, 0), new Point(10, 10)));
		NormalBall ball = new NormalBall(new Circle(new Point(1000, 1000), 200), new Vector(0, 10));
		NormalBall superBall = new SuperChargedBall(new Circle(new Point(1000, 1000), 1300), new Vector(0, 10), 10);

		assertEquals(superBall, block.ballStateAfterHit(ball));
		assertEquals(superBall, block.ballStateAfterHit(new SuperChargedBall(new Circle(new Point(1000, 1000), 800), new Vector(0, 10), 1)));
	}

	@Test
	void replicatingPaddlestateAfterHitTest() {
		ReplicatingPaddleState paddle = new ReplicatingPaddleState(point, colors, Color.RED, 3);
		PaddleState result = paddle.stateAfterHit();
		assertSame(result, paddle);
		assertEquals(2, paddle.getCount());

		result = paddle.stateAfterHit();
		assertInstanceOf(NormalPaddleState.class, result);
		assertSame(point, result.getCenter());
		assertArrayEquals(colors, result.getPossibleColors());
		assertSame(paddle.getCurColor(), result.getCurColor());
	}

	@Test
	void superChargedBallConstructorTest() {
		assertThrows(IllegalArgumentException.class, () -> new SuperChargedBall(location, velocity_fast, 0));
		assertThrows(IllegalArgumentException.class, () -> new SuperChargedBall(location, velocity_fast, -1));
	}

	@Test
	void superChargedBallHitBlockTest() {
		Rect rect = new Rect(new Point(100, 1100), new Point(2000, 2000));
		fastBall.hitBlock(rect, false);
		assertEquals(fastBall.getVelocity().getX(), velocity_fast.getX());
		assertEquals(fastBall.getVelocity().getY(), -velocity_fast.getY());

		slowBall.hitBlock(rect, true);
		assertEquals(slowBall.getVelocity().getX(), velocity_slow.getX());
		assertEquals(slowBall.getVelocity().getY(), velocity_slow.getY());

		assertEquals(fastBall.getLocation().getDiameter(), 700);
		assertEquals(slowBall.getLocation().getDiameter(), 700);
	}

	@Test
	void superChargedBallHitPaddleTest() {
		Rect rect = new Rect(new Point(100, 1100), new Point(2000, 2000));
		fastBall.hitPaddle(rect, new Vector(0, 0));

		assertEquals(fastBall.getVelocity().getX(), velocity_fast.getX());
		assertEquals(fastBall.getVelocity().getY(), -velocity_fast.getY());
		assertEquals(fastBall.getLocation().getDiameter(), 900);
	}
}

