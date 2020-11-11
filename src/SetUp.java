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
		}
	}
	
	public void gameSystemSetUp ()
	{
		gameSystem = new GameSystem(boardInUse, playerArr);
	}
	
	
}