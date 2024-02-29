package breakout;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import breakout.utils.Vector;


class VectorTest {

    private Vector vector;

    @BeforeAll
    static void setUpBeforeClass() throws Exception {
    }

    @BeforeEach
    /**
     * some basic values to help us write tests
     */
    void setUp() {
        vector = new Vector(9, 10);
    }

    @Test
    void constructorTest() {
        assertEquals(9, vector.getX());
        assertEquals(10, vector.getY());
    }

    @Test
    void scaledTest() {
        Vector scaled = vector.scaled(2);
        assertEquals(18, scaled.getX());
        assertEquals(20, scaled.getY());
    }

    @Test
    void plusTest() {
        Vector plus = vector.plus(new Vector(1, 2));
        assertEquals(10, plus.getX());
        assertEquals(12, plus.getY());
    }
}
