import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class GameSystem {
	private Board board;
	private Player[] players;
	private int turnNumber = 0;
	public int dayNumber = 1;
	private List<Action> actionList;
	private int playerNum;

	GameSystem (int playerNum, Board board, Player[] players) 
	{
		//playerNum must start with 0, not 1
		this.playerNum = playerNum;
		this.board = board;
		this.players = players;

		actionList = new ArrayList<Action>();
	}
	
	public void updateDay()
	{
		dayNumber++;
	}
	
	public Player getCurrentPlayer()
	{
		return players[turnNumber];
	}
	
	public void updateCurrentPlayer (int newPlayerNum)
	{
		turnNumber = newPlayerNum;
	}
	
	public void updateAvailableActions (int actionIndex) 
	{
		Player currentPlayer = players[turnNumber];
		int[] dollarsForUpgrade = {4, 10, 18, 28, 40};
		int[] creditsForUpgrade = {5, 10, 15, 20, 25};

		// clear the action list to be filled in this method
		actionList.clear();

		// Act preconditions: player currently has a role
		if(currentPlayer.currentRole != null) {
			actionList.add( new Act(currentPlayer) );
		}

		// rehearse preconditions:
		//   player is currently in a role
		//   practiceTokens + rank < budget (because further rehearsals would be useless)
		if(currentPlayer.currentRole != null && 
		   currentPlayer.practiceTokens < currentPlayer.location.scene.getBudget()) {
			actionList.add( new Rehearse(currentPlayer) );
		}

		// TakeRole preconditions:
		//   player's current rank >= requested role's rank
		//   player's current set contains the requested role
		//   player is not currently in a role
		for(Role r : currentPlayer.location.getRoles()) {
			if(currentPlayer.rank >= r.getRank()) {
				actionList.add( new TakeRole(currentPlayer, r) );
			}
		}

		// move preconditions:
		//   player has not yet moved on their turn
		//   player does is not in a role
		if(currentPlayer.currentRole == null && !currentPlayer.hasMoved) {
			for(Set s : currentPlayer.location.getNeighbors()) {
				actionList.add( new Move(currentPlayer, s) );
			}
		}

		// upgrade preconditions:
		//   player is in casting office
		//   player rank < 6
		//   player can afford to upgrade
		if(currentPlayer.location.getName().equals("office")) { // this is kind of a hack - should probably make this more clean later
			for(int upgradeRank = currentPlayer.rank; upgradeRank < 6; upgradeRank++) {
				if(currentPlayer.credits > creditsForUpgrade[upgradeRank]) {
					actionList.add( new Upgrade(currentPlayer, upgradeRank, true) );
				}

				if(currentPlayer.dollars > dollarsForUpgrade[upgradeRank]) {
					actionList.add( new Upgrade(currentPlayer, upgradeRank, false) );
				}
			}
		}


		// at this point, all actions available to the given player should be in the action list.
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
	
	
}
