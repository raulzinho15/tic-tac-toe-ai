package tictactoe;

public enum Player {

	O(), X(), NONE();
	
	/**
	 * Computes the player that is not this player.
	 * 
	 * @param player The player whose opposite will be returned.
	 * @return The opponent player. Is NONE if NONE is inputed.
	 */
	public Player opponent() {
		if (this == O)
			return X;
		else if (this == X)
			return O;
		return NONE;
	}
}
