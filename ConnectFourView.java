import dashboard.control.*;
import acm.graphics.*;

public class ConnectFourView extends GCanvas implements ConnectFourConstants {
	private GOval[][] pieces;
	private TouchButton[] buttons;
	private boolean waitingForHumanMove = false;
	private double boardX = 0;
	private double boardY = 0;
	private double cellWidth = 0;
	
	public ConnectFourView() {
		pieces = new GOval[DEFAULT_ROWS][DEFAULT_COLS];
		buttons = new TouchButton[DEFAULT_COLS];
	}
	
	public ConnectFourView(int numRows, int numCols) {
		pieces = new GOval[numRows][numCols];
		buttons = new TouchButton[numCols];
	}
	
	public int numRows() {
		return pieces.length;
	}
	
	public int numCols() {
		if(pieces.length > 0) return pieces[0].length;
		return 0;
	}
	
	public void addMove(int player, int col) { // Blindly adds a move
		int numRows = pieces.length;
		int numCols = pieces[0].length;
		if(col < 0 || col >= numCols) return;
		if(player != PLAYER_ONE && player != PLAYER_TWO) return;
		for(int row = numRows - 1; row >= 0; row--) {
			if(pieces[row][col] != null) continue;
			pieces[row][col] = new GOval(cellWidth - 2 * PIECE_MARGIN, cellWidth - 2 * PIECE_MARGIN);
			GOval piece = pieces[row][col];
			piece.setFilled(true);
			if(player == PLAYER_ONE) {
				piece.setFillColor(PLAYER_ONE_COLOR);
			} else {
				piece.setFillColor(PLAYER_TWO_COLOR);
			}
			add(piece, boardX + col * cellWidth + PIECE_MARGIN, boardY + row * cellWidth + PIECE_MARGIN);
		}
	}
	
	public void draw() {
		calcCellWidth();
		drawButtons();
		drawBoard();
		drawBoardLines();
		drawPieces();
	}
	
	private void drawButtons() {
		for(int col = 0; col < buttons.length; col++) {
			buttons[col] = new TouchButton(cellWidth - 2 * BUTTON_MARGIN, BUTTON_HEIGHT, BUTTON_COLOR, Integer.toString(col));
			add(buttons[col], boardX + col * cellWidth + BUTTON_MARGIN, BUTTON_MARGIN);
		}
	}
	
	private void drawBoard() {
		int numRows = pieces.length;
		int numCols = pieces[0].length;
		GRect board = new GRect(boardX, boardY, numCols * cellWidth, numRows * cellWidth);
		board.setFilled(true);
		board.setFillColor(BOARD_COLOR);
		add(board);
	}
	
	private void drawBoardLines() {
		int numRows = pieces.length;
		int numCols = pieces[0].length;
		for(int i = 1; i <= numCols; i++) {
			add(new GLine(boardX + i * cellWidth, boardY, boardX + i * cellWidth, boardY + numRows * cellWidth));
		}
		for(int i = 1; i <= numRows; i++) {
			add(new GLine(boardX, boardY + i * cellWidth, boardX + numCols * cellWidth, boardY + i * cellWidth));
		}
	}
	
	private void drawPieces() {
		int numRows = pieces.length;
		int numCols = pieces[0].length;
		for(int row = 0; row < numRows; row++) {
			for(int col = 0; col < numCols; col++) {
				if(pieces[row][col] == null) continue;
				GOval piece = pieces[row][col];
				piece.setSize(cellWidth - 2 * PIECE_MARGIN, cellWidth - 2 * PIECE_MARGIN);
				add(piece, boardX + col * cellWidth + PIECE_MARGIN, boardY + row * cellWidth + PIECE_MARGIN);
			}
		}
	}
	
	private void calcCellWidth() {
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
	}
	
	public int getHumanMove() {
		return 0;
	}
	
	public void showMessage(String msg) {
		// TODO: Implement this
	}
}
