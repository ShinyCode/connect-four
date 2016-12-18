
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
			int move = view.getHumanMove();
			model.makeMove(currentPlayer, move);
			System.out.println(model);
			view.addMove(currentPlayer, move);
			int winner = model.checkWin();
			if(winner == PLAYER_ONE || winner == PLAYER_TWO) return winner;
			numValidMoves++;
			currentPlayer *= -1;
		}
		return 0;
	}
	
	private void determineWinner(int result) {
		if(result == PLAYER_ONE) {
			println("Player One was the winner!");
		} else if(result == PLAYER_TWO) {
			println("Player Two was the winner!");
		} else {
			println("The game ended in a draw.");
		}
	}
}
