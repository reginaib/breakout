package breakout;

import static org.junit.jupiter.api.Assertions.*;

import breakout.utils.Rect;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import breakout.utils.Circle;
import breakout.utils.Point;
import breakout.utils.Vector;


class SuperChargedBallTest {
	private Vector velocity_slow;
	private Vector velocity_fast;
	private SuperChargedBall fastBall;
	private SuperChargedBall slowBall;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@BeforeEach
	void setUp()  {
		Circle location = new Circle(new Point(1000, 1000), 200);
		velocity_fast = new Vector(0, 10);
		velocity_slow = new Vector(0, 2);
		fastBall = new SuperChargedBall(location, velocity_fast, 10);
		slowBall = new SuperChargedBall(location, velocity_slow, 1);
	}

	@Test
	void getLifetimeTest() {
		assertEquals(fastBall.getLifetime(), 10);
		assertEquals(slowBall.getLifetime(), 1);
	}

	@Test
	void hitWallTest() {
		Rect rect = new Rect(new Point(100, 1100), new Point(2000, 2000));
		fastBall.hitWall(rect);
		assertEquals(fastBall.getVelocity().getX(), velocity_fast.getX());
		assertEquals(fastBall.getVelocity().getY(), -velocity_fast.getY());
	}

	@Test
	void backToNormalTest() {
		NormalBall normalBall = new NormalBall(new Circle(new Point(1000, 1000), Constants.INIT_BALL_DIAMETER), velocity_slow);

		assertSame(slowBall, slowBall.backToNormal());

		slowBall.move(new Vector(0, 0), 1);
		assertEquals(normalBall, slowBall.backToNormal());
	}

	@Test
	void cloneTest() {
		SuperChargedBall clone = (SuperChargedBall) fastBall.clone();
		assertNotSame(clone, fastBall);
		assertEquals(clone, fastBall);
		assertEquals(clone.getLifetime(), fastBall.getLifetime());
	}

	@Test
	void cloneWithVelocityTest() {
		Vector newVelocity = new Vector(10, 0);
		SuperChargedBall clone = (SuperChargedBall) fastBall.cloneWithVelocity(newVelocity);
		assertNotSame(clone, fastBall);
		assertSame(clone.getVelocity(), newVelocity);
		assertSame(clone.getLocation(), fastBall.getLocation());
		assertEquals(clone.getLifetime(), fastBall.getLifetime());
	}
}
