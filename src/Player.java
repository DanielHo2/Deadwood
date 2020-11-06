//added a role attribute so that it can be easily accessed through player
public class Player {
	public String name;
	public int rank = 1;
	public int dollars = 0;
	public int credits = 0;
	public Set location;
	public int practiceTokens = 0;
	public Role currentRole;
	
	//action attributes
	public boolean useCredits;
	public int requestedRank;
	public Role requestedRole;
	public Set moveRequest;
	
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
}
