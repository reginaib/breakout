package breakout;

import static org.junit.jupiter.api.Assertions.*;

import breakout.utils.Point;
import java.awt.Color;

import breakout.utils.Rect;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NormalPaddleStateTest {
	private Point point;
	private Color color;
	private Color[] colors;
	private NormalPaddleState paddle;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@BeforeEach
	void setUp()  {
		point = new Point(1,1);
		colors = new Color[] {Color.RED, Color.BLUE, Color.GREEN};
		color = Color.RED;
		paddle = new NormalPaddleState(point, colors, color);
	}

	@Test
	void normalPaddleStateTest() {
		assertSame(point, paddle.getCenter());
		assertSame(color, paddle.getCurColor());
		assertNotSame(colors, paddle.getPossibleColors());
		assertArrayEquals(colors, paddle.getPossibleColors());
		colors[0] = Color.WHITE;
		assertNotEquals(Color.WHITE, paddle.getPossibleColors()[0]);
		assertEquals(Color.RED, paddle.getPossibleColors()[0]);
	}

	@Test
	void getPossibleColorsTest() {
		assertNotSame(paddle.getPossibleColors(), paddle.getPossibleColors());
		assertArrayEquals(paddle.getPossibleColors(), paddle.getPossibleColors());
	}

	@Test
	void getActualColorsTest() {
		assertEquals(1, paddle.getActualColors().length);
		assertNotSame(paddle.getActualColors(), paddle.getActualColors());
		assertArrayEquals(paddle.getActualColors(), new Color[] {color});
	}

	@Test
	void getLocationTest() {
		Rect rect = new Rect(new Point(1 - 4500 / 2, 1 - 250), new Point(1 + 4500 / 2, 1 + 250));
		assertEquals(rect, paddle.getLocation());
	}

	@Test
	void setCenterTest() {
		Point newPoint = new Point(2,2);
		paddle.setCenter(newPoint);
		assertSame(newPoint, paddle.getCenter());
	}

	@Test
	void numberOfBallsAfterHitTest() {
		assertEquals(1, paddle.numberOfBallsAfterHit());
	}

	@Test
	void stateAfterHitTest() {
		assertSame(paddle, paddle.stateAfterHit());
	}

	@Test
	void tossCurColorTest() {
		Color color = paddle.getCurColor();
		assertSame(paddle.getCurColor(), color);

		int c = 0;
		for (int i = 0; i < 100; i++) {
			paddle.tossCurColor();
			Color newColor = paddle.getCurColor();
			if (newColor != color) {
				color = newColor;
				c++;
			}
		}
		assertTrue(c > 1);
	}

	@Test
	void reproduceTest() {
		assertTrue(paddle.equalContent(paddle.reproduce()));
		assertNotSame(paddle, paddle.reproduce());
	}
}
