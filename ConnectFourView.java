import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import dashboard.control.*;
import acm.graphics.*;

public class ConnectFourView extends GCanvas implements ConnectFourConstants, MouseListener, ComponentListener {
	private GOval[][] pieces;
	private TouchButton[] buttons;
	private GRect playerIndicator;
	private boolean waitingForHumanMove = false;
	private int humanMove = 0;
	private double boardX = 0;
	private double boardY = 0;
	private double barY = 0;
	private double cellWidth = 0;
	private double currPlayer = PLAYER_ONE;
	
	public ConnectFourView() {
		pieces = new GOval[DEFAULT_ROWS][DEFAULT_COLS];
		buttons = new TouchButton[DEFAULT_COLS];
		addMouseListener(this);
	}
	
	public ConnectFourView(int numRows, int numCols) {
		pieces = new GOval[numRows][numCols];
		buttons = new TouchButton[numCols];
		addMouseListener(this);
	}
	
	public int numRows() {
		return pieces.length;
	}
	
	public int numCols() {
		if(pieces.length > 0) return pieces[0].length;
		return 0;
	}
	
	public void addMove(int col) { // Blindly adds a move
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
	
	public void draw() {
		calcCellWidth();
		drawButtons();
		drawBoard();
		drawBoardHoles();
		drawBoardLines();
		drawPieces();
		drawInfoBar();
	}
	
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
	
	private void drawBoard() {
		GRect board = new GRect(boardX, boardY, numCols() * cellWidth, numRows() * cellWidth);
		board.setFilled(true);
		board.setFillColor(BOARD_COLOR);
		add(board);
	}
	
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
	
	private void drawBoardLines() {
		for(int i = 1; i <= numCols(); i++) {
			add(new GLine(boardX + i * cellWidth, boardY, boardX + i * cellWidth, boardY + numRows() * cellWidth));
		}
		for(int i = 1; i <= numRows(); i++) {
			add(new GLine(boardX, boardY + i * cellWidth, boardX + numCols() * cellWidth, boardY + i * cellWidth));
		}
	}
	
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
	
	private void drawInfoBar() {
		GRect background = new GRect(0, barY, getWidth(), getHeight());
		background.setFilled(true);
		background.setFillColor(INFOBAR_COLOR);
		add(background);
		GLine separator = new GLine(INFOBAR_HEIGHT, barY, INFOBAR_HEIGHT, getHeight());
		add(separator);
		GRect textBackground = new GRect(getWidth() - 2 * INDICATOR_MARGIN - INFOBAR_HEIGHT, INFOBAR_HEIGHT - 2 * INDICATOR_MARGIN);
		textBackground.setFilled(true);
		textBackground.setFillColor(TEXT_BACKGROUND_COLOR);
		add(textBackground, INFOBAR_HEIGHT + INDICATOR_MARGIN, barY + INDICATOR_MARGIN);
		playerIndicator = new GRect(INFOBAR_HEIGHT - 2 * INDICATOR_MARGIN, INFOBAR_HEIGHT - 2 * INDICATOR_MARGIN);
		playerIndicator.setFilled(true);
		setIndicatorColor();
		add(playerIndicator, INDICATOR_MARGIN, barY + INDICATOR_MARGIN);
	}
	
	private void setIndicatorColor() {
		playerIndicator.setFillColor((currPlayer == PLAYER_ONE) ? PLAYER_ONE_COLOR : PLAYER_TWO_COLOR);
	}
	
	private void calcCellWidth() {
		double availXSpace = getWidth() - 2 * BOARD_SIDE_MARGIN;
		double availYSpace = getHeight() - (2 * BUTTON_MARGIN + BUTTON_HEIGHT) - BOARD_TOP_MARGIN - BOARD_BOTTOM_MARGIN - INFOBAR_HEIGHT;
		if(((double)numRows() / numCols()) >= availYSpace / availXSpace) { // Too tall, use scale to availYSpace
			cellWidth = availYSpace / numRows();
		} else {
			cellWidth = availXSpace / numCols();
		}
		boardX = BOARD_SIDE_MARGIN + (availXSpace - numCols() * cellWidth) / 2;
		boardY = 2 * BUTTON_MARGIN + BUTTON_HEIGHT + BOARD_TOP_MARGIN + (availYSpace - numRows() * cellWidth) / 2;
		barY = boardY + numRows() * cellWidth + BOARD_BOTTOM_MARGIN;
	}
	
	public int getHumanMove() {
		waitingForHumanMove = true;
		while(waitingForHumanMove) { // Will be changed by MouseEvent thread
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {}
		} 
		return humanMove;
	}
	
	public void showMessage(String msg) {
		// TODO: Implement this
	}
	
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
	
	public void componentHidden(ComponentEvent e) { }
	public void componentMoved(ComponentEvent e) { }
	public void componentResized(ComponentEvent e) { draw(); }
	public void componentShown(ComponentEvent e) { }
}
