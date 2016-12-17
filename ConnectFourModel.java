
public class ConnectFourModel implements ConnectFourConstants {
	int[][] board;
	
	public ConnectFourModel() {
		board = new int[DEFAULT_ROWS][DEFAULT_COLS];
	}
	
	public ConnectFourModel(int numRows, int numCols) {
		board = new int[numRows][numCols];
	}
	
	public String toString() {
		
	}
	
}

