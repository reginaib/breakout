package breakout;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import breakout.utils.*;

import java.awt.Color;


class SturdyBlockStateTest {
    private Rect location;
    private SturdyBlockState block;

    @BeforeAll
    static void setUpBeforeClass() throws Exception {
    }

    @BeforeEach
    void setUp()  {
        location = new Rect(new Point(0, 0), new Point(10, 10));
        block = new SturdyBlockState(location, 3);
    }

    @Test
    void sturdyBlockStateConstructorTest() {
        assertEquals(3, block.getLivesLeft());
    }

    @Test
    void getLivesLeftTest() {
        assertEquals(3, block.getLivesLeft());
    }

    @Test
    void getColorTest() {
        assertEquals(new Color(92, 64, 51), block.getColor());
        SturdyBlockState oneHit = (SturdyBlockState) block.blockStateAfterHit(1);
        assertEquals(new Color(123, 63, 0), oneHit.getColor());

        SturdyBlockState twoHit = (SturdyBlockState) oneHit.blockStateAfterHit(1);
        assertEquals(new Color(160, 82, 45), twoHit.getColor());

        SturdyBlockState silkTouch = (SturdyBlockState) twoHit.blockStateAfterHit(1);
        assertEquals(new Color(160, 82, 45), silkTouch.getColor());
    }

    @Test
    void blockStateAfterHitTest() {
        SturdyBlockState oneHit = (SturdyBlockState) block.blockStateAfterHit(1);

        assertNotSame(block, oneHit);
        assertEquals(oneHit.getLocation(), location);
        assertEquals(2, oneHit.getLivesLeft());

        SturdyBlockState twoHit = (SturdyBlockState) oneHit.blockStateAfterHit(1);

        assertNotSame(twoHit, oneHit);
        assertEquals(twoHit.getLocation(), location);
        assertEquals(1, twoHit.getLivesLeft());

        SturdyBlockState silkTouch = (SturdyBlockState) twoHit.blockStateAfterHit(1);

        assertSame(twoHit, silkTouch);
        assertEquals(silkTouch.getLocation(), location);
        assertEquals(1, silkTouch.getLivesLeft());

        SturdyBlockState smashed = (SturdyBlockState) twoHit.blockStateAfterHit(100);
        assertNull(smashed);
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
