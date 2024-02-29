package breakout;



import breakout.utils.*;

public class NormalBall extends Ball {

	public NormalBall(Circle location, Vector velocity) {
		super(location, velocity);
	}

	/**
	 * Update the BallState after hitting a block at a given location.
	 * 
	 * @pre | rect != null
	 * @pre | collidesWith(rect)
	 * @post | getCenter().equals( old(getCenter() ))
	 * @post | getVelocity().equals(old(getVelocity()).mirrorOver(rect.collideWith(old(getLocation())))) || getVelocity().equals( old( getVelocity() ))
	 * @mutates | this
	 */
	@Override
	public void hitBlock(Rect rect, boolean destroyed) {
		setVelocity( bounceOn (rect) );
	}

	@Override
	/**
	 * LEGIT
	 */
	public void move(Vector v, int elapsedTime) {
		setLocation ( new Circle(getLocation().getCenter().plus(v), getLocation().getDiameter()) );
	}

	@Override
	/**
	 * LEGIT
	 */
	public void hitPaddle(Rect rect, Vector paddleVel) {
		Vector nspeed = bounceOn( rect );
		Vector mbMore = nspeed .plus(paddleVel.scaledDiv(5));
		if (mbMore.getSquareLength() <= Constants.MBS2) { setVelocity( mbMore ); }
		else { setVelocity( nspeed ); };
	}
	


	@Override
	public void hitWall(Rect rect) {
		setVelocity( bounceOn( rect ) );
	}


	@Override
	public Ball cloneWithVelocity(Vector v) {
		return new NormalBall(getLocation(), v);
	}

	/**
	 * TODO
	 *
	 * @post | result != null
	 * @post | result.getLocation().equals(getLocation())
	 * @post | result.getVelocity().equals(getVelocity())
	 */
	@Override
	public Ball backToNormal() {
		return this;
	}

}
