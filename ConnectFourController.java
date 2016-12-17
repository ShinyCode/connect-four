
import acm.program.*;

public class ConnectFourController extends ConsoleProgram implements ConnectFourConstants {
	private ConnectFourModel model;
	
	public void run() {
		model = new ConnectFourModel(DEFAULT_ROWS, DEFAULT_COLS);
		int result = playGame();
		determineWinner(result);
	}
	
	private void playGame() {
		int currentPlayer = PLAYER_ONE;
		while(true) {
			// Get player one's move
			
			
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
}
