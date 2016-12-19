
import acm.program.*;

public class ConnectFourController extends Program implements ConnectFourConstants {
	private ConnectFourModel model;
	private ConnectFourView view;
	private int numValidMoves;
	
	public void init() {
		model = new ConnectFourModel(DEFAULT_ROWS, DEFAULT_COLS);
		view = new ConnectFourView(DEFAULT_ROWS, DEFAULT_COLS);
		add(view, CENTER);
	}
	
	public void run() {
		view.draw();
		numValidMoves = 0;
		int result = playGame(null, null);
		determineWinner(result);
	}
	
	private int playGame(ConnectFourPlayer playerOneAI, ConnectFourPlayer playerTwoAI) { // null indicates human
		int currentPlayer = PLAYER_ONE;
		while(numValidMoves < model.numRows() * model.numCols()) {
			int move = 0;
			ConnectFourPlayer currentAI = ((currentPlayer == PLAYER_ONE) ? playerOneAI : playerTwoAI);
			String currentPlayerString = (curretntPlayer == PLAYER_ONE) ? "1" : "2";
			if(currentAI == null) {
				view.showMessage("Player " + currentPlayer + "'s turn. Click a button to make your move.");
				move = view.getHumanMove();
			} else {
				int[][] board = model.getBoardCopy();
				if(currentPlayer == PLAYER_TWO) ConnectFourModel.invertBoardState(board);
				view.showMessage("Player " + currentPlayer + " is thinking...");
				move = currentAI.getMove(board);
			}
			model.makeMove(currentPlayer, move);
			view.addMove(move);
			int winner = model.checkWin();
			if(winner == PLAYER_ONE || winner == PLAYER_TWO) return winner;
			numValidMoves++;
			currentPlayer *= -1;
		}
		return 0;
	}
	
	private void determineWinner(int result) {
		if(result == PLAYER_ONE) {
			view.showMessage("Player One was the winner!");
		} else if(result == PLAYER_TWO) {
			view.showMessage("Player Two was the winner!");
		} else {
			view.showMessage("The game ended in a draw.");
		}
	}
}
