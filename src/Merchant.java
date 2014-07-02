import java.util.ArrayList;


public class Merchant extends Creature {
	public ArrayList<Item> inventory = new ArrayList<Item>();
	public float gold;
	
	public Merchant(String name, int health, float gold, ArrayList<Item> inventory){
		equippedWeapon = null;
		damage = 0;
		this.name = name;
		this.health = health;
		this.gold = gold;
		this.inventory = inventory;
	}
	
	public Item getMercItemByName(String input) {
		Item result = null;
		for (Item i : inventory) {
			if (i.name.equalsIgnoreCase(input)) {
				result = i;
			}
		}
		return result;
	}
	

	public void describeMerchant(){
		System.out.println("The "+name+" is carrying:");
		for (Item s : inventory) {
			System.out.println(s.name);
		}
		System.out.println("The "+ name +" has " + gold +" gold");
	}
	
}
