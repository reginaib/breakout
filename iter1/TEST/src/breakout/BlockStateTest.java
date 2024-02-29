package breakout;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import breakout.utils.Rect;
import breakout.utils.Point;


class BlockStateTest {

    private Rect rect;
    private BlockState block;

    @BeforeAll
    static void setUpBeforeClass() throws Exception {
    }

    @BeforeEach
    void setUp() {
        rect = new Rect(new Point(0, 0), new Point(10, 10));
        block = new BlockState(rect);
    }

    @Test
    void constructorTest() {
        assertSame(rect, block.getLocation());
    }
}
