import java.util.List;
import java.util.ArrayList;

// GUI imports
import java.awt.*;
import javax.swing.*;
import javax.swing.ImageIcon;
import javax.swing.border.*;
import javax.swing.BorderFactory;
import java.awt.event.*;

import static javax.swing.ScrollPaneConstants.*;

public class DeadwoodGUI extends JFrame {
	private final int width = 1920;
	private final int height = 1030;

	private final int buttonWidth = 185;
	private final int buttonHeight = 125;

	private int page = 0;
	private GameSystem game;

	private boolean playersSmall = false;
	private int playerSize = 46;

	private String[] colors = {"b", "c", "g", "o", "p", "r", "v", "y", "w"};

	private Border currentPlayer;
	private Border otherPlayer;

	// JLabels - provide visual info, but can't be directly interacted with
	JLabel boardLabel;
	JLabel cards[];
	JLabel players[];
	JLabel playerInfo[];
	JLabel counters[][];
	JLabel menuLabel;

	// JButtons - clickable things.  like buttons
	JButton buttons[]; // idea: 12 buttons on the right, with 2 below to cycle through pages in case there are more than 12 actions available.

	// JLayeredPane, contains most of the GUI aspects
	JLayeredPane bPane;

	// JScrollPane, holds game history
	JScrollPane sPane;
	JTextArea history;
	String historyString;

	public DeadwoodGUI(GameSystem g) {
		// Set the window title.
		super("Deadwood");

		// set the GameSystem
		game = g;

		// exit when the X is pressed
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		bPane = getLayeredPane();

		// create borders to show which players are currently going and which aren't
		currentPlayer = BorderFactory.createMatteBorder(3,3,3,3, Color.BLUE);
		otherPlayer = BorderFactory.createMatteBorder(3,3,3,3, Color.BLACK);
		

		// create the board
		boardLabel = new JLabel();
		ImageIcon icon = new ImageIcon("img/board.jpg");
		boardLabel.setIcon(icon);
		boardLabel.setBounds(0,0,icon.getIconWidth(),icon.getIconHeight()); // 0,0,1200,900

		// board goes on the bottom layer, so everything else is above it
		bPane.add(boardLabel, Integer.valueOf(0));

		// 1600 by 900 window, because I think 16:9 looks nice :)
		setSize(icon.getIconWidth()+400,icon.getIconHeight());

		// create the buttons - 12 main buttons, and 2 on the bottom to cycle pages
		buttons = new JButton[14];
		for(int i = 0; i < 12; i++) {
			// initialize buttons
			buttons[i] = new JButton( Integer.toString(i) );
			buttons[i].setBackground(Color.white);

			// position them, two per row

			int x = icon.getIconWidth()+10 + ( i % 2 == 1 ? 195 : 0 );
			int y = (i / 2) * (buttonHeight+10) + 10;
			buttons[i].setBounds(x,y,buttonWidth,buttonHeight);

			// listen to mouse events on this button
			buttons[i].addMouseListener(new DeadwoodMouseListener());

			// place in top layer
			bPane.add(buttons[i], Integer.valueOf(2));
		}

		// 2 buttons to cycle pages
		buttons[12] = new JButton("<");
		buttons[12].setBackground(Color.white);
		buttons[13] = new JButton(">");
		buttons[13].setBackground(Color.white);
		
		int x = icon.getIconWidth() + 80;
		int y = 6 * (buttonHeight+10) + 10;

		buttons[12].setBounds(x,    y,45,25);
		buttons[13].setBounds(x+195,y,45,25);

		buttons[12].addMouseListener(new DeadwoodMouseListener());
		buttons[13].addMouseListener(new DeadwoodMouseListener());

		bPane.add(buttons[12], Integer.valueOf(2));
		bPane.add(buttons[13], Integer.valueOf(2));

		// create the cards - all are initially face down
		cards = new JLabel[10];
		for(int i = 0; i < cards.length; i++) {
			Area curr = game.getBoard().getSets()[i].getArea();
		
			cards[i] = new JLabel();
			ImageIcon cardIcon = new ImageIcon("img/CardBack-small.jpg");
			cards[i].setIcon(cardIcon);

			cards[i].setBounds( curr.getX(), curr.getY(), curr.getWidth(), curr.getHeight() );

			bPane.add(cards[i], Integer.valueOf(1));
		}

		// create shot counters
		counters = new JLabel[10][];
		ImageIcon shotImg = new ImageIcon("img/shot.png");
		
		for(int i = 0; i < 10; i++) {
			Area[] areas = game.getBoard().getSets()[i].getTakeAreas();
			counters[i] = new JLabel[areas.length];

			for(int j = 0; j < counters[i].length; j++) {
				counters[i][j] = new JLabel();
				counters[i][j].setIcon(shotImg);

				counters[i][j].setBounds( areas[j].getX(), areas[j].getY(), areas[j].getWidth(), areas[j].getHeight() );

				bPane.add(counters[i][j], Integer.valueOf(1));
			}
		}

		// create the players
		// TODO: let players choose their color
		if(game.getPlayerArr().length > 4) {
			playersSmall = true;
			playerSize = 23;
		}

		players = new JLabel[game.getPlayerArr().length];
		playerInfo = new JLabel[game.getPlayerArr().length];

		for(int i = 0; i < players.length; i++) {
			// create the player's die on the board
			players[i] = new JLabel();
			
			ImageIcon playerImg = new ImageIcon(getDiceFolder() + colors[i] + "1.png");
			players[i].setIcon(playerImg);

			Area trailer = game.getBoard().getTrailers().getArea();

			players[i].setBounds(trailer.getX() + (i%4)*playerSize, trailer.getY() + (i/4)*playerSize, playerSize, playerSize);

			bPane.add(players[i], Integer.valueOf(2));

			// create a display on the bottom showing player info
			playerInfo[i] = new JLabel();
			playerInfo[i].setVerticalAlignment(SwingConstants.TOP);
			
			playerInfo[i].setText( getPlayerInfo( game.getPlayerArr()[i] ) );
			playerInfo[i].setBounds(i * 200 + 5, 905, 190, 120);
			playerInfo[i].setBorder(otherPlayer);
			bPane.add(playerInfo[i], Integer.valueOf(2));
		}

		// add the scrollable game history pane
		historyString = "";
		history = new JTextArea();
		history.setWrapStyleWord(true);
		history.setLineWrap(true);

		
		sPane = new JScrollPane(history);
		sPane.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
		sPane.setBounds(1600, 10, 320, 1020);

		bPane.add(sPane);
		
		setSize(width, height + 50); // add 50 to height to include the window border
	}

	private String getPlayerInfo(Player p) {
		String result = "<html>";

		result += p.getName() + "<br/>";
		result += "Dollars: " + p.getDollars() + "<br/>";
		result += "Credits: " + p.getCredits() + "<br/>";
		result += "Practice tokens: " + p.getPracticeTokens() + "<br/>";
		result += "</html>";
		
		return result;
	} 

	private String getDiceFolder() {
		if(playersSmall) return "img/small-dice/";
		else return "img/big-dice/";
	}

	private void updateView() {
		updateButtons();
		updateScenes();
		updateShotCounters();
		updatePlayers();
	}

	private void updateButtons() {
		// set buttons 0 through 11 based on the current page number
		int i;
		for(i = page * 12; i < game.getActions().size() && i < (page+1) * 12; i++) {
			buttons[i % 12].setText("<html><p style=\"width:120px\">" + game.getActions().get(i).actionDescription() + "</p></html>");
			buttons[i % 12].setVisible(true);
		}
		
		while(i < (page+1) * 12) {
			buttons[i % 12].setText("");
			buttons[i % 12].setVisible(false);
			i++;
		}
		
		// then set the page buttons
		if(page == 0) {
			buttons[12].setVisible(false);
		} else {
			buttons[12].setVisible(true);
		}
		
		if(game.getActions().size() < (page+1) * 12) {
			buttons[13].setVisible(false);
		} else {
			buttons[13].setVisible(true);
		}
	}

	private void updateScenes() {
		for(int i = 0; i < cards.length; i++) {
			Set curr = game.getBoard().getSets()[i];

			if(curr.hasScene()) {
				ImageIcon img;
				if(curr.getScene().isFlipped()) {
					img = new ImageIcon("img/" + curr.getScene().getImage());
				} else {
					img = new ImageIcon("img/CardBack-small.jpg");
				}

				cards[i].setIcon(img);
				cards[i].setVisible(true);
			} else {
				cards[i].setVisible(false);
			}
		}
	}

	private void updateShotCounters() {
		Set[] sets = game.getBoard().getSets();
		for(int i = 0; i < 10; i++) {
			int shotsLeft = sets[i].getShotsLeft();

			int j = 0;
			while(j < shotsLeft) {
				counters[i][j].setVisible(true);
				j++;
			}

			while(j < counters[i].length) {
				counters[i][j].setVisible(false);
				j++;
			}
		}
	}

	private void updatePlayers() {
		for(int i = 0; i < players.length; i++) {
			// first, make sure the player is in the right position
			Player p = game.getPlayerArr()[i];
			Area a;
			
			if(p.hasRole()) {
				// cover the role's position on the board if one is taken
				a = p.getRole().getArea();

				// if the role is extra, then its Area is defined globally.
				// if it's not, then its Area is defined in relation to the card
				// it is on, so we need to add the card's position to the player's bounds
				int xOff = 0;
				int yOff = 0;

				if(!p.getRole().isExtra()) {
					xOff = p.getSet().getArea().getX();
					yOff = p.getSet().getArea().getY();
				}

				
				players[i].setBounds(a.getX() + xOff, a.getY() + yOff, a.getWidth(), a.getHeight());

				// then, set the player's image based on their rank - if they're on a role, they can 
				// always be shown at full size
				ImageIcon playerImg = new ImageIcon("img/big-dice/"+ colors[i] + p.getRank() + ".png");
				players[i].setIcon(playerImg);
			} else {
				// otherwise, just hang out below the scene card of the current set
				a = game.getPlayerArr()[i].getSet().getArea();
				players[i].setBounds(a.getX() + (i%4)*playerSize, a.getY() + (i/4)*playerSize + 120, playerSize, playerSize);

				// then, set the player's image based on their rank
				ImageIcon playerImg = new ImageIcon(getDiceFolder() + colors[i] + p.getRank() + ".png");
				players[i].setIcon(playerImg);
			}

			// then update the display at the bottom
			playerInfo[i].setText( getPlayerInfo( game.getPlayerArr()[i] ) );

			if(p == game.getCurrentPlayer()) {
				playerInfo[i].setBorder(currentPlayer);
			} else {
				playerInfo[i].setBorder(otherPlayer);
			}
		}
	}

	public void viewActions() {
		game.updateAvailableActions();
		page = 0;
		updateView();
	}

	public void viewScore() {
		int maxScore = 0;
		Player winningPlayer = game.getPlayerArr()[0];
		String resultString = "";
		
		for(Player p : game.getPlayerArr()) {
			int score = p.getScore();
		
		    if(score > maxScore) {
		    	winningPlayer = p;
		        maxScore = score;
		    }
		
		    resultString += (p.getName() + " has a score of " + score + "\n");
		}
		resultString += ("\nThe winner is " + winningPlayer.getName() + "!\n");
		JOptionPane.showMessageDialog(null, resultString);
		
		dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
	}

	public boolean gameFinished() {
		return false;
	}

	// listening to mouse events
	class DeadwoodMouseListener implements MouseListener {
		public void mouseClicked(MouseEvent e) {
			for(int i = 0; i < 12; i++) {
				if(e.getSource() == buttons[i]) {
					String action = game.takeAction(page*12 + i);

					historyString += action + "\n";

					history.setText(historyString);
					
					game.updateAvailableActions();
					updateView();
					return;
				}
			}

			// resetButtons() turns the page-turning buttons invisible if there are no more
			// pages in their direction, so we don't need to check if there is a page in said
			// direction before modifying the page number
			if(e.getSource() == buttons[12]) {
				page -= 1;

				updateView();
			} else if(e.getSource() == buttons[13]) {
				page += 1;

				updateView();
			}
		}
		public void mousePressed(MouseEvent e) {
		}
		public void mouseReleased(MouseEvent e) {
		}
		public void mouseEntered(MouseEvent e) {
		}
		public void mouseExited(MouseEvent e) {
		}
	}

	public static void main(String[] args) {
		//DeadwoodGUI board = new DeadwoodGUI();
		//board.setVisible(true);
	}
}
