//skeleton done
public class Set {
	private String name;
	private Set[] neighbors;
	private Area area;
	private int takesMax;
	private int takesLeft;
	private Area[] takeAreas;
	private Role[] roles;
	private boolean hasScene;
	private Scene scene;
	
	Set (String n, Area a, Area[] takes, Role[] parts) 
	{
		name = n;
		area = a;

		/* we can't initialize sets with neighbors already
		   determined, because the first set we initialize
		   would have to take a list of neighboring sets,
		   which we can't have yet.  for that reason, we
		   start with the neighbors array empty, and fill
		   it in after initialization. */

		neighbors = new Set[0];

		if(takes != null) {
			takesMax = takes.length;
			takesLeft = takesMax;
			takeAreas = takes;
		}

		roles = parts;

		hasScene = false;
		scene = null;
	}
	
	public void addNeighbor (Set s)
	{
		int newSize = neighbors.length + 1;

		Set[] newNeighbors = new Set[newSize];

		for(int i = 0; i < neighbors.length; i++) {
			newNeighbors[i] = neighbors[i];
		}

		newNeighbors[newSize - 1] = s;

		neighbors = newNeighbors;
	}
	
	public void dealScene(Scene s)
	{
		
	}
	
	public void wrapScene()
	{
		
	}
	
	public void removeShot()
	{
		
	}
	
	public void refillShots()
	{
		
	}
	
	public boolean hasScene()
	{
		return hasScene;
	}

	public String getName()
	{
		return name;
	}

	public Set[] getNeighbors()
	{
		return neighbors;
	}
}
