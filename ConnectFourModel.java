
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
		for(int r = 0; r < numRows(); r++) {
			for(int c = 0; c < numCols(); c++) {
				if(board[r][c] == PLAYER_ONE) result += PLAYER_ONE_SYMBOL;
				else if (board[r][c] == PLAYER_TWO) result += PLAYER_TWO_SYMBOL;
				else result += EMPTY_SYMBOL;
			}
		}
	}
	
	private int numRows() {
		return board.length;
	}
	
	private int numCols() {
		if(board.length > 0) return board[0].length;
		return 0;
	}
	
}

