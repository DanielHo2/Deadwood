import java.util.List;

public class SetUp {
	private Board boardInUse;
	private Player[] playerArr;
	private GameSystem gameSystem;
	private DeadwoodView view;
	private Deadwood controller;
	
	public void initialize() 
	{
		try {
			Set[] setArr = ParseXML.parseBoardFile();			
			Scene[] sceneArr = ParseXML.parseCardsFile();
			
			boardInUse = new Board(setArr, sceneArr);
		} catch (Exception e) {
			System.out.println( e.getMessage() );
			e.printStackTrace();
		}
		
		boardInUse.dealScenes();
		boardInUse.refillShotCounters();

		controller = new Deadwood();

		playerSetUp(controller.getPlayers());
	}
	
	//board must be initialized pre-playerSetUp
	private void playerSetUp (List<String> playerNames) 
	{
		playerArr = new Player[playerNames.size()];

		for(int i = 0; i < playerNames.size(); i++) {
			playerArr[i] = new Player(playerNames.get(i));
			playerArr[i].changeSet(boardInUse.getTrailers());

			if (playerNames.size() == 5) {
				playerArr[i].addCredits(2);
			}else if (playerNames.size() == 6) {
				playerArr[i].addCredits(4);
			} else if (playerNames.size() >= 7) {
				playerArr[i].setRank(2);
			}
			
		}
		
		gameSystem = new GameSystem(boardInUse, playerArr);
		view = new DeadwoodView(gameSystem);

		controller.setView(view);
	}

	public Deadwood getController()
	{
		return controller;
	}
}