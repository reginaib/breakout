package breakout;

import java.awt.Color;
import java.util.Arrays;

import breakout.utils.*;

public class ReplicatingPaddleState extends PaddleState {
	
	/**
	 * count = the number of balls that will be generated upon hitting this paddle + 1.
	 * @invar | count >= 2
	 */
	private int count;

	@Override
	public int numberOfBallsAfterHit() {
		return count;
	}
	
	/**
	 * Returns the remaining amount of ball replications this paddle will perform + 1
	 */
	public int getCount() {
		return count;
	}

	/**
	 * Construct a paddle located around a given center in the field.
	 * @pre | center != null
	 * @pre | possCols != null
	 * @pre | curColor != null
	 * @pre | count >= 2
	 * @pre | possCols.length == 3
	 * @pre | Arrays.stream(possCols).allMatch(c -> c != null)
	 * @pre | Arrays.stream(possCols).anyMatch(c -> c.equals(curColor))
	 * @pre | Arrays.stream(possCols).distinct().count() == possCols.length
	 */
	public ReplicatingPaddleState(Point center, Color[] possCols, Color curColor, int count) {
		super(center, possCols, curColor);
		this.count = count;
	}

	@Override
	public PaddleState stateAfterHit() {
		if (count > 2) {
			count --;
			return this;
		}
		tossCurColor();  // randomize current color
		return new NormalPaddleState(getCenter(), getPossibleColors(), getCurColor());
	}
	
	@Override
	/**
	 * TODO
	 * @creates | result
	 * @post | result != null
	 * @post | result.length == 3
	 * @post | Arrays.stream(result).anyMatch(c -> c.equals(getCurColor()))
	 */
	public Color[] getActualColors() {
		return getPossibleColors();
	}
	
	@Override
	/**
	 * TODO
	 * @creates | result
	 * @post | result != null
	 * @post | result != this
	 * @post | result.equalContent(this)
	 */
	public PaddleState reproduce() {
		return new ReplicatingPaddleState(getCenter(), getPossibleColors(), getCurColor(), count);
	}
	
	@Override
	/**
	 * LEGIT
	 */
	public boolean equalContent(PaddleState other) {
		if (getClass() != other.getClass()) { return false; }
		ReplicatingPaddleState oth = (ReplicatingPaddleState) other;
		if (!getCenter() .equals( oth.getCenter() )) { return false; }
		if ( ! Arrays.equals(getPossibleColors(), oth.getPossibleColors())) { return false; }
		if ( ! getCurColor() .equals( oth.getCurColor() )) { return false; }
		if ( count != oth.getCount() ) { return false; }
		return true;
	}
}
