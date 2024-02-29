package breakout;

import java.awt.Color;
import java.util.Arrays;

import breakout.utils.*;

public class ReplicatingPaddleState extends PaddleState {
	
	/**
	 * count = the number of balls that will be generated upon hitting this paddle + 1.
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

	public ReplicatingPaddleState(Point center, Color[] possCols, Color curColor, int count) {
		super(center,
				new Color[] { possCols[2], possCols[1], possCols[1] }, // TODO: FIXME
				curColor);
		this.count = count;
	}

	@Override
	public PaddleState stateAfterHit() {
		if (count > 2) {
			return this;
		} else {
			PaddleState res =
					new ReplicatingPaddleState(getCenter(), getPossibleColors(), getCurColor(), 1);
			return res;
		}
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
