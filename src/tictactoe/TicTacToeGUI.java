package tictactoe;

import static tictactoe.TicTacToeBoard.SIZE;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class TicTacToeGUI extends Application {
	
	private static final TTTQTable GAME = new TTTQTable(0.5, 0.9, 0.5);

	private static final Player PLAY_AS = Player.X;
	
	private static TicTacToeBoard board = TicTacToeBoard.DEFAULT;
	
	public static void main(String[] args) {
		for (int episode = 1; episode <= 1_000_000; episode++) {
			if (episode % 100_000 == 0)
				System.out.println("Episode: " + episode);
			GAME.trainGame();
		}
		launch(args);
	}

	public void start(Stage stage) throws Exception {
		
		// AnchorPane
		final AnchorPane pane = new AnchorPane();
		
		// Sets up the tic tac toe GUI
		final int rectSize = 200;
		final Rectangle[] spots = new Rectangle[SIZE*SIZE];
		for (int x = 0; x < SIZE; x++) {
			for (int y = 0; y < SIZE; y++) {
				final int xCoord = x;
				final int yCoord = y;
				final Rectangle rect = new Rectangle(x*rectSize, y*rectSize, rectSize, rectSize);
				spots[x + SIZE*y] = rect;
				rect.setOnMouseClicked(m -> {
					rect.setFill(PLAY_AS == Player.X ? Color.RED : Color.GREEN);
					board = new TicTacToeMove(xCoord, yCoord).nextBoard(board);
					
					if (!board.isTerminal()) {
						final TicTacToeMove move = GAME.greedyMove(board);
						spots[move.x + SIZE*move.y].setFill(PLAY_AS.opponent() == Player.X ? Color.RED : Color.GREEN);
						board = move.nextBoard(board);
					}
				});
				pane.getChildren().add(rect);
			}
		}
		
		// Adds borders
		for (int i = 0; i < SIZE; i++) {
			
			// Creates the horizontal border
			final Rectangle hBorder = new Rectangle(rectSize*SIZE, rectSize/100, Color.WHITE);
			hBorder.setLayoutY(i*rectSize);
			
			// Creates the vertical border
			final Rectangle vBorder = new Rectangle(rectSize/100, rectSize*SIZE, Color.WHITE);
			vBorder.setLayoutX(i*rectSize);

			pane.getChildren().add(hBorder);
			pane.getChildren().add(vBorder);
		}
		
		if (PLAY_AS == Player.O) {
			final TicTacToeMove move = GAME.greedyMove(board);
			spots[move.x + SIZE*move.y].setFill(PLAY_AS.opponent() == Player.X ? Color.RED : Color.GREEN);
			board = move.nextBoard(board);
		}
		
		// Sets up the scene & stage
		final Scene scene = new Scene(pane);
		stage.setScene(scene);
		stage.show();
	}

}
