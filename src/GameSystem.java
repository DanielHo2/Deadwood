
public class GameSystem {
	private Board board;
	private Player[] players;
	private int turnNumber = 1;
	private int dayNumber = 1;
	private Action[] availableActions;
	private int playerNum = 0;
	
	GameSystem (int numPlayers) 
	{
		 
	}
	
	public Player getCurrentPlayer()
	{
		return players[0];
	}
	
	public Action[] getAvailableActions ()
	{
		return availableActions;
	}
	
	public void updateAvailableActions () 
	{
		
	}
	
	public void takeAction (int actionIndex)
	{
		
	}
	
	public int rollDie ()
	{
		return -1;
	}
	
	public int[] roleDice ()
	{
		int[] placeholder = {};
		return placeholder;
	}
	
	
}
