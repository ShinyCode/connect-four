
public class MinimaxAI extends ConnectFourAI implements ConnectFourConstants {
	private static final int DEFAULT_DEPTH = 5;
	private int maxDepth;
	
	public MinimaxAI() {
		maxDepth = DEFAULT_DEPTH;
	}
	
	public MinimaxAI(int maxDepth) {
		if(maxDepth <= 0) throw new IllegalArgumentException();
		this.maxDepth = maxDepth;
	}
	
	@Override
	public int getMove(int[][] boardState) {
		
		return 0;
	}

}
