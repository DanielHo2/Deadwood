//skeleton done
public class Role {
	private int rank;
	private String name;
	private String line;
	private Area position;
	private boolean extra;
	private boolean taken;
	private Player takenBy;
	
	Role (int rank, String name, String line, Area a, boolean isExtra)
	{
		this.rank = rank;
		this.name = name;
		this.line = line;
		this.position = a;
		this.extra = isExtra;

		taken = false;
		takenBy = null;
	}
	
	public void requestRole (Player p)
	{
		takenBy = p;
		name = p.getName();
		changeTakenStatus();
	}
	
	public void changeTakenStatus ()
	{
		if(taken = false) {
			taken = true;
		} else {
			taken = false;
		}
	}

	public boolean isTaken ()
	{
		return taken;
	}
	
	public boolean isExtra()
	{
		return extra;
	}

	public Player takenBy ()
	{
		return takenBy;
	}

	public int getRank() 
	{
		return rank;
	}

	public String getName() 
	{
		return name;
	}

	public String getLine() 
	{
		return line;
	}

	public Area getArea() 
	{
		return position;
	}
}
