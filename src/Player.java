//added a role attribute so that it can be easily accessed through player
public class Player {
	private String name;
	private int rank = 1;
	private int dollars = 0;
	private int credits = 0;
	private Set location;
	private int practiceTokens = 0;
	private Role currentRole;
	
	//action attributes
	private boolean useCredits;
	private int requestedRank;
	private Role requestedRole;
	private Set moveRequest;
	
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
	
	public void wantCredit(boolean credits)
	{
		useCredits = credits;
	}
	
	public void changeRequestedRank(int rank) {
		requestedRank = rank;
	}
	
	public void changeRequestedRole(Role r) {
		requestedRole = r;
	}
	
	public void changeMoveRequest(Set s) {
		moveRequest = s;
	}
	
	public boolean checkActions(int actionIndex) {
		int[] dollarsForUpgrade = {4, 10, 18, 28, 40};
		int[] creditsForUpgrade = {5, 10, 15, 20, 25};
		
		switch (actionIndex) {
		case 0://0 Act preconditions: player currently has a role
			if (currentRole != null) {
				return false;
			} break;
		case 1://1 TakeRole preconditions: player's current rank >= requestedRole.rank
			if (rank >= requestedRole.getRank()) {
				return false;
			} break;
		case 2://2 Rehearse preconditions: practiceTokens + rank < budget
			if (practiceTokens + rank < location.scene.getBudget()) {
				return false;
			} break;
		case 3://3 Move preconditions: player currently does not have a role
			if (currentRole != null) {
				return false;
			} break;
		case 4://4 Upgrade preconditions: (player has not maxed rank && requestedRank < currentRank) && player can afford to upgrade to requestedRank
			if (rank == 6 || rank > requestedRank) {
				return false;
			} 
			
			//checks if player can afford upgrade
			if(useCredits == true) {
				if(credits < creditsForUpgrade[requestedRank-2]) {
					return false;
				}
			} else {
				if(dollars < dollarsForUpgrade[requestedRank-2]) {
					return false;
				}
			}
			break;
		}
		return true;
	}
}
