package tictactoe;

public enum Reward {
	
	TIE(-10), WIN(100), LOSS(-100), NONE(0);
	
	public final int amount;
	
	/**
	 * Creates a reward with the given amount.
	 * 
	 * @param amount The amount for the reward.
	 */
	private Reward(int amount) {
		this.amount = amount;
	}
}
