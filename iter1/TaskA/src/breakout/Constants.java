package breakout;

import java.awt.Color;

import breakout.utils.Point;
import breakout.utils.Vector;

/**
 * @immutable
 */
public class Constants {
	
	public static final Point ORIGIN = new Point(0,0);
	
	//paddle dims, speed
	public static Vector PADDLE_VEL = new Vector(15, 0);
	public static final int PADDLE_HEIGHT = 500;
	public static final int PADDLE_WIDTH = 3000;
	
	//ball dim, speed
	public static final int INIT_BALL_DIAMETER = 700;
	public static final Vector INIT_BALL_VELOCITY = new Vector(5,7);
	
	//dimensions of typical game field / blocks
	public static final int HEIGHT = 30000;
	public static final int WIDTH = 50000;
	
	public static int BLOCK_LINES = 8;
	public static int BLOCK_COLUMNS = 10;
	
	public static int BLOCK_WIDTH = WIDTH / BLOCK_COLUMNS;
	public static int BLOCK_HEIGHT = HEIGHT / BLOCK_LINES;
	
	/**
	 * @creates | result
	 */
	public static Color[] TYPICAL_PADDLE_COLORS() {
		return new Color[] {Color.green, Color.magenta, Color.orange };
	}
	
	public static final Color BALL_COLOR = Color.yellow;
	public static final Color BLOCK_COLOR = new Color(0x80,0x00,0xff);
	
	//time increments
	public static final int BALL_DELAYMS = 20;
	public static int MAX_ELAPSED_TIME = 50;
	
	//game map examples
	public static final String initMap = """
##########
##########
##########
##########
     o

     =

""";
	
	private Constants() {};
}
