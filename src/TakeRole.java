
public class TakeRole implements Action{
	private Player actor;
	private Role role;
	
	TakeRole (Player p, Role r)
	{
		actor = p;
		role = r;
	}
	
	public String actionDescription()
	{
		return "Take " + role.getName() + ".";
	}
	
	public void takeAction ()
	{
		
	}
}
