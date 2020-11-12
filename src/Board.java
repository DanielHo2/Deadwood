import java.util.Arrays;
import java.util.Collections;
import java.util.List;
//skeleton done
public class Board {
	private Set[] set;
	private Scene[] deck;
	private int deckIndex = 0;
	
	public Board(Set[] set, Scene[] deck)
	{
		this.set = set;

		// shuffle the deck
		List<Scene> deckList = Arrays.asList(deck);
		Collections.shuffle(deckList);
		this.deck = new Scene[deckList.size()];

		for(int i = 0; i < deck.length; i++) {
			this.deck[i] = deckList.get(i);
		}
	}

	public Set getCastingOffice ()
	{
		return set[10];
	}
	
	public Set getTrailers ()
	{
		return set[11];
	}
	
	public void refillShotCounters () 
	{
		for(int i = 0; i <= 9; i++) {
			set[i].refillShots();
		}
	}
	
	//shuffle and deal scenes to sets
	public void dealScenes ()
	{	
		for(int i = 0; i <= 9; i++) {
			// deal the next card in the deck
			set[i].dealScene(deck[deckIndex++]);
		}
	}

	public int numberOfScenes()
	{
		int result = 0;

		for(Set s : set) {
			if(s.hasScene()) result++;
		}

		return result;
	}

	public Set[] getSets()
	{
		return set;
	}
}
