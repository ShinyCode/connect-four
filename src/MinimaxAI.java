package src;
import java.util.ArrayList;
import java.util.List;

import acm.util.RandomGenerator;

/**
 * Implements a ConnectFourAI that uses depth-limited minimax search.
 * 
 * @author Mark Sabini
 *
 */
public class MinimaxAI extends ConnectFourAI implements ConnectFourConstants {
	/**
	 * The default search depth to use
	 */
	private static final int DEFAULT_DEPTH = 3;
	
	/**
	 * The maximum search depth for the AI
	 */
	private int maxDepth;
	
	/**
	 * Random generator for equally-good move selection
	 */
	private RandomGenerator rgen = RandomGenerator.getInstance();
	
	/**
	 * Creates a MinimaxAI that searches to the default depth.
	 */
	public MinimaxAI() {
		maxDepth = DEFAULT_DEPTH;
	}
	
	/**
	 * Creates a MinimaxAI that searches to the specified maximum depth.
	 * 
	 * @param maxDepth the maximum depth to search
	 */
	public MinimaxAI(int maxDepth) {
		if(maxDepth <= 0) throw new IllegalArgumentException();
		this.maxDepth = maxDepth;
	}
	
	@Override
	public int getMove(int[][] boardState) {
		ConnectFourModel model = new ConnectFourModel(boardState);
		double value = Double.NEGATIVE_INFINITY;
		List<Integer> bestMoves = new ArrayList<Integer>();
		for(int col = 0; col < model.numCols(); col++) {
			if(!model.makeMove(PLAYER_ONE, col)) continue;
			double newValue = minimax(model, PLAYER_TWO, maxDepth);
			if(newValue > value) {
				value = newValue;
				bestMoves.clear();
				bestMoves.add(col);
			} else if(newValue == value) {
				bestMoves.add(col);
			}
			model.undoMove();
		}
		return bestMoves.get(rgen.nextInt(bestMoves.size()));
	}
	
	/**
	 * Performs minimax search from the given (model, player) state.
	 * 
	 * @param model the current state of the game
	 * @param player the current player who is moving
	 * @param depth the remaining depth of the search
	 * @return the minimax value of the state
	 */
	private double minimax(ConnectFourModel model, int player, int depth) {
		if(model.isFull()) return 0.0;
		int result = model.checkWin();
		if(result == PLAYER_ONE) return Double.POSITIVE_INFINITY;
		else if (result == PLAYER_TWO) return Double.NEGATIVE_INFINITY;
		if(depth == 0) return eval(model);
		if(player == PLAYER_ONE) {
			double value = Double.NEGATIVE_INFINITY;
			for(int col = 0; col < model.numCols(); col++) {
				if(!model.makeMove(player, col)) continue;
				double newValue = minimax(model, -player, depth);
				if(newValue > value) value = newValue;
				model.undoMove();
			}
			return value;
		} else if (player == PLAYER_TWO) {
			double value = Double.POSITIVE_INFINITY;
			for(int col = 0; col < model.numCols(); col++) {
				if(!model.makeMove(player, col)) continue;
				double newValue = minimax(model, -player, depth - 1);
				if(newValue < value) value = newValue;
				model.undoMove();
			}
			return value;
		}
		return 0.0;
	}
	
	/**
	 * A basic static evaluation function that looks at three-in-a-row occurrences,
	 * and acts defensively to stop the adversary from accumulating three-in-a-rows.
	 * 
	 * @param model the current state to evaluate
	 * @return a measure of how good the state is
	 */
	private double eval(ConnectFourModel model) {
		return model.countThrees(PLAYER_ONE) - 2 * model.countThrees(PLAYER_TWO);
	}

}
