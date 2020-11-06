//added a role attribute so that it can be easily accessed through player
public class Player {
	public int rank = 1;
	private int dollars = 0;
	private int credits = 0;
	public Set location;
	private int practiceTokens = 0;
	public Role currentRole;
	public boolean useCredits;
	public int requestedRank;
	public String name;
	
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
}
