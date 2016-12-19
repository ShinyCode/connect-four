
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
		Thread.sleep(delay);
		
		return 0;
	}

}
