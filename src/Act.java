import java.util.List;

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
		int adjustedDieRoll = GameSystem.rollDie() + actor.getPracticeTokens();
		
		//successful roll
		if(adjustedDieRoll >= actor.getScene().getBudget()) {
			if(actor.getRole().isExtra() == false) { //on main role - give 2 credits
				actor.addCredits(2);
			} else { // on extra role - give 1 credit & 1 dollar
				actor.addCredits(1);
				actor.addDollars(1);
			}
			
			//remove a shot and payout if shots are empty
			if(actor.getSet().removeShot()) {
				payOut();
				actor.getSet().wrapScene();
			}
		} else { //failed roll
			if(actor.getRole().isExtra() == true) { //on extra - give 1 dollar
				actor.addDollars(1);
			}
			// on main - give nothing
		}

	}

	
	private void payOut() {
		List<Player> mainPlayerList = actor.getMainPlayersOnSet();
		Player[] mainPlayerArr = new Player[mainPlayerList.size()];
		mainPlayerArr = mainPlayerList.toArray(mainPlayerArr);
		mainPlayerArr = sortOnRoleRank(mainPlayerArr);
		
		List<Player> extraPlayerArr = actor.getExtraPlayersOnSet();
		
		boolean onCard = false;
		
		//checks if there is a player on card
		if(mainPlayerArr.length > 0) {
			onCard = true;
		}
		
		//distribute payout if on card
		if(onCard == true) {
			int numDice = actor.getScene().getBudget();
			int[] diceArr = GameSystem.rollDice(numDice);
			
			//payout for main actors
			for(int i = 0; i < diceArr.length; i++) {
				int modulusCount = i % mainPlayerArr.length;
				mainPlayerArr[modulusCount].addDollars(diceArr[i]);;
			}
			
			//payout for actors
			for(int i = 0; i < extraPlayerArr.size(); i++) {
				extraPlayerArr.get(i).addDollars(extraPlayerArr.get(i).getRole().getRank());
			}
		}
	}
	
	//bubble sort on main role players by role rank
	private Player[] sortOnRoleRank(Player[] playerArr) {
		int n = playerArr.length;
		for (int i = 0; i < n-1; i++) {
			for(int j = 0; j< n-i-1; j++) {
				if(playerArr[j].getRole().getRank() > playerArr[j+1].getRole().getRank()) {
					Player temp = playerArr[j];
					playerArr[j] = playerArr[j+1];
					playerArr[j+1] = temp;
				}
			}
		}
		
		return playerArr;
	}
}
