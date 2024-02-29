package breakout.utils;

/**
 * Represents a circle in a 2-dimensional integer coordinate system. 
 *
 * @immutable
 * @invar | getCenter() != null
 * @invar | getDiameter() > 0
 */
public class Circle {
	
	/**
	 * @invar | center != null
	 * @invar | diameter > 0 
	 */
	private final Point center;
	private final int diameter;
	
	/**
	 * Construct a circle with a given center point and diameter.
	 * @pre | center != null
	 * @pre | diameter > 0 
	 * @post | getCenter() == center
	 * @post | getDiameter() == diameter
	 */
	public Circle(Point center, int diameter) {
		this.center = center;
		this.diameter = diameter;
	}
	
	/**
	 * Return the center of this circle.
	 * @post | result != null
	 */
	public Point getCenter() {
		return center;
	}

	/**
	 * Return the diameter of this circle
	 * @post | result > 0
	 */ 
	public int getDiameter() {
		return diameter;
	}

	/**
	 * Return the radius of this circle
	 *
	 * @post | result == getDiameter() /2
	 */ 
	public int getRadius() {
		return diameter / 2;
	}
	
	/**
	 * Return the rightmost point of this circle.
	 * 
	 * @post | result != null 
	 * @post | result.equals(getCenter().plus(Vector.RIGHT.scaled(getRadius())))
	 */
	public Point getRightmostPoint() {
		return getOutermostPoint(Vector.RIGHT);
	}
	
	/**
	 * Return the rightmost point of this circle.
	 * 
	 * @post | result != null 
	 * @post | result.equals(getCenter().plus(Vector.LEFT.scaled(getRadius())))
	 */
	public Point getLeftmostPoint() {
		return getOutermostPoint(Vector.LEFT);
	}
	
	/**
	 * Return the rightmost point of this circle.
	 * 
	 * @post | result != null 
	 * @post | result.equals(getCenter().plus(Vector.UP.scaled(getRadius())))
	 */
	public Point getTopmostPoint() {
		return getOutermostPoint(Vector.UP);
	}
	/**
	 * Return the rightmost point of this circle.
	 * 
	 * @post | result != null 
	 * @post | result.equals(getCenter().plus(Vector.DOWN.scaled(getRadius())))
	 */
	public Point getBottommostPoint() {
		return getOutermostPoint(Vector.DOWN);
	}
	/**
	 * Return the top-left point of this circle's surrounding axis-aligned rectangle.
	 * 
	 * @post | result != null 
	 * @post | result.equals(getCenter().plus(new Vector(-1,-1).scaled(getRadius())))
	 */
	public Point getTopLeftPoint() {
		return center.plus(new Vector( -1,-1 ).scaled(getRadius()));
	}
	/**
	 * Return the bottom-right point of this circle's surrounding axis-aligned rectangle.
	 * 
	 * @post | result != null 
	 * @post | result.equals(getCenter().plus(new Vector(1,1).scaled(getRadius())))
	 */
	public Point getBottomRightPoint() {
		return center.plus(new Vector( 1, 1 ).scaled(getRadius()));
	}
	
	/**
	 * Return the outermost point of this circle in the given direction `dir`.
	 * 
	 * @pre | dir != null
	 * @pre | dir.product(dir) == 1
	 * @post | result != null 
	 * @post | result.equals(getCenter().plus(dir.scaled(getRadius())))
	 */
	public Point getOutermostPoint(Vector dir) {
		return center.plus(dir.scaled(diameter/2));
	}

	/**
	 * Return a circle with the given `center` and the same diameter as this one.
	 * @pre | c != null
	 * @post | result != null 
	 * @post | result.getCenter() == c
	 * @post | result.getDiameter() == getDiameter()
	 */
	public Circle withCenter(Point c) {
		return new Circle(c, diameter);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + center.hashCode();
		result = prime * result + diameter;
		return result;
	}

	@Override
	public boolean equals(Object other) {
		if (this == other)
			return true;
		if (other == null)
			return false;
		if (getClass() != other.getClass())
			return false;
		Circle circ = (Circle) other;
		if (diameter != circ.diameter)
			return false;
		if (! center.equals(circ.getCenter()))
			return false;
		return true;
	}
}
