public class EndTurn implements Action {
    Player actor;
    GameSystem game;

    public EndTurn(Player p, GameSystem game) {
        actor = p;
        this.game = game;
    }

    public String actionDescription()
    {
        return "End your turn.";
    }

    public void takeAction()
    {
        actor.hasMoved = false;
        actor.hasActedOrRehearsed = false;
        game.nextTurn();
    }
}
