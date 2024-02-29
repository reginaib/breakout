package breakout;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import breakout.BlockState;
import breakout.BreakoutState;
import breakout.GameMap;
import breakout.PaddleState;
import breakout.utils.*;

/**
 * Those tests should fail on the provided bad implementaiton, succeed on the model solution.
 */
class TaskBTestSuite {

	private Point point;
	private Circle circle;
	private Vector vector;
	private Ball ball;
	private Color[] colors;
	private PaddleState paddle;
	private Point rightPoint;
	private Ball[] balls;
	private BlockState[] blocks;
	private PaddleState paddleState;
	private BreakoutState breakout;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@BeforeEach
	void setUp() {
		point = new Point(0, 0);
		circle = new Circle(point, 10);
		vector = new Vector(10, 10);
		ball = new Ball(circle, vector);
		colors = new Color[] {Color.RED, Color.BLUE, Color.GREEN};
		paddle = new PaddleState(point, colors);

		rightPoint = new Point(100000, 100000);
		balls = new Ball[] {new Ball(new Circle(new Point(1000, 1000), 200), new Vector(-100, -10))};
		blocks = new BlockState[] {new BlockState(new Rect(new Point(10, 10), new Point(2000, 720)))};
		paddleState = new PaddleState(new Point(10000, 10000), colors);
		breakout = new BreakoutState(balls, blocks, rightPoint, paddleState);
	}
	
	@Test
	void dummyTest() {
		assertEquals( 35, ball.dummy() );
	}

	@Test
	void ballConstructorTest() {
		assertThrows( IllegalArgumentException.class,
				() -> new Ball(null, vector) );
		assertThrows( IllegalArgumentException.class,
				() -> new Ball(circle, null) );

		assertSame(circle, ball.getLocation());
		assertSame(vector, ball.getVelocity());
	}

	@Test
	void ballGetLocationTest() {
		assertSame(circle, ball.getLocation());
	}

	@Test
	void ballGetVelocityTest() {
		assertSame(vector, ball.getVelocity());
	}

	@Test
	void ballSetLocationTest() {
		Circle circle = new Circle(point, 10);
		ball.setLocation(circle);
		assertSame(circle, ball.getLocation());
	}

	@Test
	void ballSetVelocityTest() {
		Vector vector = new Vector(10, 10);
		ball.setVelocity(vector);
		assertSame(vector, ball.getVelocity());
	}

	@Test
	void paddleConstructorTest() {
		colors[0] = Color.MAGENTA;
		assertEquals(paddle.getPossibleColors()[0], Color.RED);
		assertEquals(paddle.getCenter(), point);
	}

	@Test
	void paddleGetPossibleColorsTest() {
		assertNotSame(paddle.getPossibleColors(), colors);
		assertNotSame(paddle.getPossibleColors(), paddle.getPossibleColors());
		assertArrayEquals(paddle.getPossibleColors(), colors);
	}

	@Test
	void breakoutStateConstructorTest() {
		assertThrows( IllegalArgumentException.class,
				() -> new BreakoutState(null,
						new BlockState[] {new BlockState(new Rect(new Point(10, 10), new Point(100, 100)))},
						rightPoint, new PaddleState(new Point(10000, 10000), colors)));

		assertThrows( IllegalArgumentException.class,
				() -> new BreakoutState(new Ball[] {new Ball(new Circle(new Point(1000, 1000), 100), vector)},
						null,
						rightPoint, new PaddleState(new Point(10000, 10000), colors)));

		assertThrows( IllegalArgumentException.class,
				() -> new BreakoutState(new Ball[] {new Ball(new Circle(new Point(1000, 1000), 100), vector)},
						new BlockState[] {new BlockState(new Rect(new Point(10, 10), new Point(100, 100)))},
						null, new PaddleState(new Point(10000, 10000), colors)));

		assertThrows( IllegalArgumentException.class,
				() -> new BreakoutState(new Ball[] {new Ball(new Circle(new Point(1000, 1000), 100), vector)},
						new BlockState[] {new BlockState(new Rect(new Point(10, 10), new Point(100, 100)))},
						rightPoint, null));

		assertThrows( IllegalArgumentException.class,
				() -> new BreakoutState(new Ball[] {new Ball(new Circle(new Point(1000, 1000), 100), vector)},
						new BlockState[] {new BlockState(new Rect(new Point(10, 10), new Point(100, 100)))},
						rightPoint, new PaddleState(new Point(-10000, 10000), colors)));

		assertThrows( IllegalArgumentException.class,
				() -> new BreakoutState(new Ball[] {new Ball(new Circle(new Point(1000, 1000), 100), vector)},
						new BlockState[] {new BlockState(new Rect(new Point(10, 10), new Point(100, 100)))},
						rightPoint, new PaddleState(new Point(1000000, 10000), colors)));

		assertThrows( IllegalArgumentException.class,
				() -> new BreakoutState(new Ball[] {new Ball(new Circle(new Point(1000, 1000), 100), vector)},
						new BlockState[] {new BlockState(new Rect(new Point(-10, 10), new Point(100, 100)))},
						rightPoint, new PaddleState(new Point(10000, 10000), colors)));

		assertThrows( IllegalArgumentException.class,
				() -> new BreakoutState(new Ball[] {new Ball(new Circle(new Point(1000, 1000), 100), vector)},
						new BlockState[] {new BlockState(new Rect(new Point(1000000, 10), new Point(1000100, 100)))},
						rightPoint, new PaddleState(new Point(10000, 10000), colors)));

		assertThrows( IllegalArgumentException.class,
				() -> new BreakoutState(new Ball[] {new Ball(new Circle(new Point(-1000, 1000), 100), vector)},
						new BlockState[] {new BlockState(new Rect(new Point(10, 10), new Point(100, 100)))},
						rightPoint, new PaddleState(new Point(10000, 10000), colors)));

		assertThrows( IllegalArgumentException.class,
				() -> new BreakoutState(new Ball[] {new Ball(new Circle(new Point(1000000, 1000), 100), vector)},
						new BlockState[] {new BlockState(new Rect(new Point(10, 10), new Point(100, 100)))},
						rightPoint, new PaddleState(new Point(10000, 10000), colors)));

		assertSame(breakout.getBottomRight(), rightPoint);
		assertSame(breakout.getPaddle(), paddleState);

		assertSame(breakout.getBlocks()[0], blocks[0]);
		blocks[0] = new BlockState(new Rect(new Point(100, 100), new Point(100, 100)));
		assertNotSame(breakout.getBlocks()[0], blocks[0]);

		assertNotSame(breakout.getBalls()[0], balls[0]);
		assertEquals(breakout.getBalls()[0], balls[0]);
		balls[0].setLocation(new Circle(new Point(100, 100), 100));
		assertNotEquals(breakout.getBalls()[0], balls[0]);
	}

	@Test
	void breakoutStateGetBallsTest() {
		assertNotSame(breakout.getBalls(), breakout.getBalls());
		assertNotSame(breakout.getBalls()[0], breakout.getBalls()[0]);
		assertEquals(breakout.getBalls()[0], breakout.getBalls()[0]);
	}

	@Test
	void breakoutStateGetBlocksTest() {
		assertNotSame(breakout.getBlocks(), breakout.getBlocks());
		assertSame(breakout.getBlocks()[0], breakout.getBlocks()[0]);
	}

	@Test
	void breakoutStateTossPaddleColorTest() {
		Color color = breakout.getCurPaddleColor();
		assertSame(breakout.getCurPaddleColor(), color);

		int c = 0;
		for (int i = 0; i < 100; i++) {
			breakout.tossPaddleColor();
			Color newColor = breakout.getCurPaddleColor();
			if (newColor != color) {
				color = newColor;
				c++;
			}
		}
		assertTrue(c > 1);
	}

	@Test
	void breakoutStateBounceWallsTest() {
		breakout.tick(0, 9);
		Ball ball = breakout.getBalls()[0];

		assertEquals(100, ball.getCenter().getX());
		assertEquals(910, ball.getCenter().getY());
		assertEquals(100, ball.getVelocity().getX());
		assertEquals(-10, ball.getVelocity().getY());
	}

	@Test
	void breakoutCollideBallBlocksTest() {
		breakout.tick(0, 9); // ball hits wall
		breakout.tick(0, 9); // ball hits block

		Ball ball = breakout.getBalls()[0];
		assertEquals(1000, ball.getCenter().getX());
		assertEquals(820, ball.getCenter().getY());
		assertEquals(100, ball.getVelocity().getX());
		assertEquals(10, ball.getVelocity().getY());

		assertTrue(breakout.isWon());
	}
}
