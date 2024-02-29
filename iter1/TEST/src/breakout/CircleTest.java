package breakout;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import breakout.utils.Circle;
import breakout.utils.Point;


class CircleTest {

    private Point point;
    private Circle circle;

    @BeforeAll
    static void setUpBeforeClass() throws Exception {
    }

    @BeforeEach

    void setUp() {
        point = new Point(0, 0);
        circle = new Circle(point, 700);
    }

    @Test
    void constructorTest() {
        assertSame(point, circle.getCenter());
        assertEquals(700, circle.getDiameter());
    }

    @Test
    void withCenterTest() {
        Point newPoint = new Point(1, 1);
        Circle newCircle = circle.withCenter(newPoint);

        assertSame(newPoint, newCircle.getCenter());
        assertEquals(700, newCircle.getDiameter());
        assertNotSame(circle, newCircle);
        assertSame(point, circle.getCenter());
    }

    @Test
    void hashCodeTest() {
        assertEquals(31452, circle.hashCode());
    }

}
