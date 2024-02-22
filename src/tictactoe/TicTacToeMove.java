package tictactoe;

import static tictactoe.TicTacToeBoard.SIZE;

public class TicTacToeMove {
	
	/** The x-coordinate at which the player will make a move. */
	public final int x;
	
	/** The y-coordinate at which the player will make a move. */
	public final int y;
	
	/**
	 * Constructs a move that places the player whose turn it is at
	 * the given position.
	 * 
	 * @param x The x-coordinate at which the player will be placed.
	 *          Must be a non-negative integer less than {@code TicTacToeBoard.SIZE}
	 * @param y The y-coordinate at which the player will be placed.
	 *          Must be a non-negative integer less than {@code TicTacToeBoard.SIZE}
	 */
	public TicTacToeMove(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Computes the resulting board reached by executing this
	 * move on the given starting board.
	 * 
	 * @param startingBoard The board at which this move is being executed.
	 * @return The board reached by executing this move.
	 */
	public TicTacToeBoard nextBoard(TicTacToeBoard startingBoard) {
		return startingBoard.playAt(x, y);
	}

	/**
	 * Computes the reward given by executing this move on the
	 * given board.
	 * 
	 * @param board The board on which this move is being executed.
	 * @param player The player playing on the given board.
	 * @return The reward (for the player) given by executing this move.
	 */
	public Reward reward(TicTacToeBoard board, Player player) {
		final TicTacToeBoard nextBoard = nextBoard(board);
		
		if (nextBoard.inWinState(player))
			return Reward.WIN;
		
		else if (nextBoard.inWinState(player.opponent()))
			return Reward.LOSS;
		
		else if (nextBoard.isTied())
			return Reward.TIE;
		
		return Reward.NONE;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean equals(Object obj) {
		// Checks if a TicTacToe move
		if (!obj.getClass().equals(getClass()))
			return false;
		
		// Checks if the move are the same
		final TicTacToeMove move = (TicTacToeMove)obj;
		return this.x == move.x && this.y == move.y;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public int hashCode() {
		return x + SIZE*y;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public String toString() {
		return "Marking (" + x + ", " + y + ")";
	}

}
