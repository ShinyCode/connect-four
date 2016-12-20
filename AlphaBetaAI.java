import java.util.ArrayList;
import java.util.List;

import acm.util.RandomGenerator;

/**
 * Implements a ConnectFourAI that uses depth-limited minimax search with alpha-beta pruning.
 * 
 * @author Mark Sabini
 *
 */
public class AlphaBetaAI extends ConnectFourAI implements ConnectFourConstants {
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
	 * A list of the best moves to take from the starting state
	 */
	private List<Integer> bestMoves;
	
	/**
	 * Creates an AlphaBetaAI that searches to the default depth.
	 */
	public AlphaBetaAI() {
		maxDepth = DEFAULT_DEPTH;
	}
	
	/**
	 * Creates a AlphaBetaAI that searches to the specified maximum depth.
	 * 
	 * @param maxDepth the maximum depth to search
	 */
	public AlphaBetaAI(int maxDepth) {
		if(maxDepth <= 0) throw new IllegalArgumentException();
		this.maxDepth = maxDepth;
	}
	
	@Override
	public int getMove(int[][] boardState) {
		bestMoves = null;
		ConnectFourModel model = new ConnectFourModel(boardState);
		alphaBeta(model, PLAYER_ONE, maxDepth, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
		if(bestMoves.isEmpty()) return NO_MOVE;
		return bestMoves.get(rgen.nextInt(bestMoves.size()));
	}
	
	/**
	 * Performs minimax search with alpha-beta pruning from the given (model, player) state.
	 * 
	 * @param model the current state of the game
	 * @param player the current player who is moving
	 * @param depth the remaining depth of the search
	 * @param alpha the current value of alpha
	 * @param beta the current value of beta
	 * @return the minimax value of the state
	 */
	private double alphaBeta(ConnectFourModel model, int player, int depth, double alpha, double beta) {
		if(model.isFull()) return 0.0;
		int result = model.checkWin();
		if(result == PLAYER_ONE) return Double.POSITIVE_INFINITY;
		else if (result == PLAYER_TWO) return Double.NEGATIVE_INFINITY;
		if(depth == 0) return eval(model);
		if(player == PLAYER_ONE) {
			if(depth == maxDepth) bestMoves = new ArrayList<Integer>();
			double value = Double.NEGATIVE_INFINITY;
			for(int col = 0; col < model.numCols(); col++) {
				if(!model.makeMove(player, col)) continue;
				double newValue = alphaBeta(model, -player, depth, alpha, beta);
				if(newValue > value) {
					value = newValue;
					if(depth == maxDepth) {
						bestMoves.clear();
						bestMoves.add(col);
					}
				} else if(depth == maxDepth && newValue == value) bestMoves.add(col);
				if(newValue > alpha) alpha = newValue;
				model.undoMove();
				if(alpha > beta) break;
			}
			return value;
		} else if (player == PLAYER_TWO) {
			double value = Double.POSITIVE_INFINITY;
			for(int col = 0; col < model.numCols(); col++) {
				if(!model.makeMove(player, col)) continue;
				double newValue = alphaBeta(model, -player, depth - 1, alpha, beta);
				if(newValue < value) value = newValue;
				if(newValue < beta) beta = newValue;
				model.undoMove();
				if(alpha > beta) break;
			}
			return value;
		}
		return 0.0;
	}
	
	
	private double eval(ConnectFourModel model) {
		return model.countThrees(PLAYER_ONE) - 2 * model.countThrees(PLAYER_TWO);
	}

}
