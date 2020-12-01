public class Scene {
	private int budget;
	private String name;
	private String description;
	private String imageFile;
	private Role[] roles;
	private boolean flipped;
	
	Scene (int budget, String name, String image, String description, Role[] roles)
	{
		this.budget = budget;
		this.name = name;
		this.imageFile = image;
		this.description = description;
		this.roles = roles;
	}
	
	public boolean isFlipped() 
	{
		return flipped;
	}

	public boolean hasPlayer()
	{
		for(Role r : roles) {
			if(r.isTaken()) return true;
		}

		return false;
	}
	
	public void flip()
	{
		if (flipped) {
			flipped = false;
		} else {
			flipped = true;
		}
	}

	public String getName()
	{
		return name;
	}

	public String getDescription()
	{
		return description;
	}

	public int getBudget()
	{
		return budget;
	}

	public Role[] getRoles()
	{
		return roles;
	}

	public String getImage() {
		return imageFile;
	}
}
