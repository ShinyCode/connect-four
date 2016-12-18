import dashboard.control.*;
import acm.graphics.*;

public class ConnectFourView extends GCanvas implements ConnectFourConstants {
	private GOval[][] pieces;
	private TouchButton[] buttons;
	private boolean waitingForHumanMove = false;
	
	public ConnectFourView() {
		pieces = new GOval[DEFAULT_ROWS][DEFAULT_COLS];
		draw();
	}
	
	public ConnectFourView(int numRows, int numCols) {
		pieces = new GOval[numRows][numCols];
		draw();
	}
	
	public void addMove(int player, int col) { // Blindly adds a move (no error-checking)
		
	}
	
	public void draw() {
		println(getWidth());
		GRect board = new GRect(BOARD_MARGIN, 2 * BUTTON_MARGIN + BUTTON_HEIGHT, getWidth() - 2 * BOARD_MARGIN, getHeight() - (2 * BUTTON_MARGIN + BUTTON_HEIGHT + BOARD_MARGIN));
		board.setFilled(true);
		board.setFillColor(BOARD_COLOR);
		add(board);
	}
	
	public int getHumanMove() {
		return 0;
	}
	
	public void showMessage(String msg) {
		
	}
}
