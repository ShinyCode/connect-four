package src;

/**
 * Abstract class representing an AI for ConnectFour.
 * 
 * @author Mark Sabini
 *
 */
public abstract class ConnectFourAI {
	/**
	 * Given the specified board state, returns the move that the AI believes
	 * it should take.
	 * 
	 * @param boardState the current board state. PLAYER_ONE is always the AI, and PLAYER_TWO is always the adversary.
	 * @return a move, where 0 <= move < numCols, or NO_MOVE
	 */
	public abstract int getMove(int[][] boardState);
}
