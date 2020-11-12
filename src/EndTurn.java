public class EndTurn implements Action {
    Player actor;

    public EndTurn(Player p) {
        actor = p;
    }

    public String actionDescription()
    {
        return "End your turn.";
    }

    public String takeAction()
    {
        actor.setMoved(false);
        actor.getGame().nextTurn();
        return ("Turn Ended\n");
    }
}
