//Daniel Ho, Jared Hechter
import java.util.*;

public class Deadwood {
	
	public static void main(String args[]) 
	{
		/*try {
			Set[] test = ParseXML.parseBoardFile();
			Scene[] test2 = ParseXML.parseCardsFile();

			Board b = new Board(test, test2);

			System.out.println("Everything works!");

			System.out.println("Testing action updating: \n");

			// create some players, put them in some arbitrary region on the board
			// with some arbitaray role/rank/dollars/credits to make sure
			// the action lists are coming out right.
			Player[] players = { new Player("Player 1"), new Player("Player 2"), new Player("Player 3") };

			players[0].changeSet(b.getTrailers());
			players[1].changeSet(b.getCastingOffice());
			players[2].changeSet(test[4]);
			players[2].getSet().dealScene(test2[0]);

			players[0].setRank(3);
			players[1].setRank(3);
			players[2].setRank(3);

			players[0].addDollars(2000);
			players[1].addDollars(2000);

			players[0].addCredits(2000);
			players[1].addCredits(2000);

			GameSystem gameSystem = new GameSystem(b, players);

			for(Player p : players) {
				System.out.println("Actions available to " + p.getName());

				gameSystem.updateAvailableActions();

				for(Action a : gameSystem.getActions()) {
					System.out.println(a.actionDescription());
				}

				System.out.println("\n");

				gameSystem.nextTurn();
			}

		} catch(Exception e) {
			System.out.println( e.getMessage() );
			e.printStackTrace();
		}*/
		SetUp test = new SetUp();
		ArrayList<String> test2 = new ArrayList();
		test2.add("one");
		test2.add("two");
		test2.add("three");
		test.initializeBoard();
		test.playerSetUp(3, test2);
		System.out.println("nothing broke");
		
	}

}
