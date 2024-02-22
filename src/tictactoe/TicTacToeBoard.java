package tictactoe;

import java.util.ArrayList;
import java.util.Arrays;

public class TicTacToeBoard {
	
	/** The size of the Tic Tac Toe board. */
	public static final int SIZE = 3;
	
	/** The default board, where no players have made any moves. Player X is playing. */
	public static final TicTacToeBoard DEFAULT;
	
	static {
		final Player[] defaultLayout = new Player[SIZE*SIZE];
		Arrays.fill(defaultLayout, Player.NONE);
		DEFAULT = new TicTacToeBoard(defaultLayout);
	}
	
	/** The Tic Tac Toe board layout. */
	private final Player[] board;
	
	/**
	 * Creates a new board with the given layout. The X player goes first.
	 * 
	 * @param board The board for the Tic Tac Toe game, where
	 *              the player at index {@code x+SIZE*y} corresponds to
	 *              the player on the board at position {@code (x, y)}.
	 *              Must be of length {@code SIZE^2}.
	 */
	public TicTacToeBoard(Player...board) {
		this.board = board.clone();
	}
	
	/**
	 * Has the player whose turn it currently is play at the given position.
	 * 
	 * @param x The x-coordinate of the position at which to play.
	 *          Must be a non-negative integer less than {@code SIZE}.
	 * @param y The y-coordinate of the position at which to play.
	 *          Must be a non-negative integer less than {@code SIZE}.
	 * @return The board resulting from the play.
	 */
	public TicTacToeBoard playAt(int x, int y) {
		final Player[] board = this.board.clone();
		board[x + SIZE*y] = turnOf();
		return new TicTacToeBoard(board);
	}
	
	/**
	 * Gets the player at the given position.
	 * 
	 * @param x The x-coordinate of the board.
	 *          Must be a non-negative integer less than {@code TicTacToeBoard.SIZE}.
	 * @param y The y-coordinate of the board.
	 *          Must be a non-negative integer less than {@code TicTacToeBoard.SIZE}.
	 * @return The player at the given position.
	 */
	private Player get(int x, int y) {
		return board[x + SIZE*y];
	}
	
	/**
	 * @return The player whose turn it currently is.
	 */
	public Player turnOf() {
		
		// Counts how many turns have already been played
		int turns = 0;
		for (int i = 0; i < board.length; i++)
			turns += board[i] == Player.NONE ? 0 : 1;
		
		return turns%2 == 0 ? Player.X : Player.O; 
	}

	/**
	 * @return The possible moves from this board. If the
	 *         board corresponds to a winning/tied board,
	 *         returns an empty array.
	 */
	public TicTacToeMove[] possibleMoves() {
		
		if (isTerminal())
			return new TicTacToeMove[] {};
		
		// Stores the possible moves
		final ArrayList<TicTacToeMove> moves = new ArrayList<TicTacToeMove>();

		// Checks for each possible move
		for (int x = 0; x < SIZE; x++)
			for (int y = 0; y < SIZE; y++)
				if (get(x, y) == Player.NONE)
					moves.add(new TicTacToeMove(x, y));
		
		return moves.toArray(new TicTacToeMove[] {});
	}
	
	/**
	 * Checks whether the game is in a win state for the given player.
	 * 
	 * @param player The player for which to check the win state. 
	 * @return Whether the game is in a win state for the given player.
	 */
	public boolean inWinState(Player player) {
		
		// Checks rows
		for (int row = 0; row < SIZE; row++) {
			boolean isWin = get(0, row) == player;
			for (int col = 1; col < SIZE; col++)
				isWin = isWin && get(col-1, row) == get(col, row);
			if (isWin)
				return true;
		}
		
		// Checks columns
		for (int col = 0; col < SIZE; col++) {
			boolean isWin = get(col, 0) == player;
			for (int row = 1; row < SIZE; row++)
				isWin = isWin && get(col, row-1) == get(col, row);
			if (isWin)
				return true;
		}
		
		// Checks main diagonal
		boolean isWin = get(0, 0) == player;
		for (int i = 1; i < SIZE; i++) {
			isWin = isWin && get(i-1, i-1) == get(i, i);
		}
		if (isWin)
			return true;
		
		// Checks anti diagonal
		isWin = get(SIZE-1, 0) == player;
		for (int i = 1; i < SIZE; i++) {
			isWin = isWin && get((SIZE-1) - (i-1), i-1) == get((SIZE-1) - i, i);
		}
		if (isWin)
			return true;
		
		return false;
	}
	
	/**
	 * @return Whether the board is in a tie.
	 */
	public boolean isTied() {
		int turns = 0;
		for (int i = 0; i < board.length; i++)
			turns += board[i] == Player.NONE ? 0 : 1;
		return turns == SIZE*SIZE;
	}

	/**
	 * @return Whether this board is in a winning/tied state.
	 */
	public boolean isTerminal() {
		return inWinState(Player.X) || inWinState(Player.O) || isTied();
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean equals(Object obj) {
		// Checks if a TicTacToe board
		if (!obj.getClass().equals(getClass()))
			return false;
		
		// Checks if the boards are the same
		final TicTacToeBoard board = (TicTacToeBoard)obj;
		return Arrays.equals(this.board, board.board);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public int hashCode() {
		int hash = 0;
		int sizePower = 1;
		
		for (int i = 0; i < board.length; i++) {
			hash += sizePower * board[i].ordinal();
			sizePower *= SIZE;
		}
		
		return hash;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public String toString() {
		
		String board = "";
		
		// Puts each player's position in the correct spot
		for (int row = 0; row < SIZE; row++) {
			for (int col = 0; col < SIZE; col++) {
				final Player player = get(col, row);
				if (player == Player.X)
					board += "X";
				else if (player == Player.O)
					board += "O";
				else
					board += " ";
			}
			board += "\n";
		}
		
		return board;
	}

}
