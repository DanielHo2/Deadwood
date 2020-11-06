//Daniel Ho, Jared Hechter

public class Deadwood {
	
	public static void main(String args[]) 
	{
		try {
			Set[] test = ParseXML.parseBoardFile();
			Scene[] test2 = ParseXML.parseCardsFile();

			Board b = new Board(test, test2);

			System.out.println("Everything works!");

			System.out.println("Testing action updating: ");

			// create some players, put them in some arbitrary region on the board
			// with some arbitaray role/rank/dollars/credits to make sure
			// the action lists are coming out right.
			Player[] players = { new Player("Test 1"), new Player("Test 2") };

			players[0].location = test[2];
			players[1].location = b.getCastingOffice();

			players[0].rank = 3;
			players[1].rank = 3;

			players[0].dollars = 2000;
			players[1].dollars = 2000;

			players[0].credits = 2000;
			players[1].credits = 2000;

			GameSystem gameSystem = new GameSystem(b, players);

			gameSystem.updateAvailableActions();

			for(Action a : gameSystem.getActions()) {
				System.out.println(a.actionDescription());
			}

			gameSystem.nextTurn();
			gameSystem.updateAvailableActions();

			for(Action a : gameSystem.getActions()) {
				System.out.println(a.actionDescription());
			}

		} catch(Exception e) {
			System.out.println( e.getMessage() );
			e.printStackTrace();
		}
		
	}

}
