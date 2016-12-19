
public class RandomAI extends ConnectFourAI {
	private int delay;
	
	public RandomAI() {
		delay = 0;
	}
	
	public RandomAI(int delay) {
		this.delay = delay;
	}
	
	@Override
	public int getMove(int[][] boardState) {
		try {
			Thread.sleep(delay);
		} catch(InterruptedException ex) {}
		int numRows = boardState.length;
		int numCols = boardState[0].length;
		for(int col = 0; col < numCols; col++) {
			for(int row = numRows - 1; row >= 0; row--) {
				
			}
		}
	}

}
