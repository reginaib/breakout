package breakout;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import breakout.utils.Circle;
import breakout.utils.Point;
import breakout.utils.Rect;
import breakout.utils.Vector;



/**
 * Represents the current state of a breakout.good game.
 *  
 * @invar | getBalls() != null
 * @invar | getBlocks() != null
 * @invar | getPaddle() != null
 * @invar | getCurPaddleColor() != null
 * @invar | Arrays.stream(getBalls()).allMatch(b -> b != null)
 * @invar | Arrays.stream(getBlocks()).allMatch(b -> b != null)
 * @invar | Arrays.stream(getPaddle().getPossibleColors()).anyMatch(c -> c.equals( getCurPaddleColor()))
 * @invar | getBottomRight() != null
 * @invar | Constants.ORIGIN.isUpAndLeftFrom(getBottomRight())
 * @invar | Arrays.stream(getBalls()).allMatch(b -> getField().contains(b.getLocation()))
 * @invar | Arrays.stream(getBlocks()).allMatch(b -> getField().contains(b.getLocation()))
 * @invar | getField().contains(getPaddle().getLocation())
 */
public class BreakoutState {

	
	/**
	 * @invar | bottomRight != null
	 * @invar | Constants.ORIGIN.isUpAndLeftFrom(bottomRight)
	 */
	private final Point bottomRight;
	/**
	 * @invar | balls != null
	 * @invar | Arrays.stream(balls).allMatch(b -> b != null)
	 * @invar | Arrays.stream(balls).allMatch(b -> getFieldInternal().contains(b.getLocation()))
	 * @representationObject
	 * @representationObjects
	 */
	private Ball[] balls;	
	/**
	 * @invar | blocks != null
	 * @invar | Arrays.stream(blocks).allMatch(b -> b != null)
	 * @invar | Arrays.stream(blocks).allMatch(b -> getFieldInternal().contains(b.getLocation()))
	 * @representationObject
	 */
	private BlockState[] blocks;
	/**
	 * @invar | paddle != null
	 * @invar | getFieldInternal().contains(paddle.getLocation())
	 */
	private PaddleState paddle;
	/**
	 * @invar | curPaddleColor != null
	 * @representationObject
	 * @invar | Arrays.stream(paddle.getPossibleColors()).anyMatch(c -> c.equals(curPaddleColor))
	 */
	private Color curPaddleColor;

	private final Rect topWall;
	private final Rect rightWall;
	private final Rect leftWall;
	private final Rect[] walls;

	/**
	 * Construct a new BreakoutState with the given balls, blocks, paddle.
	 * 
	 * @throws IllegalArgumentException | balls == null
	 * @throws IllegalArgumentException | blocks == null
	 * @throws IllegalArgumentException | bottomRight == null
	 * @throws IllegalArgumentException | paddle == null
	 * @throws IllegalArgumentException | Arrays.stream(balls).allMatch(b -> b != null)
	 * @throws IllegalArgumentException | Arrays.stream(blocks).allMatch(b -> b != null)
	 * @throws IllegalArgumentException | !Constants.ORIGIN.isUpAndLeftFrom(bottomRight)
	 * @throws IllegalArgumentException | !(new Rect(Constants.ORIGIN,bottomRight)).contains(paddle.getLocation())
	 * @throws IllegalArgumentException | !Arrays.stream(blocks).allMatch(b -> (new Rect(Constants.ORIGIN,bottomRight)).contains(b.getLocation()))
	 * @throws IllegalArgumentException | !Arrays.stream(balls).allMatch(b -> (new Rect(Constants.ORIGIN,bottomRight)).contains(b.getLocation()))
	 * @post | Arrays.equals(getBalls(),balls)
	 * @post | Arrays.equals(getBlocks(),blocks)
	 * @post | getBottomRight().equals(bottomRight)
	 * @post | getPaddle().equals(paddle)
	 */
	public BreakoutState(Ball[] balls, BlockState[] blocks, Point bottomRight, PaddleState paddle) {
		if( balls == null) throw new IllegalArgumentException();
		if( blocks == null) throw new IllegalArgumentException();
		if( bottomRight == null) throw new IllegalArgumentException();
		if( paddle == null) throw new IllegalArgumentException();
		
		if (Arrays.stream(balls).anyMatch(b -> b == null)) throw new IllegalArgumentException();
		if (Arrays.stream(blocks).anyMatch(b -> b == null)) throw new IllegalArgumentException();

		if(!Constants.ORIGIN.isUpAndLeftFrom(bottomRight)) throw new IllegalArgumentException();
		this.bottomRight = bottomRight;
		if(!getFieldInternal().contains(paddle.getLocation())) throw new IllegalArgumentException();
		if(!Arrays.stream(blocks).allMatch(b -> getFieldInternal().contains(b.getLocation()))) throw new IllegalArgumentException();
		if(!Arrays.stream(balls).allMatch(b -> getFieldInternal().contains(b.getLocation()))) throw new IllegalArgumentException();
	
		// deep copy in
	    this.balls= new Ball[balls.length];
	    for (int i = 0 ; i < balls.length ; i++) {
	    	this.balls[i] = balls[i].reproduce();
	    }
		this.blocks = blocks.clone();
		this.paddle = paddle;
		this.curPaddleColor = paddle.getPossibleColors()[0];

		this.topWall = new Rect( new Point(0,-1000), new Point(bottomRight.getX(),0));
		this.rightWall = new Rect( new Point(bottomRight.getX(),0), new Point(bottomRight.getX()+1000,bottomRight.getY()));
		this.leftWall = new Rect( new Point(-1000,0), new Point(0,bottomRight.getY()));
		this.walls = new Rect[] {topWall,rightWall, leftWall };
	}

	/**
	 * Return the balls of this BreakoutState.
	 * 
	 * @creates | result
	 */
	public Ball[] getBalls() {
		//deep copy out
		Ball[] res = new Ball[balls.length];
		for (int i = 0 ; i < balls.length ; i++) {
			res[i] = balls[i].reproduce();
		}
		return res;
	}

	/**
	 * Return the blocks of this BreakoutState. 
	 *
	 * @creates | result
	 */
	public BlockState[] getBlocks() {
		return blocks.clone();
	}

	/**
	 * Return the paddle of this BreakoutState. 
	 */
	public PaddleState getPaddle() {
		return paddle;
	}
	
	public Color getCurPaddleColor() {
		return curPaddleColor;
	}
	
	/**
	 * randomly choose color within paddle.getPossibleColors() 
	 */
	public void tossPaddleColor() {
		Random rand = new Random(); //
		int randInt = rand.nextInt( paddle.getPossibleColors().length );
		curPaddleColor = paddle.getPossibleColors()[randInt];
	}

	/**
	 * Return the point representing the bottom right corner of this BreakoutState.
	 * The top-left corner is always at Coordinate(0,0). 
	 */
	public Point getBottomRight() {
		return bottomRight;
	}

	// internal version of getField which can be invoked in partially inconsistent states
	private Rect getFieldInternal() {
		return new Rect(Constants.ORIGIN, bottomRight);
	}
	
	/**
	 * Return a rectangle representing the game field.
	 * @post | result != null
	 * @post | result.getTopLeft().equals(Constants.ORIGIN)
	 * @post | result.getBottomRight().equals(getBottomRight())
	 */
	public Rect getField() {
		return getFieldInternal();
	}
	
	private void bounceWalls(Ball ball) {
		for (Rect wall : walls) {
			ball.hitRect(wall);
		}
	}
	


	private Ball removeDead(Ball ball) {
		if( ball.getLocation().getBottommostPoint().getY() > bottomRight.getY()) { return null; }
		else { return ball; }
	}

	private void clampBall(Ball b) {
		Circle loc = getFieldInternal().constrain(b.getLocation());
		b.setLocation(loc);
	}
	
	private void collideBallBlocks(Ball ball) {
		for (BlockState block : blocks) {
			boolean res = ball.hitRect( block .getLocation() );
			if (res) { 
				removeBlock(block);
			}
		}

	}

	private void collideBallPaddle(Ball ball, Vector paddleVel) {
		boolean changed = ball.hitPaddle(paddle.getLocation(), paddleVel);
		if (changed) tossPaddleColor();
	}

	private void removeBlock(BlockState block) {
		ArrayList<BlockState> nblocks = new ArrayList<BlockState>();
		for( BlockState b : blocks ) {
			if(b != block) {
				nblocks.add(b);
			}
		}
		blocks = nblocks.toArray(new BlockState[] {});
	}

	/**
	 * Move all moving objects one step forward.
	 * 
	 * @pre | paddleDir == -1 || paddleDir == 0 || paddleDir == 1
	 * 
	 * @pre | elapsedTime >= 0
	 * @pre | elapsedTime <= Constants.MAX_ELAPSED_TIME
	 * 
	 * @mutates | this
	 * @mutates | ...getBalls()
	 */
	public void tick(int paddleDir, int elapsedTime) {
		stepBalls(elapsedTime);
		bounceBallsOnWalls();
		removeDeadBalls();
		bounceBallsOnBlocks();
		bounceBallsOnPaddle(paddleDir);
		clampBalls();
		balls = Arrays.stream(balls).filter(x -> x != null).toArray(Ball[]::new);
	}
	
	/**
	 * ticking by 20ms increments
	 */
	public void tickDuring(int elapsedTime) {
		for (int i = 0 ; i + 20 <= elapsedTime ; i += 20) {
			tick(0, 20);
		}
		if( elapsedTime % 20 != 0) { 
		  tick(0, elapsedTime % 20);
		}
	}

	private void clampBalls() {
		for(int i = 0; i < balls.length; ++i) {
			if(balls[i] != null) {
				clampBall(balls[i]);
			}		
		}
	}

	/**
	 * @pre | paddleDir == -1 || paddleDir == 0 || paddleDir == 1
	 */
	private void bounceBallsOnPaddle(int paddleDir) {
		Vector paddleVel = Constants.PADDLE_VEL.scaled(paddleDir);
		for(int i = 0; i < balls.length; ++i) {
			if(balls[i] != null) {
				collideBallPaddle(balls[i], paddleVel);
			}
		}
	}

	private void bounceBallsOnBlocks() {
		for(int i = 0; i < balls.length; ++i) {
			if(balls[i] != null) {
				collideBallBlocks(balls[i]);
			}
		}
	}

	private void removeDeadBalls() {
		for(int i = 0; i < balls.length; ++i) {
			balls[i] = removeDead(balls[i]);
		}
	}

	private void bounceBallsOnWalls() {
		for(int i = 0; i < balls.length; ++i) {
			bounceWalls(balls[i]);
		}
	}

	
	private void stepBalls(int elapsedTime) {
		for(int i = 0; i < balls.length; ++i) {
			balls[i].move(balls[i].getVelocity().scaled(elapsedTime));
		}
	}
		
//		for(int i = 0; i < balls.length; ++i) {
//			Point newcenter = balls[i].getLocation().getCenter().plus(balls[i].getVelocity());
//			balls[i] = new Ball(balls[i].getLocation().withCenter(newcenter),balls[i].getVelocity());
//		}

	/**
	 * Move the paddle right.
	 * 
	 * @mutates | this
	 */
	public void movePaddleRight(int elapsedTime) {
		Point ncenter = paddle.getCenter().plus(Constants.PADDLE_VEL.scaled(elapsedTime));
		this.paddle = new PaddleState(
				getField().minusMargin(Constants.PADDLE_WIDTH/2,0).constrain(ncenter),
				this.paddle.getPossibleColors()
			);
	}

	/**
	 * Move the paddle left.
	 * 
	 * @mutates | this
	 */
	public void movePaddleLeft(int elapsedTime) {
		Point ncenter = paddle.getCenter().plus(Constants.PADDLE_VEL.scaled(-elapsedTime));
		this.paddle = new PaddleState(
				getField().minusMargin(Constants.PADDLE_WIDTH/2,0).constrain(ncenter),
				this.paddle.getPossibleColors()
			);
	}

	/**
	 * Return whether this BreakoutState represents a game where the player has won.
	 * 
	 * @post | result == (getBlocks().length == 0 && !isDead())
	 * @inspects | this
	 */
	public boolean isWon() {
		return getBlocks().length == 0 && !isDead();
	}

	/**
	 * Return whether this BreakoutState represents a game where the player is dead.
	 * 
	 * @post | result == (getBalls().length == 0)
	 * @inspects | this
	 */
	public boolean isDead() {
		return getBalls().length == 0;
	}
}
