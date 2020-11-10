import java.util.Arrays;
import java.util.Collections;
import java.util.List;
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
		for(int i = 0; i <= 9; i++) {
			set[i].refillShots();
		}
	}
	
	//shuffle and deal scenes to sets
	public void dealScenes ()
	{
		Scene[] sceneArrCopy = deck;
		
		List<Scene> sceneList = Arrays.asList(sceneArrCopy);
		Collections.shuffle(sceneList);
		sceneList.toArray(sceneArrCopy);
		
		for(int i = 0; i <= 9; i++) {
			set[i].dealScene(sceneArrCopy[i]);
		}
		
		
	}
}
