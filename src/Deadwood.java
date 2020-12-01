//Daniel Ho, Jared Hechter
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class Deadwood {
	private DeadwoodGUI view;
    private Scanner scan;

    public Deadwood() 
    {
		scan = new Scanner(System.in);
	}
	
	public void setView(DeadwoodGUI v)
	{
		view = v;
	}

    public void runGame()
    {
		view.setVisible(true);
    	
        view.viewActions();
		
		view.viewScore();
    }

    private String getInput()
    {
        System.out.print("> ");
        return scan.nextLine();
	}
	
	public List<String> setUpPlayers()
	{
		List<String> result = new ArrayList<>();

		while(result.size() < 8) {
			System.out.printf("Enter player #%d's name (enter a blank line to finish): ", result.size()+1);

			String next = scan.nextLine();
			if(next.isBlank()) {
				if(result.size() < 2) {
					System.out.println("At least 2 players are needed.");
					continue;
				} else {
					break;
				}
			}

			result.add(next);
		}

		return result;
	}
	public static void main(String args[]) 
	{
		SetUp setUp = new SetUp();
		setUp.initialize();

		Deadwood controller = setUp.getController();

		controller.runGame();
	}
}
