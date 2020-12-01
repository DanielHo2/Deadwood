
public class Rehearse implements Action{
	private Player actor;
	
	Rehearse(Player p)
	{
		actor = p;
	}
	
	public String actionDescription ()
	{
		return "Rehearse for your current role.";
	}
	
	public String takeAction ()
	{
		actor.givePracticeTokens();

		actor.getGame().nextTurn();
		return (actor.getName() + " has rehearsed and now has " + actor.getPracticeTokens() + " Practice Tokens\n");
	}
}
