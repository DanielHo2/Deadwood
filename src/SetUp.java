import java.util.List;

public class SetUp {
	private Board boardInUse;
	private Player[] playerArr;
	private GameSystem gameSystem;
	
	public void initializeBoard() 
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
	}
	
	//board must be initialized pre-playerSetUp
	public void playerSetUp (int numberOfPlayers, List<String> playerNames) 
	{
		playerArr = new Player[numberOfPlayers];
		for(int i = 0; i < numberOfPlayers; i++) {
			playerArr[i] = new Player(playerNames.get(i));
			playerArr[i].changeSet(boardInUse.getTrailers());
			if (numberOfPlayers == 5) {
				playerArr[i].addCredits(2);
			}else if (numberOfPlayers == 6) {
				playerArr[i].addCredits(4);
			} else if (numberOfPlayers > 8) {
				playerArr[i].setRank(2);
			}
			
		}
		
		gameSystemSetUp(numberOfPlayers);
		
		for(int i = 0; i < numberOfPlayers; i++) {
			playerArr[i].setGame(gameSystem);
		}
	}
	
	public void gameSystemSetUp (int numberOfPlayers)
	{
		gameSystem = new GameSystem(boardInUse, playerArr, numberOfPlayers);
	}
	
	
}