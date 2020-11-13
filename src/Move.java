
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
	
	public String takeAction ()
	{
		actor.changeSet(to);
		if(!actor.getSet().getScene().isFlipped()) {
			actor.getSet().getScene().flip();
		}
		actor.setMoved(true);
		
		return ("Moved to: " + to.getName() +"\n");
	}
}
