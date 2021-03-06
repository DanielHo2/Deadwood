
public class Upgrade implements Action{
	private Player actor;
	private int rank;
	private int cost;
	private boolean useCredits;
	
	Upgrade(Player p, int r, int amount, boolean credits)
	{
		actor = p;
		rank = r;
		cost = amount;
		useCredits = credits;
	}
	
	public String actionDescription()
	{
		if(useCredits) {
			return "Ugrade to rank " + String.valueOf(rank) + " at the cost of " + String.valueOf(cost) + " credits.";
		} else {
			return "Ugrade to rank " + String.valueOf(rank) + " at the cost of " + String.valueOf(cost) + " dollars.";
		}
	}

	public String takeAction() {
		if(useCredits) {
			actor.takeCredits(cost);
		} else {
			actor.takeDollars(cost);
		}

		actor.setRank(rank);
		if(useCredits == true) {
			return (actor.getName() + " upgraded to rank " + rank + " for " + cost + " credits\n");
		}
		return (actor.getName() + " upgraded to rank " + rank + " for " + cost + "dollars\n");
	}
}
