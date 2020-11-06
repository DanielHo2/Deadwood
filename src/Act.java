
public class Act implements Action{
	private Player actor;
	
	Act (Player p)
	{
		actor = p;
	}
	
	public String actionDescription()
	{
		return "Act for your current role.";
	}
	
	public void takeAction()
	{
		// todo
	}
}
