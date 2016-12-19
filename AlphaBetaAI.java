import acm.util.RandomGenerator;


public class AlphaBetaAI extends ConnectFourAI implements ConnectFourConstants {
	private static final int DEFAULT_DEPTH = 3;
	private int maxDepth;
	private RandomGenerator rgen = RandomGenerator.getInstance();
	
	public AlphaBetaAI() {
		maxDepth = DEFAULT_DEPTH;
	}
	
	public AlphaBetaAI(int maxDepth) {
		if(maxDepth <= 0) throw new IllegalArgumentException();
		this.maxDepth = maxDepth;
	}
	
	@Override
	public int getMove(int[][] boardState) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	private double alphaBeta(ConnectFourModel model, int player, int depth, double alpha, double beta) {
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
