import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class GameSystem {
	private Board board;
	private Player[] players;
	private int turnNumber = 0;
	private int dayNumber = 1;
	private List<Action> actionList;

	GameSystem (Board board, Player[] players) 
	{
		this.board = board;
		this.players = players;

		for(Player p : this.players) {
			p.setGame(this);
		}
	}
	
	public void updateDay()
	{
		dayNumber++;
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
	
	public void updateCurrentPlayer (int newPlayerNum)
	{
		turnNumber = newPlayerNum;
	}
	
	public void updateAvailableActions () 
	{
		actionList = players[turnNumber].availableActions();
	}


	public List<Action> getActions()
	{
		return actionList;
	}
	
	public void takeAction (int actionIndex)
	{
		actionList.get(actionIndex).takeAction();	
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
		
		return diceArr;
	}

	public void nextTurn() {
		turnNumber++;
		turnNumber %= players.length;
	}
	
	
}
