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

}
