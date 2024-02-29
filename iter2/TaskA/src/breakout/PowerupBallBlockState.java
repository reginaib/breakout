package breakout;

import java.awt.Color;

import breakout.utils.Rect;
import breakout.utils.Circle;

public class PowerupBallBlockState extends NormalBlockState {

	private static final Color COLOR = new Color(215, 0, 64);

	public PowerupBallBlockState(Rect location) {
		super(location);
	}

	@Override
	public Ball ballStateAfterHit(Ball b) {
		int superDiam;
		if (b.getLocation().getDiameter() > Constants.INIT_BALL_DIAMETER) {
			superDiam = b.getLocation().getDiameter();
		}
		else {
			superDiam = Constants.INIT_BALL_DIAMETER + 600;
		}
		Circle superLoc = new Circle( b.getCenter(), superDiam);
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
