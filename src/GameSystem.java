

// A bunch of imports needed for XML parsing
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.util.concurrent.ThreadLocalRandom;



public class GameSystem {
	private Board board;
	private Player[] players;
	private int turnNumber = 0;
	public int dayNumber = 1;
	private Action[][] actionList;
	private boolean[][] actionListCheck;
	private int playerNum;

	GameSystem (int playerNum, Board board, Player[] players) 
	{
		 //playerNum must start with 0, not 1
		 this.playerNum = playerNum;
		 this.board = board;
		 this.players = players;
		 
	}
	
	public void updateDay()
	{
		dayNumber++;
	}
	
	public Player getCurrentPlayer()
	{
		return players[turnNumber];
	}
	
	//creates a 2d array of actions. rows are the playerNum, columns are the action objects--> [Act, TakeRole, Rehearse, Move, Upgrade] 
	//also creates a corresponding 2d arrays of booleans matching up, so that we can determine which ones are currently able to be used
	public void createPlayerActions () 
	{
		actionList = new Action[players.length][5];
		actionListCheck = new boolean[players.length][5];
		
		// 0 = act, 1 = takerole, 2 = rehearse, 3 = move, 4 = upgrade
		for(int i = 0; i < players.length; i++) {
			actionList[i][0] = new Act(players[i]);	
			actionList[i][1] = new TakeRole(players[i], players[i].currentRole);
			actionList[i][2] = new Rehearse(players[i]);
			actionList[i][3] = new Move(players[i], players[i].location);
			actionList[i][4] = new Upgrade(players[i]);
			
			for(int j = 0; j < 5; j++) {
				actionListCheck[i][j] = true;
			}
		}
	}
	
	public void updateCurrentPlayer (int newPlayerNum)
	{
		turnNumber = newPlayerNum;
	}
	
	public boolean[] getAvailableActions ()
	{
		return actionListCheck[turnNumber];
	}
	
	//run through the actionList for the current player and switch the booleans in actionListCheck if the action can be done
	public void updateAvailableActions () 
	{
		
	}
	
	public void takeAction (int actionIndex)
	{
		actionList[playerNum][actionIndex].takeAction();	
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
	
	
}
