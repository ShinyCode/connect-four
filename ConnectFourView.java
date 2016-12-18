import dashboard.control.*;
import acm.graphics.*;

public class ConnectFourView extends GCanvas implements ConnectFourConstants {
	private GOval[][] pieces;
	private TouchButton[] buttons;
	private boolean waitingForHumanMove = false;
	private double boardX = 0;
	private double boardY = 0;
	
	public ConnectFourView() {
		pieces = new GOval[DEFAULT_ROWS][DEFAULT_COLS];
	}
	
	public ConnectFourView(int numRows, int numCols) {
		pieces = new GOval[numRows][numCols];
	}
	
	public void addMove(int player, int col) { // Blindly adds a move (no error-checking)
		
	}
	
	public void draw() {
		System.out.println(getWidth());
		double cellWidth = calcCellWidth();
		int numRows = pieces.length;
		int numCols = pieces[0].length;
		GRect board = new GRect(boardX, boardY, numCols * cellWidth, numRows * cellWidth);
		board.setFilled(true);
		board.setFillColor(BOARD_COLOR);
		add(board);
	}
	
	private double calcCellWidth() {
		double cellWidth = 0;
		double availXSpace = getWidth() - 2 * BOARD_SIDE_MARGIN;
		double availYSpace = getHeight() - (2 * BUTTON_MARGIN + BUTTON_HEIGHT) - BOARD_TOP_MARGIN - BOARD_BOTTOM_MARGIN;
		int numRows = pieces.length;
		int numCols = pieces[0].length;
		if(((double)numRows / numCols) >= availYSpace / availXSpace) { // Too tall, use scale to availYSpace
			cellWidth = availYSpace / numRows;
		} else {
			cellWidth = availXSpace / numCols;
		}
		boardX = BOARD_SIDE_MARGIN + (availXSpace - numCols * cellWidth) / 2;
		boardY = 2 * BUTTON_MARGIN + BUTTON_HEIGHT + BOARD_TOP_MARGIN + (availYSpace - numRows * cellWidth) / 2;
		return cellWidth;
	}
	
	public int getHumanMove() {
		return 0;
	}
	
	public void showMessage(String msg) {
		
	}
}
