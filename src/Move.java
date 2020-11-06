
public class Move implements Action{
	private Player actor;
	private Set to;
	
	Move(Player p, Set t) 
	{
		actor = p;
		to = t;
	}
	
	public String actionDescription ()
	{
		return "";
	}
	
	public void takeAction ()
	{
		
	}
}
