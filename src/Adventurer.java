import java.util.ArrayList;
public class Adventurer {
public static String name;	
public static ArrayList<Item> inventory = new ArrayList<Item>();
public static int health ;
public static int damage;
public static Weapon equippedWeapon = null;

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

	public static boolean drop(ArrayList<Item> groundItems, Item item) {
		boolean result = false;
		if (item != equippedWeapon) {
			if (inventory.remove(item)) {
				result = true;
				groundItems.add(item);
			} else {
				System.out.println("Sorry, that item is not in your inventory.");
			}
		} else {
			System.out.println("You must first unequip this weapon, or equip a diffent one.");
		}
		return result;
	}

	public static boolean equip (Weapon x, int advDamage){
	boolean result = false;
	if(inventory.contains(x)){
		equippedWeapon= x;
		x.equipped = true;
		damage = advDamage + equippedWeapon.damage;
		System.out.println("You have equipped the " + x.name);
		result = true;
	}else{
		System.out.println("That item is not in your inventory");
	}
	return result;
}

public static boolean unequip (Weapon x){
	boolean result = false;
	if(equippedWeapon != null){
		equippedWeapon = null;
		damage = damage - x.damage; 
		result = true;
		System.out.println("You have unequipped the " + x.name);
	}else{
		System.out.println("That item is not equipped");
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

public static Weapon getWeaponByName (String input){
	Weapon result = null;
	for (Item i : inventory){
		if(i.name.equalsIgnoreCase(input)){
			if(i instanceof Weapon){				
			result = (Weapon) i;
			}
		}
	}  
	return result;
}

public static Item getItemByName (String input){
	Item result = null;
	for (Item i : inventory){
		if(i.name.equalsIgnoreCase(input)){
			result = i;
		}
	}  
	return result;
}

public static void summarize (){
	System.out.println("Here is what is in your inventory:");
	for (Item s : inventory){
		if (s != equippedWeapon){
		System.out.println(s.name);
		} else {
			System.out.println(s.name + "(equipped)");
		}
	}
}

}
