
public class Upgrade implements Action{
	private Player actor;
	//private int rank;
	//private boolean useCredits;
	
	Upgrade(Player p)
	{
		
	}
	
	public String actionDescription()
	{
		return "";
	}

	public void takeAction() {
		
	}
	
	//I added a useCredits flag and requestedRank in the Player class so we don't have to create a new object for each different option
	
}
