package breakout;

import breakout.utils.Circle;
import breakout.utils.Point;
import breakout.utils.Rect;
import breakout.utils.Vector;

/**
 * Represents the state of a ball in the breakout game.
 * 
 * @invar | getLocation() != null
 * @invar | getVelocity() != null
 */
public class Ball {
	
	/**
	 * @invar | location != null
	 * @invar | velocity != null
	 */
	private Circle location;
	private Vector velocity;
	
	
	/**
	 * Construct a new ball at a given `location`, with a given `velocity`.
 	 * @throws IllegalArgumentException | location == null
 	 * @throws IllegalArgumentException | velocity == null
 	 * @post | getLocation().equals(location)
 	 * @post | getVelocity().equals(velocity)
	 */
	public Ball(Circle location, Vector velocity) throws IllegalArgumentException {
		if (location == null) throw new IllegalArgumentException();
		if (velocity == null) throw new IllegalArgumentException();
		this.location = location; //OK since Circle is immutable
		this.velocity = velocity; //same
	}

	
	/**
	 * Return this ball's location.
	 */
	public Circle getLocation() {
		return location; //OK since Circle immutable
	}

	/**
	 * Return this ball's velocity.
	 */
	public Vector getVelocity() {
		return velocity; //Vector immutable
	}
	
	public void setLocation(Circle location) {
		this.location = location;
	}
	
	public void setVelocity(Vector velocity) {
		this.velocity = velocity;
	}
	
	/**
	 * @pre | v != null
	 */
	public void move(Vector v) {
			location = new Circle(getCenter().plus(v), getLocation().getDiameter());
	}

	
	/**
	 * Mirrors this ball speed if the ball "collidesWith" rect (ie both shapes collide and this ball moves toward rect). Returns true in this case.
	 * Else returns false.
	 * 
	 * @mutates | this
	 * @pre | rect != null
	 * @post | getLocation() .equals( old(getLocation() ))
	 * @post | ( (!old( collidesWith(rect))) && (old(getVelocity()) .equals( getVelocity()) ) && result == false) || 
	 * 	     | ( old( collidesWith( rect )) &&
	 *       |   (getVelocity() .equals( old(getVelocity()).mirrorOver(rect.collideWith(getLocation())) ) ) &&
	 *       |   (result == true)  )          
	 */
	public boolean hitRect(Rect rect) {
		if (collidesWith(rect)) {
			Vector coldir = rect.collideWith(location); //not null
			velocity = velocity.mirrorOver(coldir);
			return true;
		}
		else return false;
	}
	
	
	/**
	 * Check whether this ball collides with a given `rect`.
	 * Or rather whether the speed really needs to be updated upon collision (see Rect.collideWith)
	 * For instance if the ball is moving away from rect, this method returns false
	 * 
	 * @pre | rect != null
	 * @post | result == ((rect.collideWith(getLocation()) != null) &&
	 *       |            (getVelocity().product(rect.collideWith(getLocation())) > 0))
	 * @inspects | this
	 */
	public boolean collidesWith(Rect rect) {
		Vector coldir = rect.collideWith(getLocation());
		return coldir != null && (getVelocity().product(coldir) > 0);
	}
	
//	public void hitWall(Rect rect) {
//		velocity = bounceOn(rect);
//	}
	

	/**
	 * return true iff the ball `hitRect`s rect.
	 */
	public boolean hitPaddle(Rect rect, Vector paddleVel) {
		if (collidesWith(rect)) { //in case of a bounce, ball velocity is slightly affected by paddle
			velocity = velocity.plus(paddleVel.scaledDiv(5));
		}
		boolean res = hitRect(rect);
		return res;
	}

	/**
	 * Return this ball's center.
	 * 
	 * @post | getLocation().getCenter().equals(result)
	 */
	public Point getCenter() {
		return getLocation().getCenter();
	}
	
	@Override
	// TODO in theory hashCode should be modified as well
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ball other = (Ball) obj;
		if (! getLocation().equals(other.getLocation() ) )
			return false;
		if ( ! getVelocity().equals(other.getVelocity() ))
			return false;
		return true;
	}
	
	/**
	 * @post | this.equals(result)
	 * @creates | result
	 */
	public Ball reproduce() {
		return new Ball( getLocation() , getVelocity() );
	}
	
	/**
	 * to demonstrate how you should write tests in TaskBTestSuite
	 * @post | result == 35
	 */
	public int dummy() {
		return 35;
	}
	
}
