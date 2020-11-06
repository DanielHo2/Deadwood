

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
			actionList[i][1] = new TakeRole(players[i]);
			actionList[i][2] = new Rehearse(players[i]);
			actionList[i][3] = new Move(players[i]);
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
	
	public boolean processActionRequest (int actionIndex, Role requestedRole, boolean useCredits, int requestedRank, Set moveRequest)
	{
		Player currentPlayer = players[turnNumber];
		
		if (actionIndex == 1) {
			currentPlayer.requestedRole = requestedRole;
		} else if (actionIndex == 3) {
			currentPlayer.moveRequest = moveRequest;
		} else if (actionIndex == 4) {
			currentPlayer.requestedRank = requestedRank;
			currentPlayer.useCredits = useCredits;
		}
		
		updateAvailableActions(actionIndex);
		
		//if action is able to be completed, do the action otherwise return false
		if(getAvailableActions()[actionIndex] == true) {
			takeAction(actionIndex);
			return true;
		} else {
			return false;
		}
	}
	
	//checks if action preconditions are met
	public void updateAvailableActions (int actionIndex) 
	{
		Player currentPlayer = players[turnNumber];
		int[] dollarsForUpgrade = {4, 10, 18, 28, 40};
		int[] creditsForUpgrade = {5, 10, 15, 20, 25};
		
		switch (actionIndex) {
		case 0://0 Act preconditions: player currently has a role
			if (currentPlayer.currentRole != null) {
				actionListCheck[turnNumber][0] = false;
			} break;
		case 1://1 TakeRole preconditions: player's current rank >= requestedRole.rank
			if (currentPlayer.rank >= players[turnNumber].requestedRole.getRank()) {
				actionListCheck[turnNumber][1] = false;
			} break;
		case 2://2 Rehearse preconditions: practiceTokens + rank < budget
			if (currentPlayer.practiceTokens + currentPlayer.rank < currentPlayer.location.scene.getBudget()) {
				actionListCheck[turnNumber][2] = false;
			} break;
		case 3://3 Move preconditions: player currently does not have a role
			if (currentPlayer.currentRole != null) {
				actionListCheck[turnNumber][3] = false;
			} break;
		case 4://4 Upgrade preconditions: (player has not maxed rank && requestedRank < currentRank) && player can afford to upgrade to requestedRank
			if (currentPlayer.rank == 6 || currentPlayer.rank > currentPlayer.requestedRank) {
				actionListCheck[turnNumber][4] = false;
				break;
			} 
			
			//checks if player can afford upgrade
			if(currentPlayer.useCredits == true) {
				if(currentPlayer.credits < creditsForUpgrade[currentPlayer.requestedRank-2]) {
					actionListCheck[turnNumber][4] = false;
				}
			} else {
				if(currentPlayer.dollars < dollarsForUpgrade[currentPlayer.requestedRank-2]) {
					actionListCheck[turnNumber][4] = false;
				}
			}
			break;
		}
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
