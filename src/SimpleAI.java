package src;
import java.util.ArrayList;
import java.util.List;

import acm.util.RandomGenerator;

/**
 * Implements a ConnectFourAI that responds to occurences of near-wins/losses
 * (3 in a row for NUM_IN_A_ROW = 4), but otherwise plays randomly.
 * 
 * @author Mark Sabini
 *
 */
public class SimpleAI extends ConnectFourAI implements ConnectFourConstants {
	/**
	 * The optional delay to use for the AI
	 */
	private int delay;
	
	/**
	 * Random generator for move selection
	 */
	private RandomGenerator rgen = RandomGenerator.getInstance();
	
	/**
	 * Creates a SimpleAI with no delay (so the AI begins working immediately)
	 */
	public SimpleAI() {
		delay = 0;
	}
	
	/**
	 * Creates a SimpleAI with the specified delay (so the AI can be slowed down, and waits
	 * before it begins to work)
	 * 
	 * @param delay the delay to use for the AI
	 */
	public SimpleAI(int delay) {
		this.delay = delay;
	}
	
	@Override
	public int getMove(int[][] boardState) {
		try {
			Thread.sleep(delay);
		} catch(InterruptedException ex) {}
		ConnectFourModel model = new ConnectFourModel(boardState);
		List<Integer> dangerCols = new ArrayList<Integer>();
		List<Integer> winningCols = new ArrayList<Integer>();
		List<Integer> emptyCols = new ArrayList<Integer>();
		for(int col = 0; col < model.numCols(); col++) {
			if(model.colIsFull(col)) continue;
			emptyCols.add(col);
			model.makeMove(PLAYER_ONE, col);
			if(model.checkWin() != NO_PLAYER) winningCols.add(col);
			model.undoMove();
			model.makeMove(PLAYER_TWO, col);
			if(model.checkWin() != NO_PLAYER) dangerCols.add(col);
			model.undoMove();
		}
		if(!winningCols.isEmpty()) return winningCols.get(rgen.nextInt(winningCols.size())); // AI will win
		else if(!dangerCols.isEmpty()) return dangerCols.get(rgen.nextInt(dangerCols.size())); // Adversary will win
		else if(!emptyCols.isEmpty()) return emptyCols.get(rgen.nextInt(emptyCols.size()));
		else return NO_MOVE;
	}

}
