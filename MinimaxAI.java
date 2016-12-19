
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
	
	private double minimax(int[][] boardState, int player, int depth) {
		ConnectFourModel model = new ConnectFourModel(boardState);
		int result = model.checkWin();
		if(result == PLAYER_ONE) {
			return Double.POSITIVE_INFINITY;
		} else if (result == PLAYER_TWO) {
			return Double.NEGATIVE_INFINITY;
		}
		if(depth == 0) return eval(boardState);
		if(player == PLAYER_ONE) {
			double value = Double.NEGATIVE_INFINITY;
			
		} else if (player == PLAYER_TWO) {
			double value = Double.POSITIVE_INFINITY;
		}
		return 0.0;
	}
	
	private double eval(int[][] boardState) {
		return 0.0;
	}

}
