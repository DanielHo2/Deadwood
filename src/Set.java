import java.util.List;
import java.util.ArrayList;


public class Set {
	private String name;
	private Set[] neighbors;
	private Area area;
	private Area[] takeAreas;
	private int shotsMax;
	private int shotCounters;
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
			shotsMax = takes.length;
			shotCounters = shotsMax;
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
		scene = s;
		hasScene = true;
	}
	
	public void wrapScene()
	{
		if(scene.hasPlayer()) payOut();

		removePlayers();

		scene = null;
		hasScene = false;
	}
	
	private void payOut() {
		List<Player> mainActors = getMainActors();
		List<Player> extraActors = getExtraActors();
		int[] diceArr = GameSystem.rollDice(scene.getBudget());

		//payout for main actors
		for(int i = 0; i < diceArr.length; i++) {
			int modulusCount = i % mainActors.size();
			mainActors.get(modulusCount).addDollars(diceArr[i]);;
		}
			
		//payout for actors
		for(int i = 0; i < extraActors.size(); i++) {
			extraActors.get(i).addDollars(extraActors.get(i).getRole().getRank());
		}
	}

	private void removePlayers()
	{
		for(Role r : getRoles()) {
			if(r.isTaken()) {
				r.removePlayer();
			}
		}
	}

	public boolean removeShot()
	{
		shotCounters--;
		if(shotCounters == 0) { 
			wrapScene();
			return true;
		}
		return false;
	}
	
	public void refillShots()
	{
		shotCounters = shotsMax;
	}

	public int getShotsLeft()
	{
		return shotCounters;
	}
	
	public boolean hasScene()
	{
		return hasScene;
	}
	
	public Scene getScene()
	{
		return scene;
	}

	public String getName()
	{
		return name;
	}

	public Set[] getNeighbors()
	{
		return neighbors;
	}

	public Role[] getRoles()
	{
		if(scene == null) return new Role[0];

		int roleCount = roles.length + scene.getRoles().length;
		Role[] result = new Role[roleCount];

		int index = 0;

		for(int i = 0; i < roles.length; i++) {
			result[index] = roles[i];
			index++;
		}

		for(int i = 0; i < scene.getRoles().length; i++) {
			result[index] = scene.getRoles()[i];
			index++;
		}

		return result;

	}

	public Role[] getMainRoles() 
	{
		return scene.getRoles();
	}

	public Role[] getExtraRoles()
	{
		return roles;
	}

	// returns players sorted in order from lowest to highest rank of their role
	public List<Player> getMainActors()
	{
		// there are, at most, 6 roles per card, at least in theory,
		// because the max rank is 6, and the roles must all be different
		// ranks for them to be able to be unambigiously sorted.
		// in practice, there are about 3 roles per card, but by using
		// 6 we can sort the players by role rank very easily
		Player[] players = new Player[6];

		for(Role r : scene.getRoles()) {
			if(r.isTaken()) {
				players[r.getRank()-1] = r.takenBy();
			}
		}

		// at this point, we've filled in all of the main roles, slotting them
		// into the index representing their rank - this means that the players
		// array is sorted from lowest to highest role rank, with null values
		// potentially dividing any given entries.  by going through and removing
		// these null values, we'll have the result we want
		List<Player> result = new ArrayList<>();

		for(int i = 0; i < players.length; i++) {
			if(players[i] != null) {
				result.add(players[i]);
			}
		}

		return result;
	}

	public List<Player> getExtraActors()
	{
		List<Player> result = new ArrayList<>();

		for(Role r : roles) {
			if(r.isTaken()) {
				result.add(r.takenBy());
			}
		}

		return result;
	}
}
