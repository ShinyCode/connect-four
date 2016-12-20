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
	private static final int DEFAULT_DEPTH = 3;
	private int maxDepth;
	private RandomGenerator rgen = RandomGenerator.getInstance();
	
	public MinimaxAI() {
		maxDepth = DEFAULT_DEPTH;
	}
	
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
	
	private double eval(ConnectFourModel model) {
		return model.countThrees(PLAYER_ONE) - 2 * model.countThrees(PLAYER_TWO);
	}

}
