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
		return 0.0;
	}
	
	private double eval(ConnectFourModel model) {
		return model.countThrees(PLAYER_ONE) - 2 * model.countThrees(PLAYER_TWO);
	}

}
