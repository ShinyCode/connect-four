package src;
import java.util.ArrayList;
import java.util.List;

import acm.util.RandomGenerator;

/**
 * Implements a ConnectFourAI that plays moves randomly.
 * 
 * @author Mark Sabini
 *
 */
public class RandomAI extends ConnectFourAI implements ConnectFourConstants {
	/**
	 * The optional delay to use for the AI
	 */
	private int delay;
	
	/**
	 * Random generator for move selection
	 */
	private RandomGenerator rgen = RandomGenerator.getInstance();
	
	/**
	 * Creates a RandomAI with no delay (so the AI begins working immediately)
	 */
	public RandomAI() {
		delay = 0;
	}
	
	/**
	 * Creates a RandomAI with the specified delay (so the AI can be slowed down, and waits
	 * before it begins to work)
	 * 
	 * @param delay the delay to use for the AI
	 */
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
			if(!model.colIsFull(col)) emptyCols.add(col);
		}
		if(emptyCols.isEmpty()) return NO_MOVE;
		else return emptyCols.get(rgen.nextInt(emptyCols.size()));
	}

}
