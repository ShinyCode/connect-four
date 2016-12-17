
public class ConnectFourModel implements ConnectFourConstants {
	int[][] board;
	
	public ConnectFourModel() {
		board = new int[DEFAULT_ROWS][DEFAULT_COLS];
	}
	
	public ConnectFourModel(int numRows, int numCols) {
		board = new int[numRows][numCols];
	}
	
	public String toString() {
		String result = "";
		for(int row = 0; row < numRows(); row++) {
			for(int col = 0; col < numCols(); col++) {
				if(board[row][col] == PLAYER_ONE) result += PLAYER_ONE_SYMBOL;
				else if (board[row][col] == PLAYER_TWO) result += PLAYER_TWO_SYMBOL;
				else result += EMPTY_SYMBOL;
			}
			if(row != numRows() - 1) result += ".%n";
		}
		return String.format(result);
	}
	
	private int numRows() {
		return board.length;
	}
	
	private int numCols() {
		if(board.length > 0) return board[0].length;
		return 0;
	}
	
	public boolean makeMove(int player, int col) { // Returns whether the move was valid. player is 1 or -1.
		if(col < 0 || col >= numCols()) return false; // Out of bounds
		if(player != PLAYER_ONE && player != PLAYER_TWO) return false;
		for(int row = numRows() - 1; row >= 0; row--) {
			if(isEmpty(row, col)) {
				board[row][col] = player;
				return true;
			}
		}
		return false; // The column was full
	}
	
	private boolean isEmpty(int row, int col) {
		return (board[row][col] != PLAYER_ONE && board[row][col] != PLAYER_TWO);
	}
	
	public int checkWin() { // Returns player number who won, or 0 if no one has won yet
		// Check rows
		
		return 0;
	}
	
	private int checkRows() {
		for(int row = 0; row < numRows(); row++) {
			for(int startCol = 0; startCol <= numCols() - NUM_IN_A_ROW; startCol++) {
				int probeValue = board[row][startCol];
				if(probeValue != PLAYER_ONE && probeValue != PLAYER_TWO) continue;
				for(int col = startCol + 1; col < startCol + NUM_IN_A_ROW; col++) {
					if(board[row][col] != probeValue) break;
				}
				return probeValue;
			}
		}
		return 0;
	}
	
	private boolean allEqual(int[] values) {
		if(values.length == 0) return true;
		int probeValue = values[0];
		for(int i = 1; i < values.length; i++) {
			if(values[i] != probeValue) return false;
		}
		return true;
	}
}

