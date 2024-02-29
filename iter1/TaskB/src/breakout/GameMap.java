package breakout;

import java.util.ArrayList;
import java.util.Arrays;


import breakout.utils.Circle;
import breakout.utils.Point;
import breakout.utils.Rect;
import breakout.utils.Vector;

public class GameMap {


	private GameMap() { throw new AssertionError("This class is not intended to be instantiated"); }

	private static BlockState createBlock(Point topLeft) {
		Vector marginBL = new Vector(20,20);
		Vector size = new Vector(Constants.BLOCK_WIDTH-70,Constants.BLOCK_HEIGHT-70);
		Point blockTL = topLeft.plus(marginBL);
		Point blockBR = blockTL.plus(size);
		Rect loc = new Rect(blockTL,blockBR);
		return new BlockState(loc);
	}
	private static PaddleState createPaddle(Point topLeft) {
		Vector size = new Vector(Constants.BLOCK_WIDTH/2,Constants.BLOCK_HEIGHT/2);
		Point center = topLeft.plus(size);
		return new PaddleState(center, Constants.TYPICAL_PADDLE_COLORS());
	}
	private static Ball createBall(Point topLeft) {
		Vector centerD = new Vector(Constants.BLOCK_WIDTH/2,Constants.BLOCK_HEIGHT/2);
		Point center = topLeft.plus(centerD);
		int diameter = Constants.INIT_BALL_DIAMETER;
		return new Ball(new Circle(center,diameter), Constants.INIT_BALL_VELOCITY);
	}
	
	/**
	 * Return the initial breakout state represented by string `description`.
	 * @pre | description != null
	 * @post | result != null
	 */
	public static BreakoutState createStateFromDescription(String description) {
		String[] lines = description.split("\n", Constants.BLOCK_LINES);
		
		Vector unitVecRight = new Vector(Constants.BLOCK_WIDTH,0);
		Vector unitVecDown = new Vector(0,Constants.BLOCK_HEIGHT);
		BlockState[] blocks = new BlockState[Constants.BLOCK_COLUMNS*Constants.BLOCK_LINES];
		int nblock = 0;
		Ball[] balls = new Ball[Constants.BLOCK_COLUMNS*Constants.BLOCK_LINES];
		int nball = 0;
		PaddleState paddle = null;
		
		Point topLeft = new Point(0,0);
		assert lines.length <= Constants.BLOCK_LINES;
		for(String line : lines) {
			assert line.length() <= Constants.BLOCK_COLUMNS;
			Point cursor = topLeft;
			for(char c : line.toCharArray()) {
				switch(c) {
				case '#': blocks[nblock++] = createBlock(cursor); break;
				case 'o': balls[nball++] = createBall(cursor); break;
				case '=': paddle = createPaddle(cursor); break;
				}
				cursor = cursor.plus(unitVecRight);
			}
			topLeft = topLeft.plus(unitVecDown);
		}
		Point bottomRight = new Point(Constants.WIDTH, Constants.HEIGHT);
		
		return new BreakoutState(Arrays.stream(balls).filter(x -> x != null).toArray(Ball[]::new),
								 Arrays.stream(blocks).filter(x -> x != null).toArray(BlockState[]::new),
								 bottomRight, paddle);
	}
}
