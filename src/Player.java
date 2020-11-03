//skeleton done
public class Player {
	private int rank = 1;
	private int dollars = 0;
	private int credits = 0;
	private Set location;
	private int practiceTokens = 0;
	
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
}
