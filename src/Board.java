//skeleton done
public class Board {
	private Set[] set;
	private Scene[] deck;
	//placeholders
	Area aPlaceholder = new Area(1,1,1,1);
	Set placeholder = new Set("", aPlaceholder, null, null);
	
	public Board(Set[] set, Scene[] deck)
	{
		this.set = set;
		this.deck = deck;
		// still need to work on getting players initialized and whatnot
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
		
	}
	
	public void dealScenes ()
	{
		
	}
}
