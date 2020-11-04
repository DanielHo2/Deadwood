//Daniel Ho, Jared Hechter

public class Deadwood {
	
	public static void main(String args[]) 
	{
		try {
			Set[] test = ParseXML.parseBoardFile();

			for(Set s : test) {
				System.out.println(s.getName());
				for(Set n : s.getNeighbors()) {
					System.out.print(n.getName() + " - ");
				}
				System.out.println();
			}
		} catch(Exception e) {
			System.out.println( e.getMessage() );
			e.printStackTrace();
		}
	}

}
