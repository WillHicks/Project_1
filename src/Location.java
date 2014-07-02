import java.util.ArrayList;
public class Location{
public ArrayList<Item> items = new ArrayList<Item>();
public ArrayList<Mob> mobs = new ArrayList<Mob>();
public String environment;
public int temperature;
public Trap trap = null;
public Merchant merchant = null;

public static String[][] convertLocToString (Location[][] initialMap){
	String[][] map = new String[AdventureGame.NUM_OF_ROWS][AdventureGame.NUM_OF_COLUMNS];
	for(int i = 0; i < AdventureGame.NUM_OF_ROWS; i++){
		for(int j = 0; j < AdventureGame.NUM_OF_COLUMNS; j++){
			map[i][j] = initialMap[i][j].environment;
		}
	}
	return map;
}

public Mob getMobByName (String input){
	Mob result = null;
	for (Mob m : mobs){
		if(m.name.equalsIgnoreCase(input)){
			result = m;
		}
	}  
	return result;
}

public Item getLocalItemByName (String input){
	Item result = null;
	for (Item i : items){
		if(i.name.equalsIgnoreCase(input)){
			result = i;
		}
	}  
	return result;
}



}