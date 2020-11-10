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
	private boolean hasActedOrRehearsed = false;

	
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
		return location.scene;
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

	public boolean getActedOrRehearsed()
	{
		return hasActedOrRehearsed;
	}

	public void setActedOrRehearsed(boolean b)
	{
		hasActedOrRehearsed = b;
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

	public List<Action> availableActions() 
	{
		int[] dollarsForUpgrade = {4, 10, 18, 28, 40};
		int[] creditsForUpgrade = {5, 10, 15, 20, 25};

		ArrayList<Action> result = new ArrayList<>();

		// Act preconditions: 
		//   player currently has a role
		//   player has not yet acted or rehearsed
		if(currentRole != null && !hasActedOrRehearsed) {
			result.add( new Act(this) );
		}

		// rehearse preconditions:
		//   player is currently in a role
		//   practiceTokens + rank < budget (because further rehearsals would be useless)
		//   player has not yet acted or rehearsed
		if(currentRole != null && 
		   practiceTokens < location.scene.getBudget() &&
		   !hasActedOrRehearsed) {
			result.add( new Rehearse(this) );
		}

		// TakeRole preconditions:
		//   player's current rank >= requested role's rank
		//   player's current set contains the requested role
		//   player is not currently in a role
		for(Role r : location.getRoles()) {
			if(rank >= r.getRank()) {
				result.add( new TakeRole(this, r) );
			}
		}

		// move preconditions:
		//   player has not yet moved on their turn
		//   player does is not in a role
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

		// EndTurn preconditions: none!
		result.add( new EndTurn(this, game) );


		// at this point, all actions available to the given player should be in the array list.
		return result;
	}
}
