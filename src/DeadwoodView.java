import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Queue;
import java.util.LinkedList;
import java.util.HashMap;

public class DeadwoodView {
    private GameSystem game;

    public DeadwoodView(GameSystem g)
    {
        game = g;
    }

    private void viewRole(Role r)
    {
        System.out.printf("\t%s: Rank %d.", r.getName(), r.getRank());
        if(r.isTaken()) {
            System.out.printf("  Taken by %s\n", r.takenBy().getName());
        } else {
            System.out.println();
        }

        System.out.printf("\t\t\"%s\"\n\n", r.getLine());
    }

    private void viewBoardState()
    {
        Board b = game.getBoard();

        // print out board details
        int setNumber = 1;
        for(Set s : b.getSets()) {
            if(s.hasScene()) {
                System.out.printf("Set #%d: %s is now filming %s with a budget of %d\n", 
                                   setNumber, s.getName(), s.getScene().getName(), s.getScene().getBudget());

                // main roles
                System.out.println("Main roles:  ");

                for(Role curr : s.getMainRoles()) {
                    viewRole(curr);
                }

                System.out.println();

                // extra roles
                System.out.println("Extra roles: ");

                for(Role curr : s.getExtraRoles()) {
                    viewRole(curr);
                }

                System.out.println();
                
                // reamining shots
                System.out.printf("Remaining shots: %d\n", s.getShotsLeft());
            } else {
                System.out.printf("Set #%d: %s\n", setNumber, s.getName());
            }

            // neighbors
            System.out.print("Neighbors: ");

            for(int i = 0; i < s.getNeighbors().length; i++) {
                System.out.print(s.getNeighbors()[i].getName());
                if(i != s.getNeighbors().length-1) {
                    System.out.print(", ");
                }
            }

            System.out.println();

            System.out.println();

            setNumber++;
        }

        System.out.println();
        System.out.println();
    }

    public void viewScore()
    {
        int maxScore = 0;
        Player winningPlayer = game.getPlayerArr()[0];

        for(Player p : game.getPlayerArr()) {
            int score = p.getScore();

            if(score > maxScore) {
                winningPlayer = p;
            }

            System.out.printf("%s has a score of %d\n", p.getName(), score);
        }

        System.out.printf("\nThe winner is %s!\n", winningPlayer.getName());
    }

    public void viewActions()
    {
        game.updateAvailableActions();

        System.out.printf("Current Player: %s\n\n", game.getCurrentPlayer().getName());

        int actionNumber = 0;
        for(Action a : game.getActions()) {
            System.out.printf("%d - %s\n", ++actionNumber, a.actionDescription());
        }

        System.out.println();

        System.out.println("Enter \"help\" for more commands.\n");
    }

    private void viewPlayerData()
    {
        for(Player p : game.getPlayerArr()) {
            System.out.printf("%s is in the %s", p.getName(), p.getSet().getName());

            if(p.hasRole()) {
                System.out.printf(", currently acting as %s", p.getRole().getName());
            }

            System.out.println();

            System.out.printf("Credits: %d | Dollars: %d | Rank: %d\n\n\n\n", 
                               p.getCredits(), p.getDollars(), p.getRank());
        }

    }

    private void viewPath(int setIndex)
    {
        Set start = game.getCurrentPlayer().getSet();
        Set target = game.getBoard().getSets()[setIndex];

        Queue<Set> currentPositions = new LinkedList<>();
        Queue<Set> previousPositions = new LinkedList<>();
        HashMap<Set, Set> visited = new HashMap<>();

        // breadth first search to find the target set from the current set
        currentPositions.add(start);
        previousPositions.add(null);

        while(currentPositions.size() > 0) {
            Set curr = currentPositions.remove();
            Set prev = previousPositions.remove();

            checkSet(curr, prev, currentPositions, previousPositions, visited);

            if(curr == target) break;
        }

        // trace through the hashmap to get the sequence of sets to visit
        List<Set> path = new ArrayList<>();

        Set current = target;
        while(current != start) {
            if(current != null) {
                path.add(current);
            } else {
                break;
            }
            
            current = visited.get(current);
        }


        // start at... the start, which current is set to
        System.out.printf("%s", current.getName());
        
        for(int i = path.size()-1; i >= 0; i--) {
            System.out.printf(" -> %s", path.get(i).getName());
        }

        System.out.println();
        System.out.println();
    }

    private void checkSet(Set current, Set previous, Queue<Set> currentLocations, 
                             Queue<Set> previousLocations, HashMap<Set,Set> visited) 
    {
        if(visited.containsKey(current)) return;

        visited.put(current, previous);
        
        for(Set s : current.getNeighbors()) {
            currentLocations.add(s);
            previousLocations.add(current);
        }

        return;

    }

    private void viewHelp()
    {
        System.out.println("Enter a number listed next to an action to perform it.");
        System.out.println();
        System.out.println("Other commands: ");
        System.out.println();
        System.out.println("overview - Get an overview of the board.");
        System.out.println();
        System.out.println("players - Get an overview of the players.");
        System.out.println();
        System.out.println("pathto [number] - Get the shortest path from your current set to the given set.");
        System.out.println();
        System.out.println("help - Show this screen.");
        System.out.println();
    }

    private List<String> processInput(String input)
    {
        List<String> inputs = new ArrayList<>();

        Scanner scan = new Scanner(input);

        while(scan.hasNext()) {
            inputs.add(scan.next());
        }

        scan.close();

        return inputs;
    }

    private boolean isBuiltin(List<String> inputs)
    {
        // built in commands - overview, players, pathto, help.  maybe more?
        switch(inputs.get(0)) {
            case "overview":
            case "players":
            case "pathto":
            case "help":

            return true;

            default:

            return false;
        }

    }

    private void builtin(List<String> inputs) {

        switch(inputs.get(0)) {
            case "overview":
            if(inputs.size() == 1)
                viewBoardState();
            else
                System.out.println("Invalid input.");
            break;

            case "pathto":
            if(inputs.size() == 2) {
                try {
                    int value = Integer.parseInt(inputs.get(1));
        
                    if(value < 1 || value > 12) {
                        System.out.println("Invalid input");
                        return;
                    } else {
                        viewPath(value-1);
                    }
        
                } catch(NumberFormatException e) {
                    // if the input string cannot be parsed as an integer
                    System.out.println("Invalid input.");
                    return;
                }
            }
            else
                System.out.println("Invalid input.");
            break;
            
            case "players":
            if(inputs.size() == 1)
                viewPlayerData();
            else
                System.out.println("Invalid input.");
            break;

            case "help":
            if(inputs.size() == 1)
                viewHelp();
            else
                System.out.println("Invalid input.");
            break;
        }
    }

    private void takeAction(String input) {
        try {
            int value = Integer.parseInt(input);

            if(value < 1 || value > game.getActions().size()) {
                System.out.println("Invalid input");
                return;
            } else {
                game.takeAction(value-1);
            }

        } catch(NumberFormatException e) {
            // if the input string cannot be parsed as an integer
            System.out.println("Invalid input.");
            return;
        }
    }

    public void input(String input) 
    {
        List<String> inputs = processInput(input);

        if(inputs.size() == 0) return;

        // see if the input is one of the builtin commands
        if(isBuiltin(inputs)) {
            builtin(inputs);
        } else {
            takeAction(input);
        }
    }

    public boolean gameFinished()
    {
        return game.gameFinished();
    }
}
