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
		
		//successful roll
		if(adjustedDieRoll >= actor.getScene().getBudget()) {
			if(actor.getRole().isExtra() == false) { //on main role - give 2 credits
				actor.addCredits(2);
				returnMessage = 0;
			} else { // on extra role - give 1 credit & 1 dollar
				actor.addCredits(1);
				actor.addDollars(1);
				returnMessage = 1;
			}
			
			//remove a shot - this automatically pays out and wraps the scene if shots reach 0
			actor.getSet().removeShot();
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
		actor.getGame().updateDay(); // if enough scenes are done, go to next day
		
		switch (returnMessage) {
			case 0:
				return "Successful roll: +2 credits\n";
			case 1:
				return "Successful roll: +1 credit +1 dollar\n";
			case 2:
				return "Failed roll: +1 credit\n";
			case 3:
				return "Failed roll\n";
			default:
				return "Error";
		}
	}
}
