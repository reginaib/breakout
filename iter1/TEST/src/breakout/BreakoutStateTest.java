package breakout;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import breakout.utils.Circle;
import breakout.utils.Point;
import breakout.utils.Vector;
import breakout.utils.Rect;


class BreakoutStateTest {

    private Ball[] balls;
    private Color[] colors;
    private BlockState[] blocks;
    private PaddleState paddleState;
    private Point rightPoint;
    private BreakoutState breakout;

    @BeforeAll
    static void setUpBeforeClass() throws Exception {
    }

    @BeforeEach
    void setUp() {
        rightPoint = new Point(100000, 100000);
        colors = new Color[] {Color.RED, Color.BLUE, Color.GREEN};
        balls = new Ball[] {new Ball(new Circle(new Point(1000, 1000), 200), new Vector(-100, 10))};
        blocks = new BlockState[] {new BlockState(new Rect(new Point(10, 10), new Point(2000, 720)))};
        paddleState = new PaddleState(new Point(2000, 1360), colors);
        breakout = new BreakoutState(balls, blocks, rightPoint, paddleState);
    }

    @Test
    void getCurPaddleColorTest() {
       assertSame(breakout.getCurPaddleColor(), Color.RED);
    }

    @Test
    void removeDeadTest() {
        breakout.tick(0, 1);
        assertFalse(breakout.isDead());

        BreakoutState breakoutDead = new BreakoutState(new Ball[] {
                new Ball(new Circle(new Point(50000, 50000), 200), new Vector(0, 50000))
        },
                blocks, rightPoint, paddleState);
        breakoutDead.tick(0, 1);
        assertTrue(breakoutDead.isDead());
    }

    @Test
    void collideBallPaddleStatTest() {
        breakout.tick(0, 1);

        Ball ball = breakout.getBalls()[0];
        assertEquals(900, ball.getCenter().getX());
        assertEquals(1010, ball.getCenter().getY());
        assertEquals(-100, ball.getVelocity().getX());
        assertEquals(-10, ball.getVelocity().getY());
    }

    @Test
    void collideBallPaddleMoveRightTest() {
        breakout.tick(1, 1);

        Ball ball = breakout.getBalls()[0];
        assertEquals(900, ball.getCenter().getX());
        assertEquals(1010, ball.getCenter().getY());
        assertEquals(-97, ball.getVelocity().getX());
        assertEquals(-10, ball.getVelocity().getY());
    }

    @Test
    void collideBallPaddleMoveLeftTest() {
        breakout.tick(-1, 1);

        Ball ball = breakout.getBalls()[0];
        assertEquals(900, ball.getCenter().getX());
        assertEquals(1010, ball.getCenter().getY());
        assertEquals(-103, ball.getVelocity().getX());
        assertEquals(-10, ball.getVelocity().getY());
    }

    @Test
    void isWonTest() {
        assertFalse(breakout.isWon());
        BreakoutState breakoutWon = new BreakoutState(balls, new BlockState[0], rightPoint, paddleState);
        assertTrue(breakoutWon.isWon());
    }

    @Test
    void isDeadTest() {
        assertFalse(breakout.isDead());
        BreakoutState breakoutDead = new BreakoutState(new Ball[0], blocks, rightPoint, paddleState);
        assertTrue(breakoutDead.isDead());
    }
}
