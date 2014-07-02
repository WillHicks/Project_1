import java.util.ArrayList;


public class Merchant extends Creature {
	public float gold;
	public boolean hostile;
	
	public Merchant(String name, int health, float gold, ArrayList<Item> inventory, int damage, Weapon equippedWeapon){
		this.equippedWeapon = equippedWeapon;
		this.damage = damage + equippedWeapon.damage;
		this.name = name;
		this.health = health;
		this.gold = gold;
		this.inventory = inventory;
		hostile = false;
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
