package breakout;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import breakout.utils.Circle;
import breakout.utils.Point;
import breakout.utils.Vector;
import breakout.utils.Rect;


class NormalBallTest {
	private Circle location;
	private Vector velocity_slow;
	private Vector velocity_fast;
	private NormalBall slowBall;
	private NormalBall fastBall;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@BeforeEach
	void setUp()  {
		location = new Circle(new Point(1000, 1000), 200);
		velocity_fast = new Vector(0, 10);
		velocity_slow = new Vector(0, 2);
		fastBall = new NormalBall(location, velocity_fast);
		slowBall = new NormalBall(location, velocity_slow);
	}

	@Test
	void normalBallConstructorTest() {
		assertThrows(IllegalArgumentException.class, () -> new NormalBall(location, null));
		assertThrows(IllegalArgumentException.class, () -> new NormalBall(null, velocity_fast));
	}

	@Test
	void getLocationTest() {
		assertSame(fastBall.getLocation(), location);
	}

	@Test
	void getVelocityTest() {
		assertSame(fastBall.getVelocity(), velocity_fast);
		assertSame(slowBall.getVelocity(), velocity_slow);
	}

	@Test
	void getCenterTest() {
		assertSame(fastBall.getCenter(), location.getCenter());
	}

	@Test
	void setLocationTest() {
		Circle newLocation = new Circle(new Point(100, 100), 200);
		fastBall.setLocation(newLocation);
		assertSame(fastBall.getLocation(), newLocation);
	}

	@Test
	void setVelocityTest() {
		Vector newVelocity = new Vector(10, 0);
		fastBall.setVelocity(newVelocity);
		assertSame(fastBall.getVelocity(), newVelocity);
	}

	@Test
	void setPositionTest() {
		Point newPos = new Point(100, 100);
		fastBall.setPosition(newPos);
		assertSame(fastBall.getCenter(), newPos);
	}

	@Test
	void hitBlockTest() {
		Rect rect = new Rect(new Point(100, 1100), new Point(2000, 2000));
		fastBall.hitBlock(rect, false);
		assertEquals(fastBall.getVelocity().getX(), velocity_fast.getX());
		assertEquals(fastBall.getVelocity().getY(), -velocity_fast.getY());

		slowBall.hitBlock(rect, true);
		assertEquals(slowBall.getVelocity().getX(), velocity_slow.getX());
		assertEquals(slowBall.getVelocity().getY(), -velocity_slow.getY());
	}

	@Test
	void hitWallTest() {
		Rect rect = new Rect(new Point(100, 1100), new Point(2000, 2000));
		fastBall.hitWall(rect);
		assertEquals(fastBall.getVelocity().getX(), velocity_fast.getX());
		assertEquals(fastBall.getVelocity().getY(), -velocity_fast.getY());
	}

	@Test
	void getColorTest() {
		assertSame(fastBall.getColor(), Constants.BALL_FAST_COLOR);
		assertSame(slowBall.getColor(), Constants.BALL_COLOR);
	}

	@Test
	void backToNormalTest() {
		assertSame(fastBall, fastBall.backToNormal());
	}

	@Test
	void cloneTest() {
		NormalBall clone = (NormalBall) fastBall.clone();
		assertNotSame(clone, fastBall);
		assertEquals(clone, fastBall);
	}

	@Test
	void cloneWithVelocityTest() {
		Vector newVelocity = new Vector(10, 0);
		NormalBall clone = (NormalBall) fastBall.cloneWithVelocity(newVelocity);
		assertNotSame(clone, fastBall);
		assertSame(clone.getVelocity(), newVelocity);
		assertSame(clone.getLocation(), fastBall.getLocation());
	}
}
