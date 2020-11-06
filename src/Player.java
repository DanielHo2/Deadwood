import java.util.List;
import java.util.ArrayList;

public class Player {
	public String name;
	public int rank = 1;
	public int dollars = 0;
	public int credits = 0;
	public Set location;
	public int practiceTokens = 0;
	public Role currentRole;

	public boolean hasMoved = false;
	
	public Player(String name) {
		this.name = name;
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
	
	public void changeRole(Role newRole)
	{
		currentRole = newRole;
	}

	public List<Action> availableActions() {
		int[] dollarsForUpgrade = {4, 10, 18, 28, 40};
		int[] creditsForUpgrade = {5, 10, 15, 20, 25};

		ArrayList<Action> result = new ArrayList<>();

		// Act preconditions: player currently has a role
		if(currentRole != null) {
			result.add( new Act(this) );
		}

		// rehearse preconditions:
		//   player is currently in a role
		//   practiceTokens + rank < budget (because further rehearsals would be useless)
		if(currentRole != null && 
		   practiceTokens < location.scene.getBudget()) {
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
		if(location.getName().equals("office")) { // checking for String equality feels like a bit of a hack, but it'll work for now
			for(int upgradeRank = rank; upgradeRank < 6; upgradeRank++) {
				if(credits > creditsForUpgrade[upgradeRank-1]) {
					result.add( new Upgrade(this, upgradeRank+1, true) );
				}

				if(dollars > dollarsForUpgrade[upgradeRank-1]) {
					result.add( new Upgrade(this, upgradeRank+1, false) );
				}
			}
		}


		// at this point, all actions available to the given player should be in the array list.
		return result;
	}
}
