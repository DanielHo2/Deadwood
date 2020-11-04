import java.util.*;

public class Scene {
	private int budget;
	private String name;
	private String description;
	private String imageFile;
	private List<Role> roles;
	private boolean flipped;
	
	Scene (int budget, String name, String image, String description)
	{
		this.budget = budget;
		this.name = name;
		this.imageFile = image;
		this.description = description;
	}
	
	public void addRole(Role r)
	{
		roles.add(r);
	}
	
	public boolean isFlipped() 
	{
		return flipped;
	}
	
	public void flip()
	{
		if (flipped = true) {
			flipped = false;
		} else {
			flipped = true;
		}
	}
}
