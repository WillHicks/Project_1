import java.util.ArrayList;
public class Adventurer {
public static String name;	
public static ArrayList<Item> inventory = new ArrayList<Item>();
public static int health ;
public static int damage;

public static boolean pickUp (ArrayList<Item> groundItems, Item item){
	boolean result = false;
	if(groundItems.remove(item)){
		result = true;
		inventory.add(item);
	} else{
		System.out.println("Sorry, that item is not present.");
	}
	return result;
}

public static boolean drop (ArrayList<Item> groundItems, Item item){
	boolean result = false;
	if(inventory.remove(item)){
		result = true;
		groundItems.add(item);
	} else{
		System.out.println("Sorry, that item is not in your inventory.");
	}
	return result;
}

public static boolean attack (ArrayList<Mob> enemies, Mob enemy, int damage){
	boolean result = false;
	if(enemies.contains(enemy)){
		result = true;
		enemy.health = enemy.health - damage;
		System.out.println("The "+enemy.name+" takes "+ damage +" damage");
		if(enemy.health <= 0){
			enemies.remove(enemy);
			System.out.println("The "+enemy.name+" is dead.");
		}
	} else {
		System.out.println("This enemy is not present");
	}
	return result;
}

public static void summarize (){
	System.out.println("Here is what is in your inventory:");
	for (Item s : inventory){
		System.out.println(s.name);
	}
}

}
