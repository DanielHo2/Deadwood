
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
		if(role.isExtra()) {
			return "Take the extra role of " + role.getName() + ".";
		} else {
			return "Take the lead role of " + role.getName() + ".";
		}
	}
	
	public void takeAction ()
	{
		role.requestRole(actor);
	}
}
