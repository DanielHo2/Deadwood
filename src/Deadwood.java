//Daniel Ho, Jared Hechter
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import javax.swing.JOptionPane;

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

		// wait for the game to end - the JFrame seems to run in a different thread,
		// so we can just have this thread wait until the game ends
		while(!view.gameFinished()) {
			// for SOME reason, unless there's something in this loop, the viewScore method
			// below never gets called
			try {
				Thread.sleep(100);	
			} catch(Exception e) {}
		}
        
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

		String messagePrepend = "";

		while(result.size() < 8) {
			String next = JOptionPane.showInputDialog(messagePrepend + "Enter player #" + (result.size()+1) + "'s name.  Press cancel if there are no more players.");
			
			if(next == null) {
				if(result.size() < 2) {
					messagePrepend = "At least 2 players are needed.  ";
					continue;
				} else {
					break;
				}
			} else if(next.isBlank()) {
				messagePrepend = "Please enter a name.  ";
			}

			messagePrepend = "";
			
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
