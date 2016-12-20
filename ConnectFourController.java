
import java.awt.Dimension;

import acm.io.IOConsole;
import acm.program.*;

public class ConnectFourController extends Program implements ConnectFourConstants {
	private ConnectFourModel model;
	private ConnectFourView view;
	
	public void init() {
		model = new ConnectFourModel(DEFAULT_ROWS, DEFAULT_COLS);
		view = new ConnectFourView(DEFAULT_ROWS, DEFAULT_COLS);
		add(view, CENTER);
		IOConsole console = new IOConsole();
		setConsole(console);
		add(console, CENTER);
		setSize(new Dimension(APP_WIDTH, APP_HEIGHT));
	}
	
	public void run() {
		view.draw();
		ConnectFourAI playerOneAI = getAIFromConsole(PLAYER_ONE);
		ConnectFourAI playerTwoAI = getAIFromConsole(PLAYER_TWO);
		println("Running game...");
		int result = playGame(playerOneAI, playerTwoAI);
		println("...finished game.");
		determineWinner(result);
		readLine();
		view.reset();
		
	}
	
	private ConnectFourAI getAIFromConsole(int player) {
		int aiNumber = 0;
		while(true) {
			String playerString = (player == PLAYER_ONE) ? "1" : "2";
			println("Please choose an AI for Player " + playerString + ":");
			println("(0) Human (1) Random (2) Simple (3) Minimax (4) AlphaBeta");
			aiNumber = readInt(">> ");
			if(aiNumber >= 0 && aiNumber < NUM_AIS) break;
			print("Invalid choice. ");
		}
		switch(aiNumber) {
			case 0: return null;
			case 1: return new RandomAI();
			case 2: return new SimpleAI();
			case 3: return new MinimaxAI(getAIDepth());
			case 4: return new AlphaBetaAI(getAIDepth());
			default: return null;
		}
	}
	
	private int getAIDepth() {
		int depth = 0;
		while(true) {
			println("Please enter the search depth. (Depth must be positive)");
			depth = readInt(">> ");
			if(depth > 0) break;
			print("Invalid depth. ");
		}
		return depth;
	}
	
	private int playGame(ConnectFourAI playerOneAI, ConnectFourAI playerTwoAI) { // null indicates human
		int numValidMoves = 0;
		int currentPlayer = PLAYER_ONE;
		while(numValidMoves < model.numRows() * model.numCols()) {
			int move = 0;
			boolean error = false;
			while(true) {
				ConnectFourAI currentAI = ((currentPlayer == PLAYER_ONE) ? playerOneAI : playerTwoAI);
				String currentPlayerString = (currentPlayer == PLAYER_ONE) ? "1" : "2";
				if(currentAI == null) { // Human player
					if(!error) view.showMessage("Player " + currentPlayerString + "'s turn. Click a button to make your move.");
					else view.showMessage("Invalid move. Enter another move for player " + currentPlayerString + ".");
					move = view.getHumanMove();
				} else { // Computer player
					int[][] board = model.getBoardCopy();
					if(currentPlayer == PLAYER_TWO) ConnectFourModel.invertBoardState(board);
					if(!error) view.showMessage("Player " + currentPlayerString + " is thinking...");
					else view.showMessage("AI for player " + currentPlayerString + " returned an invalid move!");
					move = currentAI.getMove(board);
				}
				if(model.makeMove(currentPlayer, move)) {
					view.addMove(move);
					break;
				}
				error = true;
			}
			int winner = model.checkWin();
			if(winner == PLAYER_ONE || winner == PLAYER_TWO) return winner;
			numValidMoves++;
			currentPlayer *= -1;
		}
		return 0;
	}
	
	private void determineWinner(int result) {
		if(result == PLAYER_ONE) {
			view.showMessage("Player 1 was the winner!");
			println("Player 1 was the winner!");
		} else if(result == PLAYER_TWO) {
			view.showMessage("Player 2 was the winner!");
			println("Player 2 was the winner!");
		} else {
			view.showMessage("The game ended in a draw.");
			println("The game ended in a draw.");
		}
	}
}
