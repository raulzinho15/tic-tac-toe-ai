package tictactoe;

import java.util.Arrays;
import java.util.HashSet;

public class Tests {

	public static void main(String[] args) {
		ticTacToeBoardTests();
	}
	
	private static void ticTacToeBoardTests() { try {
		System.out.println("Tests for TicTacToeBoard ADT:");
		
		// Tests the default board with equals()
		{
			if (!TicTacToeBoard.DEFAULT.equals(TicTacToeBoard.DEFAULT))
				throw new RuntimeException("Expected the two boards to be equal.");
			System.out.print("P");
		}
		
		// Tests boards without parameter aliasing with equals()
		{
			final TicTacToeBoard board1 = new TicTacToeBoard(
					Player.X, Player.O, Player.X,
					Player.O, Player.X, Player.O,
					Player.X, Player.O, Player.X
			);
			final TicTacToeBoard board2 = new TicTacToeBoard(
					Player.X, Player.O, Player.X,
					Player.O, Player.X, Player.O,
					Player.X, Player.O, Player.X
			);
			if (!board1.equals(board1))
				throw new RuntimeException("Expected the two boards to be equal.");
			if (!board1.equals(board2))
				throw new RuntimeException("Expected the two boards to be equal.");
			System.out.print("P");
		}

		// Tests boards with parameter aliasing with equals()
		{
			final Player[] layout = new Player[] {
					Player.X, Player.O, Player.X,
					Player.O, Player.X, Player.O,
					Player.X, Player.O, Player.X,
			};
			final TicTacToeBoard board1 = new TicTacToeBoard(layout);
			final TicTacToeBoard board2 = new TicTacToeBoard(layout);
			if (!board1.equals(board1))
				throw new RuntimeException("Expected the two boards to be equal.");
			if (!board1.equals(board2))
				throw new RuntimeException("Expected the two boards to be equal.");
			System.out.print("P");
		}
		
		// Tests board at X's turn with possibleActions()
		{
			final TicTacToeBoard board = new TicTacToeBoard(
					Player.X,    Player.O,    Player.X,
					Player.O,    Player.X,    Player.O,
					Player.NONE, Player.NONE, Player.NONE
			);
			final HashSet<TicTacToeMove> actualActions = new HashSet<TicTacToeMove>(Arrays.asList(board.possibleMoves()));
			final HashSet<TicTacToeMove> expectedActions = new HashSet<TicTacToeMove>(Arrays.asList(
					new TicTacToeMove(0, 2),
					new TicTacToeMove(1, 2),
					new TicTacToeMove(2, 2)
			));
			if (!expectedActions.equals(actualActions))
				throw new RuntimeException("Expected the possible actions to be correct.");
			System.out.print("P");
		}
		
		// Tests board at O's turn with possibleActions()
		{
			final TicTacToeBoard board = new TicTacToeBoard(
					Player.X,    Player.O,    Player.X,
					Player.O,    Player.X,    Player.O,
					Player.NONE, Player.X,    Player.NONE
			);
			final HashSet<TicTacToeMove> actualActions = new HashSet<TicTacToeMove>(Arrays.asList(board.possibleMoves()));
			final HashSet<TicTacToeMove> expectedActions = new HashSet<TicTacToeMove>(Arrays.asList(
					new TicTacToeMove(0, 2),
					new TicTacToeMove(2, 2)
			));
			if (!expectedActions.equals(actualActions))
				throw new RuntimeException("Expected the possible actions to be correct.");
			if (board.isTerminal())
				throw new RuntimeException("Expected the board to not be terminal.");
			System.out.print("P");
		}
		
		// Tests board at an anti-diagonal terminal state with isTerminal() & possibleActions()
		{
			final TicTacToeBoard board = new TicTacToeBoard(
					Player.X,    Player.O,    Player.X,
					Player.O,    Player.X,    Player.O,
					Player.X,    Player.NONE, Player.NONE
			);
			if (board.possibleMoves().length != 0)
				throw new RuntimeException("Expected the possible actions to be correct.");
			if (!board.isTerminal())
				throw new RuntimeException("Expected the board to be terminal.");
			System.out.print("P");
		}
		
		// Tests board at main diagonal terminal state with isTerminal()
		{
			final TicTacToeBoard board = new TicTacToeBoard(
					Player.X,    Player.O,    Player.X,
					Player.O,    Player.X,    Player.O,
					Player.NONE, Player.NONE, Player.X
			);
			if (!board.isTerminal())
				throw new RuntimeException("Expected the board to be terminal.");
			System.out.print("P");
		}
		
		// Tests board at a row terminal state with isTerminal()
		{
			final TicTacToeBoard board = new TicTacToeBoard(
					Player.X,    Player.X,    Player.X,
					Player.O,    Player.O,    Player.O,
					Player.NONE, Player.NONE, Player.NONE
			);
			if (!board.isTerminal())
				throw new RuntimeException("Expected the board to be terminal.");
			System.out.print("P");
		}
		
		// Tests board at a column terminal state with isTerminal()
		{
			final TicTacToeBoard board = new TicTacToeBoard(
					Player.X, Player.O, Player.NONE,
					Player.X, Player.O, Player.NONE,
					Player.X, Player.O, Player.NONE
			);
			if (!board.isTerminal())
				throw new RuntimeException("Expected the board to be terminal.");
			System.out.print("P");
		}
		
		// Tests board at a tie state with isTerminal()
		{
			final TicTacToeBoard board = new TicTacToeBoard(
					Player.X, Player.O, Player.NONE,
					Player.X, Player.O, Player.NONE,
					Player.X, Player.O, Player.NONE
			);
			if (!board.isTerminal())
				throw new RuntimeException("Expected the board to be terminal.");
			System.out.print("P");
		}
		
	} catch (Exception e) {e.printStackTrace();}}
}
