
import acm.program.*;

public class ConnectFourController extends ConsoleProgram implements ConnectFourConstants {
	private ConnectFourModel model;
	private int numValidMoves;
	
	public void run() {
		model = new ConnectFourModel(DEFAULT_ROWS, DEFAULT_COLS);
		numValidMoves = 0;
		int result = playGame();
		println(repString("-", 20));
		println(model);
		println(repString("-", 20));
		determineWinner(result);
	}
	
	private int playGame() {
		int currentPlayer = PLAYER_ONE;
		while(numValidMoves < model.numRows() * model.numCols()) {
			println(repString("-", 20));
			println(model);
			int move = getHumanMove(currentPlayer);
			model.makeMove(currentPlayer, move);
			int winner = model.checkWin();
			if(winner == PLAYER_ONE || winner == PLAYER_TWO) return winner;
			numValidMoves++;
			currentPlayer *= -1;
		}
		return 0;
	}
	
	private int getHumanMove(int player) {
		while(true) {
			String name = (player == 1 ? "Player One" : "Player Two");
			int move = readInt("Enter a move for " + name + ": ");
			if(model.moveIsValid(player, move)) return move;
			println("The move you entered was invalid.");
		}
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
	
	private String repString(String s, int n) {
		String result = "";
		for(int i = 0; i < n; i++) {
			result += s;
		}
		return result;
	}
}
