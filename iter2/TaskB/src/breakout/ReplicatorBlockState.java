package breakout;

import java.awt.Color;
import java.util.Arrays;
import breakout.utils.Rect;

public class ReplicatorBlockState extends NormalBlockState {

	private static final Color COLOR = new Color(100, 149, 237);

	/**
	 * @pre | location != null
	 */
	public ReplicatorBlockState(Rect location) {
		super(location);
	}

	@Override
	/**
	 * TODO
	 * @pre | paddleState != null
	 * @creates | result
	 * @post | result != null
	 * @post | result.getCenter().equals(paddleState.getCenter())
	 * @post | Arrays.equals(result.getPossibleColors(), paddleState.getPossibleColors())
	 * @post | result.getCurColor().equals(paddleState.getCurColor())
	 */
	public PaddleState paddleStateAfterHit(PaddleState paddleState) {
		return new ReplicatingPaddleState(paddleState.getCenter(), paddleState.getPossibleColors(), paddleState.getCurColor(), Constants.MAX_BALL_REPLICAS + 1);
	}

	@Override
	public Color getColor() {
		return COLOR;
	}

}
