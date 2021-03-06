
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
		String result = actor.getName() + " moved to: " + to.getName() + "\n";
		actor.changeSet(to);
		
		if(actor.getSet().hasScene() && !actor.getSet().getScene().isFlipped()) {
			actor.getSet().getScene().flip();
			result += "The scene in this Set has been revealed to be " + actor.getSet().getScene().getName() + "\n";
		}
		
		actor.setMoved(true);

		return result;
	}
}
