
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
	
	public void takeAction ()
	{
		actor.givePracticeTokens();

		actor.getGame().nextTurn();
	}
}
