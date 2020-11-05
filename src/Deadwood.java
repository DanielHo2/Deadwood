//Daniel Ho, Jared Hechter

public class Deadwood {
	
	public static void main(String args[]) 
	{
		try {
			// I'm not entirely sure if the XML parsing works
			// cross-platform or not, so I'm leaving a test of
			// it here in main.  Hopefully when run from any
			// other computer, this should run without issue.
			Set[] test = ParseXML.parseBoardFile();
			Scene[] test2 = ParseXML.parseCardsFile();

			Board b = new Board(test, test2);

			System.out.println("Everything works!");
		} catch(Exception e) {
			System.out.println( e.getMessage() );
			e.printStackTrace();
		}
		
	}

}
