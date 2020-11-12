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
			
			//remove a shot - this automatically pays out and wraps the scene if shots reach 0
			actor.getSet().removeShot();
		} else { //failed roll
			if(actor.getRole().isExtra() == true) { //on extra - give 1 dollar
				actor.addDollars(1);
			}
			// on main - give nothing
		}

		actor.getGame().nextTurn();
	}
}
