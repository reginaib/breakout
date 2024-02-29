package breakout;




import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import breakout.utils.*;

class BallTest {
	Point p11;
	Point p05;
	Point p38;
	Point pm14;
	
	Rect r1138;
	Rect rm1438;
	
	Vector v1010;
	
	Circle c052;
	Circle c389;
	Ball b1;
	
	@BeforeEach
	void setUp() throws Exception {
		p11 = new Point(1,1);
		p05 = new Point(0,5);
		p38 = new Point(3,8);
		pm14 = new Point(-1,4);
		r1138 = new Rect(p11,p38);
		rm1438 = new Rect(pm14,p38);
		c052 = new Circle(p05,2);
		c389 = new Circle(p38,9);
		v1010 = new Vector(10,10);
		b1 = new Ball(c052, v1010);
	}

	@Test
	void testBall() {
		assertEquals(p05, b1.getLocation().getCenter());
		assertEquals(2, b1.getLocation().getDiameter());
		assertEquals(v1010, b1.getVelocity());
	}

	@Test
	void testHitRect() {
		b1.hitRect(r1138);
		assertEquals(new Vector(-10,10),b1.getVelocity());
	}
	
	@Test
	void eqTest() {
		Circle c1 = new Circle(new Point(30,50) , 10);
		Vector v1 = new Vector(10,10);
		Ball b1 = new Ball(c1,v1);
		Ball[] arr1 = new Ball[] { b1 };
		
		Circle c2 = new Circle(new Point(30,50) , 10);
		Vector v2 = new Vector(10,10);
		Ball b2 = new Ball(c2,v2);
		Ball[] arr2 = new Ball[] { b2 };
		
		assertEquals( b1 , b2 );
		Arrays.equals( arr1 , arr2);
		
		
	}

}
