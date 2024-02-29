package breakout;

import breakout.utils.Rect;

/**
 * Represents the state of a block in the breakout.good game.
 * @immutable
 * @invar | getLocation() != null
 */
public class BlockState {
	/**
	 * Creates a new BlockState object with the provided `location`
	 *
	 * @invar | location != null
	 */
	private final Rect location;

	/**
	 * Construct a new block state at a given `location`
	 *
	 * @pre | location != null
	 * @post | getLocation() == location
	 */
	public BlockState(Rect location) {
		this.location = location;
	}

	/**
	 * Return this block's location.
	 *
	 * @post | result != null
	 */
	public Rect getLocation() {
		return location;
	}
}
