import java.util.ArrayList;
import java.util.List;

import acm.util.RandomGenerator;


public class RandomAI extends ConnectFourAI implements ConnectFourConstants {
	private int delay;
	private RandomGenerator rgen = RandomGenerator.getInstance();
	
	public RandomAI() {
		delay = 0;
	}
	
	public RandomAI(int delay) {
		this.delay = delay;
	}
	
	@Override
	public int getMove(int[][] boardState) {
		try {
			Thread.sleep(delay);
		} catch(InterruptedException ex) {}
		ConnectFourModel model = new ConnectFourModel(boardState);
		List<Integer> emptyCols = new ArrayList<Integer>();
		for(int col = 0; col < model.numCols(); col++) {
			for(int row = numRows - 1; row >= 0; row--) {
				if(boardState[row][col] != PLAYER_ONE && boardState[row][col] != PLAYER_TWO) {
					emptyCols.add(col);
					break;
				}
			}
			if(!model.columnIsFull(col))
		}
		if(emptyCols.size() == 0) return NO_MOVE;
		else return emptyCols.get(rgen.nextInt(emptyCols.size()));
	}

}
