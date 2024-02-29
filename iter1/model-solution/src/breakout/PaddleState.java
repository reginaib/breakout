package breakout;

import breakout.utils.Point;
import breakout.utils.Rect;
import breakout.utils.Vector;

import java.awt.Color;
import java.util.Arrays;

/**
 * Represents the state of a paddle in the breakout.good game.
 *
 * @immutable
 * @invar | getCenter() != null
 * @invar | getPossibleColors() != null
 * @invar | getPossibleColors().length >= 1
 * @invar | Arrays.stream(getPossibleColors()).allMatch(c -> c != null)
 */
public class PaddleState {
	

	/**
	 * @invar | center != null
	 */
	private final Point center;
	/**
	 * @invar | possibleColors != null
	 * @invar | possibleColors.length >= 1
	 * @invar | Arrays.stream(possibleColors).allMatch(c -> c != null)
	 * @representationObject
	 */
	private final Color[] possibleColors;

	/**
	 * Construct a paddle located around a given center in the field.
	 * @pre | center != null
	 * @pre | possibleColors != null
	 * @pre | possibleColors.length >= 1
	 * @pre | Arrays.stream(possibleColors).allMatch(c -> c != null)
	 * @post | getCenter().equals(center)
	 * @post | Arrays.equals(possibleColors , getPossibleColors())
	 */
	public PaddleState(Point center, Color[] possibleColors) {
		this.center = center;
		this.possibleColors = possibleColors.clone(); //we consider Color as immutable
	}
	
	/**
	 * Return the center point of this paddle.
	 */
	public Point getCenter() {
		return center;
	}
	
	/**
	 * @creates | result
	 */
	public Color[] getPossibleColors() {
		return possibleColors.clone();
	}

	/**
	 * Return the rectangle occupied by this paddle in the field.
	 * 
	 * @post | result != null
	 * @post | result.getTopLeft().equals(getCenter().plus(new Vector(-Constants.PADDLE_WIDTH/2,-Constants.PADDLE_HEIGHT/2)))
	 * @post | result.getBottomRight().equals(getCenter().plus(new Vector(Constants.PADDLE_WIDTH/2,Constants.PADDLE_HEIGHT/2)))
	 */
	public Rect getLocation() {
		Vector halfDiag = new Vector(-Constants.PADDLE_WIDTH/2,-Constants.PADDLE_HEIGHT/2);
		return new Rect(center.plus(halfDiag), center.plus(halfDiag.scaled(-1)));
	}

}
