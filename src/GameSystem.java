

// A bunch of imports needed for XML parsing
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;



public class GameSystem {
	private Board board;
	private Player[] players;
	private int turnNumber = 1;
	private int dayNumber = 1;
	private Action[] availableActions;
	private int playerNum = 0;

	GameSystem (int numPlayers) 
	{
		 
	}
	
	public Player getCurrentPlayer()
	{
		return players[0];
	}
	
	public Action[] getAvailableActions ()
	{
		return availableActions;
	}
	
	public void updateAvailableActions () 
	{
		
	}
	
	public void takeAction (int actionIndex)
	{
		
	}
	
	public static int rollDie ()
	{
		return -1;
	}
	
	public static int[] roleDice (int numDice)
	{
		int[] placeholder = {};
		return placeholder;
	}
	
	
}
