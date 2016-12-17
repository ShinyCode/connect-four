
import acm.program.*;

public class ConnectFourController extends ConsoleProgram implements ConnectFourConstants {
	private ConnectFourModel model;
	
	public void run() {
		model = new ConnectFourModel(DEFAULT_ROWS, DEFAULT_COLS);
	}
}
