
import acm.program.*;

public class ConnectFourController extends Program implements ConnectFourConstants {
	private ConnectFourModel model;
	private ConnectFourView view;
	
	public void init() {
		model = new ConnectFourModel(DEFAULT_ROWS, DEFAULT_COLS);
		view = new ConnectFourView(DEFAULT_ROWS, DEFAULT_COLS);
		add(view, CENTER);
	}
	
	public void run() {
		view.draw();
		int result = playGame(new AlphaBetaAI(3), new AlphaBetaAI(3));
		determineWinner(result);
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
			view.showMessage("Player One was the winner!");
		} else if(result == PLAYER_TWO) {
			view.showMessage("Player Two was the winner!");
		} else {
			view.showMessage("The game ended in a draw.");
		}
	}
}
