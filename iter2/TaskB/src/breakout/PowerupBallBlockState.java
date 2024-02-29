package breakout;

import java.awt.Color;

import breakout.utils.Rect;
import breakout.utils.Circle;

public class PowerupBallBlockState extends NormalBlockState {

	private static final Color COLOR = new Color(215, 0, 64);

	public PowerupBallBlockState(Rect location) {
		super(location);
	}

	/**
	 * @pre | b != null
	 * @creates | result
	 * @post | result != null
	 */
	@Override
	public Ball ballStateAfterHit(Ball b) {
		Circle superLoc = new Circle( b.getCenter(), Constants.INIT_BALL_DIAMETER + 600);
		return new SuperChargedBall(
				superLoc,
				b.getVelocity(),
				Constants.SUPERCHARGED_BALL_LIFETIME);
	}

	@Override
	public Color getColor() {
		return COLOR;
	}

}
