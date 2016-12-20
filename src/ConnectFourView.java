package src;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import dashboard.control.*;
import acm.graphics.*;

/**
 * The ConnectFourView provides a display that depicts the state
 * of a ConnectFour board. While it has nearly no logic on its own,
 * the ConnectFourView does have basic error checking to make sure
 * moves are valid.
 * 
 * @author Mark Sabini
 *
 */
public class ConnectFourView extends GCanvas implements ConnectFourConstants, MouseListener, ComponentListener {
	/**
	 * The various pieces that are drawn on the board
	 */
	private GOval[][] pieces;
	
	/**
	 * Buttons to allow users to input moves
	 */
	private TouchButton[] buttons;
	
	/**
	 * A colored rectangle to indicate what player is moving
	 */
	private GRect playerIndicator;
	
	/**
	 * A label for displaying a message to the user
	 */
	private GLabel msgLabel;
	
	/**
	 * Whether the view is waiting for a human to click
	 */
	private boolean waitingForHumanMove = false;
	
	/**
	 * The move that the human chose, if the view was expecting a click
	 */
	private int humanMove = 0;
	
	/**
	 * The x-coordinate of the top-left corner of the board
	 */
	private double boardX = 0;
	
	/**
	 * The y-coordinate of the top-left corner of the board
	 */
	private double boardY = 0;
	
	/**
	 * How wide each square cell is
	 */
	private double cellWidth = 0;
	
	/**
	 * The current player who is moving
	 */
	private double currPlayer = PLAYER_ONE;
	
	/**
	 * Creates a ConnectFourView with DEFAULT_ROWS rows and DEFAULT_COLS columns.
	 * The default values for the constants are usually 6 and 7, respectively.
	 */
	public ConnectFourView() {
		pieces = new GOval[DEFAULT_ROWS][DEFAULT_COLS];
		buttons = new TouchButton[DEFAULT_COLS];
		addMouseListener(this);
		addComponentListener(this);
	}
	
	/**
	 * Creates a ConnectFourView with the specified number of rows and columns.
	 * 
	 * @param numRows the number of rows for the model
	 * @param numCols the number of columns for the model
	 */
	public ConnectFourView(int numRows, int numCols) {
		pieces = new GOval[numRows][numCols];
		buttons = new TouchButton[numCols];
		addMouseListener(this);
		addComponentListener(this);
	}
	
	/**
	 * Returns the number of rows in the depicted board
	 * 
	 * @return the number of rows in the depicted board
	 */
	public int numRows() {
		return pieces.length;
	}
	
	/**
	 * Returns the number of columns in the depicted board
	 * 
	 * @return the number of columns in the depicted board
	 */
	public int numCols() {
		if(pieces.length > 0) return pieces[0].length;
		return 0;
	}
	
	/**
	 * Blindly adds a move to the board at the given column.
	 * 
	 * @param col the column at which to make the move
	 */
	public void addMove(int col) {
		if(col < 0 || col >= numCols()) return;
		for(int row = numRows() - 1; row >= 0; row--) {
			if(pieces[row][col] != null) continue;
			pieces[row][col] = new GOval(cellWidth - 2 * PIECE_MARGIN, cellWidth - 2 * PIECE_MARGIN);
			GOval piece = pieces[row][col];
			piece.setFilled(true);
			if(currPlayer == PLAYER_ONE) {
				piece.setFillColor(PLAYER_ONE_COLOR);
			} else {
				piece.setFillColor(PLAYER_TWO_COLOR);
			}
			add(piece, boardX + col * cellWidth + PIECE_MARGIN, boardY + row * cellWidth + PIECE_MARGIN);
			currPlayer *= -1;
			setIndicatorColor();
			return;
		}
	}
	
	/**
	 * Resets the ConnectFourView to its original state.
	 */
	public void reset() {
		currPlayer = PLAYER_ONE;
		pieces = new GOval[DEFAULT_ROWS][DEFAULT_COLS];
		buttons = new TouchButton[DEFAULT_COLS];
		msgLabel.setLabel("");
		draw();
	}
	
	/**
	 * Clears the ConnectFourView and draws its representation. This is called
	 * once from ConnectFour after the board is created, and also when the window
	 * is resized.
	 */
	public void draw() {
		removeAll();
		calcCellWidth();
		drawButtons();
		drawBoard();
		drawBoardHoles();
		drawBoardLines();
		drawPieces();
		drawInfoBar();
		drawMsgLabel();
	}
	
	/**
	 * Draws the buttons used by human players to input moves.
	 */
	private void drawButtons() {
		for(int col = 0; col < buttons.length; col++) {
			buttons[col] = new TouchButton(cellWidth - 2 * BUTTON_MARGIN, BUTTON_HEIGHT, BUTTON_COLOR, Integer.toString(col));
			add(buttons[col], boardX + col * cellWidth + BUTTON_MARGIN, BUTTON_MARGIN);
			final int chosenCol = col;
			buttons[col].setOffAction(new Runnable(){
				public void run() {
					humanMove = chosenCol;
					waitingForHumanMove = false;
				}
			});
		}
	}
	
	/**
	 * Draws the background of the board itself.
	 */
	private void drawBoard() {
		GRect board = new GRect(boardX, boardY, numCols() * cellWidth, numRows() * cellWidth);
		board.setFilled(true);
		board.setFillColor(BOARD_COLOR);
		add(board);
	}
	
	/**
	 * Draws the holes in the board.
	 */
	private void drawBoardHoles() {
		for(int row = 0; row < numRows(); row++) {
			for(int col = 0; col < numCols(); col++) {
				GOval hole = new GOval(cellWidth - 2 * PIECE_MARGIN, cellWidth - 2 * PIECE_MARGIN);
				hole.setFilled(true);
				hole.setFillColor(HOLE_COLOR);
				add(hole, boardX + col * cellWidth + PIECE_MARGIN, boardY + row * cellWidth + PIECE_MARGIN);
			}
		}
	}
	
	/**
	 * Draws the grid lines that demarcate different board cells.
	 */
	private void drawBoardLines() {
		for(int i = 1; i <= numCols(); i++) {
			add(new GLine(boardX + i * cellWidth, boardY, boardX + i * cellWidth, boardY + numRows() * cellWidth));
		}
		for(int i = 1; i <= numRows(); i++) {
			add(new GLine(boardX, boardY + i * cellWidth, boardX + numCols() * cellWidth, boardY + i * cellWidth));
		}
	}
	
	/**
	 * Draws the pieces on the board.
	 */
	private void drawPieces() {
		for(int row = 0; row < numRows(); row++) {
			for(int col = 0; col < numCols(); col++) {
				if(pieces[row][col] == null) continue;
				GOval piece = pieces[row][col];
				piece.setSize(cellWidth - 2 * PIECE_MARGIN, cellWidth - 2 * PIECE_MARGIN);
				add(piece, boardX + col * cellWidth + PIECE_MARGIN, boardY + row * cellWidth + PIECE_MARGIN);
			}
		}
	}
	
	/**
	 * Draws the information bar at the bottom of the view.
	 */
	private void drawInfoBar() {
		GRect background = new GRect(0, getHeight() - INFOBAR_HEIGHT, getWidth(), INFOBAR_HEIGHT);
		background.setFilled(true);
		background.setFillColor(INFOBAR_COLOR);
		add(background);
		GLine separator = new GLine(INFOBAR_HEIGHT, getHeight() - INFOBAR_HEIGHT, INFOBAR_HEIGHT, getHeight());
		add(separator);
		GRect textBackground = new GRect(getWidth() - 2 * INDICATOR_MARGIN - INFOBAR_HEIGHT, INFOBAR_HEIGHT - 2 * INDICATOR_MARGIN);
		textBackground.setFilled(true);
		textBackground.setFillColor(TEXT_BACKGROUND_COLOR);
		add(textBackground, INFOBAR_HEIGHT + INDICATOR_MARGIN, getHeight() - INFOBAR_HEIGHT + INDICATOR_MARGIN);
		playerIndicator = new GRect(INFOBAR_HEIGHT - 2 * INDICATOR_MARGIN, INFOBAR_HEIGHT - 2 * INDICATOR_MARGIN);
		playerIndicator.setFilled(true);
		setIndicatorColor();
		add(playerIndicator, INDICATOR_MARGIN, getHeight() - INFOBAR_HEIGHT + INDICATOR_MARGIN);
	}
	
	/**
	 * Draws the message label that broadcasts messages to the user.
	 */
	private void drawMsgLabel() {
		if(msgLabel == null) msgLabel = new GLabel("");
		msgLabel.setFont(MESSAGE_FONT);
		add(msgLabel, INFOBAR_HEIGHT + INDICATOR_MARGIN + MESSAGE_MARGIN, getHeight() - INFOBAR_HEIGHT + INDICATOR_MARGIN);
		msgLabel.move(0, (INFOBAR_HEIGHT - 2 * INDICATOR_MARGIN + msgLabel.getAscent()) / 2);
	}
	
	/**
	 * Sets the player indicator to the appropriate color based on the current player.
	 */
	private void setIndicatorColor() {
		playerIndicator.setFillColor((currPlayer == PLAYER_ONE) ? PLAYER_ONE_COLOR : PLAYER_TWO_COLOR);
	}
	
	/**
	 * Calculates the cell width for the board based on the available space.
	 */
	private void calcCellWidth() {
		double availXSpace = getWidth() - 2 * BOARD_SIDE_MARGIN;
		double availYSpace = getHeight() - (2 * BUTTON_MARGIN + BUTTON_HEIGHT) - BOARD_TOP_MARGIN - BOARD_BOTTOM_MARGIN - INFOBAR_HEIGHT;
		if(((double)numRows() / numCols()) >= availYSpace / availXSpace) { // Too tall, scale according to availYSpace
			cellWidth = availYSpace / numRows();
		} else { // Too wide, scale according to availXSpace
			cellWidth = availXSpace / numCols();
		}
		boardX = BOARD_SIDE_MARGIN + (availXSpace - numCols() * cellWidth) / 2;
		boardY = 2 * BUTTON_MARGIN + BUTTON_HEIGHT + BOARD_TOP_MARGIN + (availYSpace - numRows() * cellWidth) / 2;
	}
	
	/**
	 * Waits for the human player to input a move (via the buttons), and then returns
	 * the option that was clicked.
	 * 
	 * @return the move chosen by the human
	 */
	public int getHumanMove() {
		waitingForHumanMove = true;
		while(waitingForHumanMove) { // Will be changed by MouseEvent thread
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {}
		} 
		return humanMove;
	}
	
	/**
	 * Displays a message to the user using the information bar.
	 * 
	 * @param msg the message to display
	 */
	public void showMessage(String msg) {
		msgLabel.setLabel(msg);
	}
	
	// Implements the MouseListener interface. These are pulled from the dashboard library,
	// to enable buttons to work
	
	@Override
	public void mouseClicked(MouseEvent e) {
		GObject o = getElementAt(e.getX(), e.getY());
		if(o != null) e.translatePoint(-(int)o.getX(), -(int)o.getY());
		if(o instanceof Control) ((Control) o).mouseClicked(e);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		GObject o = getElementAt(e.getX(), e.getY());
		if(o != null) e.translatePoint(-(int)o.getX(), -(int)o.getY());
		if(o instanceof Control) ((Control) o).mouseEntered(e);
	}
	
	@Override
	public void mouseExited(MouseEvent e) {
		GObject o = getElementAt(e.getX(), e.getY());
		if(o != null) e.translatePoint(-(int)o.getX(), -(int)o.getY());
		if(o instanceof Control) ((Control) o).mouseExited(e);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		GObject o = getElementAt(e.getX(), e.getY());
		if(o != null) e.translatePoint(-(int)o.getX(), -(int)o.getY());
		if(o instanceof Control) ((Control) o).mousePressed(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		GObject o = getElementAt(e.getX(), e.getY());
		if(o != null) e.translatePoint(-(int)o.getX(), -(int)o.getY());
		if(o instanceof Control) ((Control) o).mouseReleased(e);
	}
	
	// Implements the ComponentListener interface
	
	@Override
	public void componentHidden(ComponentEvent e) { }
	
	@Override
	public void componentMoved(ComponentEvent e) { }
	
	@Override
	public void componentResized(ComponentEvent e) {
		draw();
	}
	
	@Override
	public void componentShown(ComponentEvent e) { }
}
