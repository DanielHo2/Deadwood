import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class GameSystem {
	private Board board;
	private Player[] players;
	private int turnNumber = 0;
	private int dayNumber = 1;
	private int maxDays;
	private List<Action> actionList;

	GameSystem (Board board, Player[] players) 
	{
		this.board = board;
		this.players = players;

		for(Player p : this.players) {
			p.setGame(this);
		}

		if(players.length < 4) {
			maxDays = 3;
		} else {
			maxDays = 4;
		}
	}
	
	/**
	 * Checks if the condition to move to the next day (only 1 scene left on the board) is met.
	 * If so, moves to the next day, returns all players to the trailers, and deals more scenes,
	 * or returns early if the final day has been completed. 
	 * @return true if the game moved to the next day, false if it didn't.
	 */
	public boolean updateDay()
	{
		if(board.numberOfScenes() == 1) {
			// move to next day
			dayNumber++;

			// end if the final day has been finished
			if(dayNumber > maxDays) {
				return true;
			}

			// otherwise, prepare for the next day
			for(Player p : players) {
				// each player leaves their role if in one, and returns to the trailers
				p.leaveRole();
				p.changeSet(board.getTrailers());
				// replace all shot counters
				board.refillShotCounters();
			}

			// deal 10 more scenes to the board
			board.dealScenes();

			return true;
		}

		// don't do anything if there are at least 2 cards remaining
		return false;
	}
	
	public Player getCurrentPlayer()
	{
		return players[turnNumber];
	}
	
	public Player[] getPlayerArr()
	{
		return players;
	}

	public Board getBoard()
	{
		return board;
	}
	
	public void updateAvailableActions () 
	{
		actionList = players[turnNumber].availableActions();
	}


	public List<Action> getActions()
	{
		return actionList;
	}
	
	public String takeAction (int actionIndex)
	{
		return actionList.get(actionIndex).takeAction();	
	}
	
	public static int rollDie ()
	{
		return ThreadLocalRandom.current().nextInt(1, 6 + 1);
	}
	
	public static int[] rollDice (int numDice)
	{
		int[] diceArr = new int[numDice];
		for(int i = 0; i <= numDice-1; i++) {
			diceArr[i] = ThreadLocalRandom.current().nextInt(1, 6 + 1);
		}

		// quick bubble sort to get the dice into sorted order - we're
		// working with 6 elements at most, so O(n^2) should be negligible.

		for(int i = 0; i < diceArr.length; i++) {
			for(int j = 0; j < diceArr.length - i; j++) {
				if( diceArr[i] < diceArr[j] ) {
					int temp = diceArr[i];
					diceArr[i] = diceArr[j];
					diceArr[j] = temp;
				}
			}
		}
		
		return diceArr;
	}

	public void nextTurn() 
	{
		turnNumber++;
		turnNumber %= players.length;
	}

	public boolean gameFinished()
	{
		return dayNumber > maxDays;
	}
	
	public int getTurnNumber()
	{
		return turnNumber;
	}

	public int getDay()
	{
		return dayNumber;
	}
}
