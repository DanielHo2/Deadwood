
public class Move implements Action{
	private Player actor;
	private Set to;
	
	Move(Player p, Set s) 
	{
		actor = p;
		to = s;
	}
	
	public String actionDescription ()
	{
		return "Move to " + to.getName() + ".";
	}
	
	public void takeAction ()
	{
		actor.changeSet(to);
		actor.setMoved(true);
	}
}
