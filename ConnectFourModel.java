import java.util.Stack;


public class ConnectFourModel implements ConnectFourConstants {
	private int[][] board;
	private Stack<Integer> moves;
	private int numMoves;
	
	public ConnectFourModel() {
		board = new int[DEFAULT_ROWS][DEFAULT_COLS];
		moves = new Stack<Integer>();
		numMoves = 0;
	}
	
	public ConnectFourModel(int numRows, int numCols) {
		board = new int[numRows][numCols];
		moves = new Stack<Integer>();
		numMoves = 0;
	}
	
	public ConnectFourModel(int[][] board) {
		this.board = board;
		moves = new Stack<Integer>();
		numMoves = 0;
		for(int col = 0; col < numCols(); col++) {
			for(int row = numRows() - 1; row >= 0; row--) {
				if(!isEmpty(row, col)) numMoves++;
				else break;
			}
		}
	}
	
	public String toString() {
		String result = "";
		for(int row = 0; row < numRows(); row++) {
			for(int col = 0; col < numCols(); col++) {
				if(board[row][col] == PLAYER_ONE) result += PLAYER_ONE_SYMBOL;
				else if (board[row][col] == PLAYER_TWO) result += PLAYER_TWO_SYMBOL;
				else result += EMPTY_SYMBOL;
			}
			if(row != numRows() - 1) result += "%n";
		}
		return String.format(result);
	}
	
	public int[][] getBoardCopy() {
		int[][] boardCopy = new int[numRows()][numCols()];
		for(int row = 0; row < numRows(); row++) {
			for(int col = 0; col < numCols(); col++) {
				boardCopy[row][col] = board[row][col];
			}
		}
		return boardCopy;
	}
	
	public int numRows() {
		return board.length;
	}
	
	public int numCols() {
		if(board.length > 0) return board[0].length;
		return 0;
	}
	
	public boolean makeMove(int player, int col) { // Returns whether the move was valid. player is 1 or -1.
		boolean isValid = moveHelper(player, col, true);
		if(isValid) moves.push(col);
		return isValid;
	}
	
	public boolean moveIsValid(int player, int col) {
		return moveHelper(player, col, false);
	}
	
	public boolean undoMove() { // Undoes the last move played. Returns whether there was a move to undo.
		if(moves.empty()) return false;
		int lastMove = moves.pop();
		for(int row = 0; row < numRows(); row++) {
			if(!isEmpty(row, lastMove)) {
				board[row][lastMove] = NO_PLAYER;
				break;
			}
		}
		return true;
	}
	
	private boolean moveHelper(int player, int col, boolean makeMove) {
		if(col < 0 || col >= numCols()) return false; // Out of bounds
		if(player != PLAYER_ONE && player != PLAYER_TWO) return false;
		for(int row = numRows() - 1; row >= 0; row--) {
			if(isEmpty(row, col)) {
				if(makeMove) board[row][col] = player;
				return true;
			}
		}
		return false; // The column was full
	}
	
	public boolean isEmpty(int row, int col) {
		return (board[row][col] != PLAYER_ONE && board[row][col] != PLAYER_TWO);
	}
	
	public boolean colIsFull(int col) {
		for(int row = numRows() - 1; row >= 0; row--) {
			if(isEmpty(row, col)) {
				return false;
			}
		}
		return true;
	}
	
	public int checkWin() { // Returns player number who won, or NO_PLAYER if no one has won yet
		int result = checkRows();
		if(result == PLAYER_ONE || result == PLAYER_TWO) return result;
		result = checkCols();
		if(result == PLAYER_ONE || result == PLAYER_TWO) return result;
		result = checkTLDiags();
		if(result == PLAYER_ONE || result == PLAYER_TWO) return result;
		result = checkTRDiags();
		if(result == PLAYER_ONE || result == PLAYER_TWO) return result;
		return NO_PLAYER;
	}
	
	private int checkRows() {
		for(int row = 0; row < numRows(); row++) {
			for(int startCol = 0; startCol <= numCols() - NUM_IN_A_ROW; startCol++) {
				int[] values = new int[NUM_IN_A_ROW];
				int index = 0;
				for(int col = startCol; col < startCol + NUM_IN_A_ROW; col++) {
					values[index++] = board[row][col];
				}
				int result = allEqual(values);
				if(result == PLAYER_ONE || result == PLAYER_TWO) return result;
			}
		}
		return NO_PLAYER;
	}
	
	private int checkCols() {
		for(int col = 0; col < numCols(); col++) {
			for(int startRow = 0; startRow <= numRows() - NUM_IN_A_ROW; startRow++) {
				int[] values = new int[NUM_IN_A_ROW];
				int index = 0;
				for(int row = startRow; row < startRow + NUM_IN_A_ROW; row++) {
					values[index++] = board[row][col];
				}
				int result = allEqual(values);
				if(result == PLAYER_ONE || result == PLAYER_TWO) return result;
			}
		}
		return NO_PLAYER;
	}
	
	private int checkTLDiags() {
		for(int startRow = 0; startRow <= numRows() - NUM_IN_A_ROW; startRow++) {
			for(int startCol = 0; startCol <= numCols() - NUM_IN_A_ROW; startCol++) {
				int[] values = new int[NUM_IN_A_ROW];
				int index = 0;
				for(int offset = 0; offset < NUM_IN_A_ROW; offset++) {
					values[index++] = board[startRow + offset][startCol + offset];
				}
				int result = allEqual(values);
				if(result == PLAYER_ONE || result == PLAYER_TWO) return result;
			}
		}
		return NO_PLAYER;
	}
	
	private int checkTRDiags() {
		for(int startRow = 0; startRow <= numRows() - NUM_IN_A_ROW; startRow++) {
			for(int startCol = numCols() - 1; startCol >= NUM_IN_A_ROW - 1; startCol--) {
				int[] values = new int[NUM_IN_A_ROW];
				int index = 0;
				for(int offset = 0; offset < NUM_IN_A_ROW; offset++) {
					values[index++] = board[startRow + offset][startCol - offset];
				}
				int result = allEqual(values);
				if(result == PLAYER_ONE || result == PLAYER_TWO) return result;
			}
		}
		return NO_PLAYER;
	}
	
	private int allEqual(int[] values) {
		if(values.length == 0) return NO_PLAYER;
		int probeValue = values[0];
		if(probeValue != PLAYER_ONE && probeValue != PLAYER_TWO) return NO_PLAYER;
		for(int i = 1; i < values.length; i++) {
			if(values[i] != probeValue) return NO_PLAYER;
		}
		return probeValue;
	}
	
	public static void invertBoardState(int[][] board) {
		int numRows = board.length;
		int numCols = board[0].length;
		for(int row = 0; row < numRows; row++) {
			for(int col = 0; col < numCols; col++) {
				board[row][col] *= -1;
			}
		}
	}
}

