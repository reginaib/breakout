package breakout;

import java.awt.Color;
import java.util.Arrays;

import breakout.utils.*;

public class NormalPaddleState extends PaddleState {

	public NormalPaddleState(Point center, Color[] possColors, Color curColor) {
		super(center, possColors, curColor);
	}

	/**
	 * TODO
	 * @post | result == 1
	 */
	@Override
	public int numberOfBallsAfterHit() {
		return 1;
	}

	/**
	 * TODO
	 * @post | result == this
	 */
	@Override
	public PaddleState stateAfterHit() {
		return this;
	}
	
	@Override
	/**
	 * TODO
	 * @creates | result
	 */
	public Color[] getActualColors() {
		return new Color[]{getCurColor()};
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
		return new NormalPaddleState(getCenter(), getPossibleColors(), getCurColor());
	}
	
	/**
	 * LEGIT
	 */
	public boolean equalContent(PaddleState other) {
		if (getClass() != other.getClass()) { return false; }
		if (! getCenter() .equals( other.getCenter()) ) { return false; }
		if ( ! Arrays.equals(getPossibleColors(), other.getPossibleColors())) { return false; }
		if ( ! getCurColor() .equals( other.getCurColor() )) { return false; }
		return true;
		
	}

	

}
