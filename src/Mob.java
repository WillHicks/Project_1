import java.util.ArrayList;
public class Mob extends Creature{
public ArrayList<Item> loot = new ArrayList<Item>();

public void dropLoot(Location tile){
	tile.items.addAll(loot);
}

}
