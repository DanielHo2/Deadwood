import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;


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
	
	public String wrapScene()
	{
		String result = "The scene is done filming!\n\n";;
		if(scene.hasPlayer())
			result += payOut();

		removePlayers();

		scene = null;
		hasScene = false;

		return result;
	}
	
	private String payOut() {
		HashMap<Player, Integer> payments = new HashMap<>();
		int[] diceArr = GameSystem.rollDice(scene.getBudget());

		//payout for main actors
		for(int i = 0; i < diceArr.length; i++) {
			int modulusCount = i % getMainRoles().length;

			Role current = getMainRoles()[modulusCount];

			if(current.isTaken()) {
				Player player = current.takenBy();
				
				player.addDollars(diceArr[i]);

				if(!payments.containsKey(player)) {
					payments.put(player, diceArr[i]);
				} else {
					payments.put( player, payments.get(player) + diceArr[i] );
				}
			}
		}
			
		//payout for extra actors
		for(int i = 0; i < getExtraRoles().length; i++) {
			Role current = getExtraRoles()[i];

			if(current.isTaken()) {
				Player player = current.takenBy();

				player.addDollars( current.getRank() );

				if(!payments.containsKey(player)) {
					payments.put(player, current.getRank());
				} else {
					payments.put(player, payments.get(player) + current.getRank());
				}
			}
		}

		String result = "";

		for(Player p : payments.keySet()) {
			result += p.getName() + " received " + payments.get(p) + " dollars.\n";
		}

		return result;
	}

	private void removePlayers()
	{
		for(Role r : getRoles()) {
			if(r.isTaken()) {
				r.takenBy().removePracticeTokens();
				r.removePlayer();
			}
		}
	}

	/**
	 * Removes 1 shot counter from the current set.  If the number of shot counters
	 * hits zero, the scene is wrapped.
	 * @return A blank string if the scene does not wrap.  If it does wrap, a summary of the payout.
	 */
	public String removeShot()
	{
		shotCounters--;
		if(shotCounters == 0) { 
			return wrapScene();
		}
		return "";
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
