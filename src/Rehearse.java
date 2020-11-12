
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
		return ("You have rehearsed and now have: " + actor.getPracticeTokens() + " Practice Tokens\n");
	}
}
