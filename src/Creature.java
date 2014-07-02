import java.util.ArrayList;


public class Creature extends Element{

public int health ;
public int damage;
public Weapon equippedWeapon = null;
public ArrayList<Item> inventory = new ArrayList<Item>();
public float gold;

public void dropLoot(Location tile){
	tile.gold = tile.gold + gold;
	tile.items.addAll(inventory);
	gold = 0;
	inventory.clear();
}

public Creature getCreatureByName(String input, ArrayList<Creature> candidates) {
	Creature result = null;
	for (Creature i : candidates) {
		if (i.name.equalsIgnoreCase(input)) {			
				result =  i;			
		}
	}
	return result;
}

}
