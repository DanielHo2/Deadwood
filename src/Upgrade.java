
public class Upgrade implements Action{
	private Player actor;
	private int rank;
	private boolean useCredits;
	
	Upgrade(Player p, int r, boolean credits)
	{
		actor = p;
		rank = r;
		useCredits = credits;
	}
	
	public String actionDescription()
	{
		return "Upgrade to rank " + String.valueOf(rank) + ( useCredits ? "using credits." : "using dollars." ) ;
	}

	public void takeAction() {
		
	}
}
