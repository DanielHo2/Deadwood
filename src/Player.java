import java.util.List;
import java.util.ArrayList;

public class Player {
	private String name;
	private int rank = 1;
	private int dollars = 0;
	private int credits = 0;
	private Set location;
	private int practiceTokens = 0;
	private Role currentRole;


	private GameSystem game;

	private boolean hasMoved = false;
	
	public Player(String name) 
	{
		this.name = name;
	}

	public int getRank()
	{
		return rank;
	}

	public int getDollars()
	{
		return dollars;
	}

	public int getCredits()
	{
		return credits;
	}

	public Set getSet()
	{
		return location;
	}

	public Scene getScene()
	{
		return location.getScene();
	}

	public void setRank(int r)
	{
		rank = r;
	}

	public boolean hasRole()
	{
		return currentRole != null;
	}

	public Role getRole()
	{
		return currentRole;
	}

	public void setGame(GameSystem game) 
	{
		this.game = game;
	}

	public boolean getMoved()
	{
		return hasMoved;
	}

	public void setMoved(boolean b)
	{
		hasMoved = b;
	}

	public void addDollars(int amount)
	{
		dollars += amount;
	}
	
	public void takeDollars(int amount)
	{
		dollars -= amount;
	}
	
	public void addCredits(int amount)
	{
		credits += amount;
	}
	
	public void takeCredits(int amount)
	{
		credits -= amount;
	}
	
	public void givePracticeTokens()
	{
		practiceTokens += 1;
	}
	
	public int getPracticeTokens()
	{
		return practiceTokens;
	}
	
	public void removePracticeTokens()
	{
		practiceTokens = 0;
	}
	
	public void changeSet(Set newLocation)
	{
		location = newLocation;
	}
	
	public void setName(String enteredName)
	{
		name = enteredName;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void changeRole(Role newRole)
	{
		currentRole = newRole;
	}

	public void leaveRole()
	{
		currentRole = null;
	}
	
	//return list of main players on set with roles
	public List<Player> getMainPlayersOnSet() 
	{
		Player[] players = game.getPlayerArr();
		ArrayList<Player> onSetPlayers = new ArrayList<>();
		
		for(int i = 0; i < players.length; i++) {
			if(players[i].getSet() == location && 
			   players[i].getRole() != null &&
			   players[i].getRole().isExtra()) {
				onSetPlayers.add(players[i]);
			}
		}
		return onSetPlayers;
	}
	
	public List<Player> getExtraPlayersOnSet() 
	{
		Player[] players = game.getPlayerArr();
		ArrayList<Player> onSetPlayers = new ArrayList<>();
		
		for(int i = 0; i < players.length; i++) {
			if(players[i].getSet() == location && 
			   players[i].getRole() != null &&
			   players[i].getRole().isExtra() == false) {
				onSetPlayers.add(players[i]);
			}
		}
		return onSetPlayers;
	}

	public List<Action> availableActions() 
	{
		int[] dollarsForUpgrade = {4, 10, 18, 28, 40};
		int[] creditsForUpgrade = {5, 10, 15, 20, 25};

		ArrayList<Action> result = new ArrayList<>();

		// Act preconditions: 
		//   player currently has a role
		//   player has not yet acted or rehearsed
		//      (acting or rehearsing automatically ends the turn, so we don't need to check for this)
		if(hasRole()) {
			result.add( new Act(this) );
		}

		// rehearse preconditions:
		//   player is currently in a role
		//   practiceTokens + rank < budget (because further rehearsals would be useless)
		//   player has not yet acted or rehearsed 
		//      (acting or rehearsing automatically ends the turn, so we don't need to check for this)
		if(currentRole != null && 
		   practiceTokens < location.getScene().getBudget()) {
			result.add( new Rehearse(this) );
		}

		// TakeRole preconditions:
		//   player's current rank >= requested role's rank
		//   player's current set contains the requested role
		//   player is not currently in a role
		//   role is not currently taken
		for(Role r : location.getRoles()) {
			if(rank >= r.getRank() && !r.isTaken() && !hasRole()) {
				result.add( new TakeRole(this, r) );
			}
		}

		// move preconditions:
		//   player has not yet moved on their turn
		//   player is not in a role
		if(currentRole == null && !hasMoved) {
			for(Set s : location.getNeighbors()) {
				result.add( new Move(this, s) );
			}
		}

		// upgrade preconditions:
		//   player is in casting office
		//   player rank < 6
		//   player can afford to upgrade
		if(location == game.getBoard().getCastingOffice()) {
			for(int upgradeRank = rank; upgradeRank < 6; upgradeRank++) {
				if(credits > creditsForUpgrade[upgradeRank-1]) {
					result.add( new Upgrade(this, upgradeRank+1, creditsForUpgrade[upgradeRank-1], true) );
				}

				if(dollars > dollarsForUpgrade[upgradeRank-1]) {
					result.add( new Upgrade(this, upgradeRank+1, dollarsForUpgrade[upgradeRank-1], false) );
				}
			}
		}

		// EndTurn preconditions:
		//   cannot end turn if both in a role, and you've not acted or rehearsed yet.
		//      (acting or rehearsing automatically ends the turn, so we don't need to check for this)
		if( !(hasRole()) ) {
			result.add( new EndTurn(this) );
		}


		// at this point, all actions available to the given player should be in the array list.
		return result;
	}

	public int getScore()
	{
		return dollars + credits + (rank * 5);
	}

	public GameSystem getGame()
	{
		return game;
	}
}
