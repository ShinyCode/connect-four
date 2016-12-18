import dashboard.control.*;
import acm.graphics.*;

public class ConnectFourView extends GCanvas implements ConnectFourConstants {
	private int[][] board;
	private TouchButton[] buttons;
	
	public ConnectFourView() {
		board = new int[DEFAULT_ROWS][DEFAULT_COLS];
		redraw();
	}
	
	public ConnectFourView(int numRows, int numCols) {
		board = new int[numRows][numCols];
		redraw();
	}
	
	public void addMove(int player, int col) { // Blindly adds a move (no error-checking)
		
	}
	
	public void redraw() {
		
	}
	
	public int getHumanMove() {
		return 0;
	}
	
	public void showMessage(String msg) {
		
	}
}
