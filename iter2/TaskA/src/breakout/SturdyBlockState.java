package breakout;

import java.awt.Color;
import breakout.utils.Rect;

import static java.lang.Math.sqrt;

public class SturdyBlockState extends BlockState {

	private static final Color COLOR1 = new Color(160, 82, 45);
	private static final Color COLOR2 = new Color(123, 63, 0);
	private static final Color COLOR3 = new Color(92, 64, 51);
	private final int livesLeft;

	public SturdyBlockState(Rect location, int lives) {
		super(location);
		livesLeft = lives;
	}

	public int getLivesLeft() {
		return livesLeft;
	}
	
	@Override
	/**
	 * TODO
	 *
	 */
	public BlockState blockStateAfterHit(int squaredSpeed) {
		if (livesLeft > 1) {
			return new SturdyBlockState(getLocation(), livesLeft - 1);
		} else if (sqrt(squaredSpeed) >= Constants.BALL_SPEED_THRESH) {
			return null;
		} else {
			return this;
		}
	}

	@Override
	/**
	 * @pre | ballState != null
	 * @post | result == ballState
	 */
	public Ball ballStateAfterHit(Ball ballState) {
		return ballState;
	}

	@Override
	/**
	 * TODO
	 * @pre | paddleState != null
	 * @post | result == paddleState
	 */
	public PaddleState paddleStateAfterHit(PaddleState paddleState) {
		return paddleState;
	}

	@Override
	/**
	 * LEGIT
	 */
	public Color getColor() {
		switch (livesLeft) {
		case 1:
			return COLOR1;
		case 2:
			return COLOR2;
		default:
			return COLOR3;
		}
	}

}
