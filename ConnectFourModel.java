
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
	
	public boolean makeMove(int player, int col) { // Returns whether the move was valid
		if(col < 0 || col >= numCols() || isFull(col)) return false;
		for(int row = numRows() - 1; row >= 0; row--)
		return true;
	}
	
	private boolean isFull(int col) {
		return true;
	}
	
}

