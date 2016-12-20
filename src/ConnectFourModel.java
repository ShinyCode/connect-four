package src;
import java.util.Stack;

/**
 * The ConnectFourModel encapsulates the state of a ConnectFour board,
 * and contains all the logic for checking/making/undoing moves, as well
 * as computing various statistics about the board.
 * 
 * @author Mark Sabini
 *
 */
public class ConnectFourModel implements ConnectFourConstants {
	/**
	 * The raw board itself, containing values of PLAYER_ONE, PLAYER_TWO, and NO_PLAYER
	 */
	private int[][] board;
	
	/**
	 * The history of moves made so far, used for undoing moves
	 */
	private Stack<Integer> moves;
	
	/**
	 * Number of moves so far, used to quickly determine whether the board is full
	 */
	private int numMoves;
	
	/**
	 * Creates a ConnectFourModel with DEFAULT_ROWS rows and DEFAULT_COLS columns.
	 * The default values for the constants are usually 6 and 7, respectively.
	 */
	public ConnectFourModel() {
		board = new int[DEFAULT_ROWS][DEFAULT_COLS];
		moves = new Stack<Integer>();
		numMoves = 0;
	}
	
	/**
	 * Creates a ConnectFourModel with the specified number of rows and columns.
	 * 
	 * @param numRows the number of rows for the model
	 * @param numCols the number of columns for the model
	 */
	public ConnectFourModel(int numRows, int numCols) {
		board = new int[numRows][numCols];
		moves = new Stack<Integer>();
		numMoves = 0;
	}
	
	/**
	 * Creates a ConnectFourModel from a pre-existing raw board.
	 * 
	 * @param board the board from which to initialize the model
	 */
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
	
	/**
	 * Returns whether the internal board is full.
	 * 
	 * @return whether the internal board is full
	 */
	public boolean isFull() {
		return numMoves == numRows() * numCols();
	}
	
	/**
	 * Returns the String representation of the board.
	 */
	@Override
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
	
	/**
	 * Returns a copy of the internal board, which can be modified without
	 * affecting the model.
	 * 
	 * @return a copy of the internal board
	 */
	public int[][] getBoardCopy() {
		int[][] boardCopy = new int[numRows()][numCols()];
		for(int row = 0; row < numRows(); row++) {
			for(int col = 0; col < numCols(); col++) {
				boardCopy[row][col] = board[row][col];
			}
		}
		return boardCopy;
	}
	
	/**
	 * Returns the number of rows in the model
	 * 
	 * @return the number of rows in the model
	 */
	public int numRows() {
		return board.length;
	}
	
	/**
	 * Returns the number of columns in the model
	 * 
	 * @return the number of columns in the model
	 */
	public int numCols() {
		if(board.length > 0) return board[0].length;
		return 0;
	}
	
	/**
	 * Attempts to make the specified move for the specified player. If the move is
	 * invalid, no action is taken, but the method will return false. If the move
	 * was successful, the method will return true.
	 * 
	 * @param player the player who is moving, either PLAYER_ONE or PLAYER_TWO
	 * @param col the column in which to place a piece
	 * @return whether the move was valid
	 */
	public boolean makeMove(int player, int col) { // Returns whether the move was valid. player is 1 or -1.
		boolean isValid = moveHelper(player, col, true);
		if(isValid) {
			moves.push(col);
			numMoves++;
		}
		return isValid;
	}
	
	/**
	 * Checks whether the specified move for the specified player is valid.
	 * 
	 * @param player the player who is moving, either PLAYER_ONE or PLAYER_TWO
	 * @param col the column in which to place a piece
	 * @return whether the move was valid
	 */
	public boolean moveIsValid(int player, int col) {
		return moveHelper(player, col, false);
	}
	
	/**
	 * Helper method to unify common functionality between makeMove and moveIsValid.
	 * 
	 * @param player the player who is moving, either PLAYER_ONE or PLAYER_TWO
	 * @param col the column in which to place a piece
	 * @param makeMove whether a move is being made
	 * @return whether the move was valid
	 */
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
	
	/**
	 * Undoes the last move played. If there are no more moves to undo, the method
	 * returns false. Else, if the move was undone successfully, the method returns
	 * true.
	 * 
	 * @return whether there was a move to undo
	 */
	public boolean undoMove() {
		if(moves.empty()) return false;
		int lastMove = moves.pop();
		numMoves--;
		for(int row = 0; row < numRows(); row++) {
			if(!isEmpty(row, lastMove)) {
				board[row][lastMove] = NO_PLAYER;
				break;
			}
		}
		return true;
	}
	
	/**
	 * Returns whether the position at the specified row and column is empty.
	 * 
	 * @param row the row of the position to check
	 * @param col the column of the position to check
	 * @return whether the specified position is empty
	 */
	public boolean isEmpty(int row, int col) {
		return (board[row][col] != PLAYER_ONE && board[row][col] != PLAYER_TWO);
	}
	
	/**
	 * Returns whether an entire specified column is full.
	 * 
	 * @param col the column to check
	 * @return whether the specified column is full
	 */
	public boolean colIsFull(int col) {
		for(int row = numRows() - 1; row >= 0; row--) {
			if(isEmpty(row, col)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Checks the entire board to determine if someone has won, and returns the result.
	 * 
	 * @return the winner (either PLAYER_ONE or PLAYER_TWO), or NO_PLAYER if no one has won yet
	 */
	public int checkWin() {
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
	
	/**
	 * Checks all the rows in the entire board for a winner.
	 * 
	 * @return the winner (either PLAYER_ONE or PLAYER_TWO), or NO_PLAYER if no one has won yet
	 */
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
	
	/**
	 * Checks all the columns in the entire board for a winner.
	 * 
	 * @return the winner (either PLAYER_ONE or PLAYER_TWO), or NO_PLAYER if no one has won yet
	 */
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
	
	/**
	 * Checks all the top-left to bottom-right diagonals in the entire board for a winner.
	 * 
	 * @return the winner (either PLAYER_ONE or PLAYER_TWO), or NO_PLAYER if no one has won yet
	 */
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
	
	/**
	 * Checks all the top-right to bottom-left diagonals in the entire board for a winner.
	 * 
	 * @return the winner (either PLAYER_ONE or PLAYER_TWO), or NO_PLAYER if no one has won yet
	 */
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
	
	/**
	 * Determines whether all the values in an array are equal.
	 * 
	 * @param values an array of integer values to check
	 * @return whether all the values are equal
	 */
	private int allEqual(int[] values) {
		if(values.length == 0) return NO_PLAYER;
		int probeValue = values[0];
		if(probeValue != PLAYER_ONE && probeValue != PLAYER_TWO) return NO_PLAYER;
		for(int i = 1; i < values.length; i++) {
			if(values[i] != probeValue) return NO_PLAYER;
		}
		return probeValue;
	}
	
	/**
	 * Given a raw board state, flips all instances of PLAYER_ONE to PLAYER_TWO,
	 * and vice versa.
	 * 
	 * @param board the raw board state to invert (in place)
	 */
	public static void invertBoardState(int[][] board) {
		int numRows = board.length;
		int numCols = board[0].length;
		for(int row = 0; row < numRows; row++) {
			for(int col = 0; col < numCols; col++) {
				board[row][col] *= -1;
			}
		}
	}
	
	/**
	 * Returns whether a specific position is within the bounds of the model.
	 * 
	 * @param row the row of the position to check
	 * @param col the column of the position to check
	 * @return whether the position is in-bounds
	 */
	public boolean inBounds(int row, int col) {
		return (row >= 0 && row < numRows() && col >= 0 && col < numCols());
	}
	
	/**
	 * Counts the number of two-in-a-row occurrences in a board for a given player.
	 * 
	 * @param player the player of interest, either PLAYER_ONE or PLAYER_TWO
	 * @return the number of two-in-a-row occurrences
	 */
	public int countTwos(int player) {
		int count = 0;
		for(int row = 0; row < numRows(); row++) {
			for(int col = 0; col < numCols(); col++) {
				if(board[row][col] != player) continue;
				for(int drow = -1; drow <= 1; drow++) {
					for(int dcol = -1; dcol <= 1; dcol++) {
						if(drow != 0 && dcol != 0 && inBounds(row + drow, col + dcol) && board[row + drow][col + dcol] == player) {
							count++;
						}
					}
				}
			}
		}
		return count / 2; // Each occurrence was counted twice
	}
	
	/**
	 * Counts the number of three-in-a-row occurrences in a board for a given player.
	 * 
	 * @param player the player of interest, either PLAYER_ONE or PLAYER_TWO
	 * @return the number of three-in-a-row occurrences
	 */
	public int countThrees(int player) {
		int count = 0;
		for(int row = 0; row < numRows(); row++) {
			for(int col = 0; col < numCols(); col++) {
				if(board[row][col] != player) continue;
				for(int drow = -1; drow <= 1; drow++) {
					for(int dcol = -1; dcol <= 1; dcol++) {
						if(drow != 0 && dcol != 0 && inBounds(row + drow, col + dcol) && board[row + drow][col + dcol] == player
								&& inBounds(row - drow, col - dcol) && board[row - drow][col - dcol] == player) {
							count++;
						}
					}
				}
			}
		}
		return count / 2; // Each occurrence was counted twice
	}
}

