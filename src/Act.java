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
	
	public String takeAction()
	{
		int adjustedDieRoll = GameSystem.rollDie() + actor.getPracticeTokens();
		int returnMessage;
		// will be empty if no pay out occurs.
		// otherwise contains information on how much each player got payed.
		String payOutMessage = ""; 
		
		//successful roll
		if(adjustedDieRoll >= actor.getSet().getScene().getBudget()) {
			if(actor.getRole().isExtra() == false) { //on main role - give 2 credits
				actor.addCredits(2);
				returnMessage = 0;
			} else { // on extra role - give 1 credit & 1 dollar
				actor.addCredits(1);
				actor.addDollars(1);
				returnMessage = 1;
			}
			
			//remove a shot - this automatically pays out and wraps the scene if shots reach 0
			payOutMessage = actor.getSet().removeShot();
		} else { //failed roll
			if(actor.getRole().isExtra() == true) { //on extra - give 1 dollar
				actor.addDollars(1);
				returnMessage = 2;
			} else {// on main - give nothing
				returnMessage = 3;
			}
		}

		actor.setMoved(false);
		actor.getGame().nextTurn();
		boolean nextDay = actor.getGame().updateDay(); // if enough scenes are done, go to next day

		if(nextDay) {
			if(!actor.getGame().gameFinished()) {
				payOutMessage += "\n\nThe current day has ended.\n";
				payOutMessage += "All players have returned to the trailers, and new scenes have been dealt.\n\n";
			} else {
				payOutMessage += "\n\n The current day has ended.\n\n";
			}
		}

		switch (returnMessage) {
			case 0:
				return "Successful roll: +2 credits\n" + payOutMessage;
			case 1:
				return "Successful roll: +1 credit +1 dollar\n" + payOutMessage;
			case 2:
				return "Failed roll: +1 credit\n";
			case 3:
				return "Failed roll\n";
			default:
				return "Error";
		}
	}
}
