import java.util.ArrayList;
import java.util.List;


public class SimpleAI extends ConnectFourAI implements ConnectFourConstants {

	@Override
	public int getMove(int[][] boardState) {
		ConnectFourModel model = new ConnectFourModel(boardState);
		List<Integer> winningCols = new ArrayList<Integer>();
		List<Integer> emptyCols = new ArrayList<Integer>();
		for(int col = 0; col < model.numCols(); col++) {
			if(model.colIsFull(col)) continue;
			emptyCols.add(col);
			model.makeMove(PLAYER_ONE, col);
			if(model.checkWin() != NO_PLAYER) winningCols.add(col);
			model.undoMove();
		}
		return 0;
	}

}
