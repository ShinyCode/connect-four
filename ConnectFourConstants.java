import java.awt.Color;


public interface ConnectFourConstants {
	public static final int DEFAULT_ROWS = 6;
	public static final int DEFAULT_COLS = 7;
	public static final int NUM_IN_A_ROW = 4;
	public static final int NO_PLAYER = 0;
	public static final int PLAYER_ONE = 1;
	public static final int PLAYER_TWO = -1;
	public static final char PLAYER_ONE_SYMBOL = 'O';
	public static final char PLAYER_TWO_SYMBOL = 'X';
	public static final char EMPTY_SYMBOL = '-';
	public static final int NO_MOVE = -1;
	
	public static final double BOARD_SIDE_MARGIN = 30;
	public static final double BOARD_BOTTOM_MARGIN = 10;
	public static final double BOARD_TOP_MARGIN = 0;
	public static final double BUTTON_MARGIN = 10;
	public static final double BUTTON_HEIGHT = 40;
	public static final double PIECE_MARGIN = 10;
	public static final Color PLAYER_ONE_COLOR = Color.BLACK;
	public static final Color PLAYER_TWO_COLOR = Color.RED;
	public static final Color BOARD_COLOR = Color.BLUE;
	public static final Color BUTTON_COLOR = Color.ORANGE;
	public static final Color HOLE_COLOR = Color.WHITE;
	public static final double INFOBAR_HEIGHT = 50;
	public static final Color INFOBAR_COLOR = Color.BLUE;
	public static final double INDICATOR_MARGIN = 10;
	public static final Color TEXT_BACKGROUND_COLOR = Color.ORANGE;
	public static final String MESSAGE_FONT = "*-*-15";
	public static final double MESSAGE_MARGIN = 10;
	public static final int APP_WIDTH = 800;
	public static final int APP_HEIGHT = 600;
}
