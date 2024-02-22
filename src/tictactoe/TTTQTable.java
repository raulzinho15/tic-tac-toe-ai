package tictactoe;

import java.util.HashMap;

import static tictactoe.TicTacToeBoard.SIZE;

public class TTTQTable {
	
	/** 
	 * Stores the Q-values for each board-move pair for the
	 * X player, where the key is the hash value of the
	 * board-move pair, and the value is the Q-value for it.
	 */
	private final HashMap<Integer, Double> xTable = new HashMap<Integer, Double>();
	
	/** 
	 * Stores the Q-values for each board-move pair for the
	 * O player, where the key is the hash value of the
	 * board-move pair, and the value is the Q-value for it.
	 */
	private final HashMap<Integer, Double> oTable = new HashMap<Integer, Double>();
	
	/** The learning rate for the Q tables. */
	private final double learningRate;
	
	/** The discount rate for the Q tables. */
	private final double discountRate;
	
	/**
	 * The rate at which update steps will choose
	 * the greedy action rather than a random action.
	 * */
	private final double greedyRate;
	
	/**
	 * Constructs a TicTacToe Q table collection, where there
	 * is a Q table for the X player (who goes first) and the
	 * O player.
	 * 
	 * @param learningRate The learning rate for this Q table.
	 * @param discountRate The discount rate for this Q table.
	 * @param greedyRate The rate at which update steps will choose the greedy action
	 *                   rather than a random action.
	 */
	public TTTQTable(double learningRate, double discountRate, double greedyRate) {
		this.learningRate = learningRate;
		this.discountRate = discountRate;
		this.greedyRate = greedyRate;
	}
	
	/**
	 * Computes the hash value of the given board-move pair.
	 * 
	 * @param board The board whose hash value will be computed.
	 * @param move The move whose hash value will be computed.
	 * @return The hash value of the board-move pair.
	 */
	private int hash(TicTacToeBoard board, TicTacToeMove move) {
		final int boardHash = board.hashCode();
		final int moveHash = move == null ? 0 : move.hashCode();
		return boardHash * SIZE*SIZE + moveHash; 
	}
	
	/**
	 * Gets the Q-value associated with the given board and move for
	 * the given player.
	 * 
	 * @param board The board for which to check the Q-value.
	 * @param move The move for which to check the Q-value.
	 * @param player The player for whom to check the Q-value.
	 * @return The Q-value associated with the given board and move
	 *         for the given player.
	 */
	public double qValue(TicTacToeBoard board, TicTacToeMove move, Player player) {
		
		// Gets the correct table
		final HashMap<Integer, Double> table;
		if (player == Player.X)
			table = xTable;
		else
			table = oTable;
		
		return table.getOrDefault(hash(board, move), 0.0);
	}
	
	/**
	 * Sets the Q-value associated with the given board and move for
	 * the given player to the given value.
	 * 
	 * @param board The board for which to set the Q-value.
	 * @param move The move for which to set the Q-value.
	 * @param player The player for whom to set the Q-value.
	 *               Must be X or O.
	 * @param qValue The Q-value to set.
	 */
	private void setQValue(TicTacToeBoard board, TicTacToeMove move, Player player, double qValue) {
		
		// Gets the correct table
		final HashMap<Integer, Double> table;
		if (player == Player.X)
			table = xTable;
		else
			table = oTable;
		
		table.put(hash(board, move), qValue);
	}
	
	/**
	 * Gets the greedy move at the given board for the player whose turn it is.
	 * The greedy move is the move with the highest Q-value out of the
	 * possible moves with the given board for the player.
	 * 
	 * @param board The board for which to get the greedy move.
	 * @return The greedy move with the board for the player whose turn it is.
	 */
	public TicTacToeMove greedyMove(TicTacToeBoard board) {
		
		// Stores the greedy move details
		TicTacToeMove bestMove = null;
		double bestQValue = Double.NEGATIVE_INFINITY;
		
		// Computes the greedy move 
		for (TicTacToeMove move : board.possibleMoves()) {
			final double moveQValue = qValue(board, move, board.turnOf());
			if (bestQValue < moveQValue) {
				bestMove = move;
				bestQValue = moveQValue;
			}
		}
		
		return bestMove;
	}
	
	/**
	 * Updates the Q tables at the given board.
	 * 
	 * @param board The board at which to make an update to the tables.
	 * @return The board reached by the ultimately chosen move,
	 *         or the starting board {@code TicTacToeBoard.DEFAULT}
	 *         if a terminal board was reached.
	 */
	public TicTacToeBoard update(TicTacToeBoard board) {
		
		// Selects the action to take
		final TicTacToeMove move;
		if (Math.random() <= greedyRate) {
			move = greedyMove(board);
		} else {
			// Chooses a random valid move
			final TicTacToeMove[] moves = board.possibleMoves();
			final int randomIndex = (int)(moves.length*Math.random());
			move = moves[randomIndex];
		}
		
		// Computes the next board and its greedy move
		final TicTacToeBoard nextBoard = move.nextBoard(board);
		final TicTacToeMove greedyMove = greedyMove(nextBoard);
		
		// Computes & stores the new Q-value for the players
		for (Player player : new Player[] {Player.X, Player.O}) {
			final double startingQValue = qValue(board, move, player);
			double qValue = -startingQValue;
			qValue += move.reward(board, player).amount;
			qValue += discountRate * qValue(nextBoard, greedyMove, player);
			qValue *= learningRate;
			qValue += startingQValue;
			setQValue(board, move, player, qValue);
		}
		
		return nextBoard.isTerminal() ? TicTacToeBoard.DEFAULT : nextBoard;
	}
	
	/**
	 * Runs enough update steps to train the Q tables until
	 * the game terminates.
	 */
	public void trainGame() {
		TicTacToeBoard board = TicTacToeBoard.DEFAULT;
		do {
			board = update(board);
		} while (board != TicTacToeBoard.DEFAULT);
	}
}
