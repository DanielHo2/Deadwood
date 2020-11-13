import java.util.List;

public class SetUp {
	private Deadwood controller;
	
	public void initialize() 
	{
		try {
			Set[] setArr = ParseXML.parseBoardFile();			
			Scene[] sceneArr = ParseXML.parseCardsFile();
			
			Board boardInUse = new Board(setArr, sceneArr);

			boardInUse.dealScenes();
			boardInUse.refillShotCounters();

			controller = new Deadwood();

			List<String> playerNames = controller.setUpPlayers();

			Player[] playerArr = new Player[playerNames.size()];

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
		
			GameSystem gameSystem = new GameSystem(boardInUse, playerArr);
			DeadwoodView view = new DeadwoodView(gameSystem);

			controller.setView(view);
		} catch (Exception e) {
			System.out.println( e.getMessage() );
			e.printStackTrace();
		}
	}

	public Deadwood getController()
	{
		return controller;
	}
}