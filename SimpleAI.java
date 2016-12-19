import java.util.ArrayList;
import java.util.List;

import acm.util.RandomGenerator;


public class SimpleAI extends ConnectFourAI implements ConnectFourConstants {
	private RandomGenerator rgen = RandomGenerator.getInstance();
	
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
		if(!winningCols.isEmpty()) return winningCols.get(rgen.nextInt(winningCols.size()));;
		else return emptyCols.get(rgen.nextInt(emptyCols.size()));
		return 0;
	}

}
