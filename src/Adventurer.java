import java.util.ArrayList;

public class Adventurer extends Creature {

	public boolean pickUp(ArrayList<Item> groundItems, Item item) {
		boolean result = false;
		if (groundItems.remove(item)) {
			result = true;
			inventory.add(item);
		} else {
			System.out.println("Sorry, that item is not present.");
		}
		return result;
	}

	public boolean pickUpAll(Location x) {
		boolean result = false;
		if (!x.items.isEmpty() || x.gold != 0) {
			inventory.addAll(x.items);
			x.items.clear();
			gold = gold + x.gold;
			x.gold = 0;
			result = true;
		} else {
			System.out.println("There are no items to pick up.");
		}
		return result;
	}

	public boolean drop(ArrayList<Item> groundItems, Item item) {
		boolean result = false;
		if (item != equippedWeapon) {
			if (inventory.remove(item)) {
				result = true;
				groundItems.add(item);
			} else {
				System.out
						.println("Sorry, that item is not in your inventory.");
			}
		} else {
			System.out
					.println("You must first unequip this weapon, or equip a diffent one.");
		}
		return result;
	}

	public boolean dropAll(ArrayList<Item> groundItems) {
		boolean result = false;
		if (!inventory.isEmpty()) {
			if (equippedWeapon == null) {
				groundItems.addAll(inventory);
				inventory.clear();
				result = true;
			} else {
				if (inventory.size() == 1) {
					System.out.println("There is nothing to drop.");
				} else {
					groundItems.addAll(inventory);
					groundItems.remove(equippedWeapon);
					inventory.clear();
					inventory.add(equippedWeapon);
					System.out
							.println("Your equipped weapon remains in your inventory.");
					result = true;
				}
			}
		} else {
			System.out.println("There are no items in your inventory.");
		}
		return result;
	}

	public boolean equip(Weapon x, int advDamage) {
		boolean result = false;
		if (inventory.contains(x)) {
			equippedWeapon = x;
			x.equipped = true;
			damage = advDamage + equippedWeapon.damage;
			System.out.println("You have equipped the " + x.name);
			result = true;
		} else {
			System.out.println("That item is not in your inventory");
		}
		return result;
	}

	public boolean unequip(Weapon x) {
		boolean result = false;
		if (equippedWeapon != null) {
			equippedWeapon = null;
			damage = damage - x.damage;
			result = true;
			System.out.println("You have unequipped the " + x.name);
		} else {
			System.out.println("That item is not equipped");
		}
		return result;
	}

	public boolean attack( ArrayList<Creature> enemies, Creature enemy, int damage,
			Location x) {
		boolean result = false;
		if (enemies.contains(enemy)) {
			result = true;
			enemy.health = enemy.health - damage;
			System.out.println("The " + enemy.name + " takes " + damage
					+ " damage");
			if(enemy instanceof Merchant){
				((Merchant) enemy).hostile = true;
			}
			if (enemy.health <= 0) {
				enemies.remove(enemy);
				if(enemy instanceof Mob){
					x.mobs.remove(enemy);
				} else if (enemy instanceof Merchant){
					x.merchant = null;
				}
				enemy.dropLoot(x);
				System.out.println("The " + enemy.name + " is dead.");
			}
		} else {
			System.out.println("This enemy is not present");
		}
		return result;
	}
	
	public boolean sell(Merchant m, Item i) {
		boolean result = false;
		if (inventory.contains(i)) {
			if (i.value < m.gold) {
				if(i == equippedWeapon){
					equippedWeapon = null;
				}
				gold = gold + i.value;
				m.gold = m.gold - i.value;
				inventory.remove(i);
				m.inventory.add(i);
				System.out.println("You sell your " + i.name + " for "
						+ i.value + " gold.");
				result = true;
			} else {
				System.out
						.println("The merchant does not have enough money to pay for that.");
			}
		} else {
			System.out.println("That item is not in your inventory.");
		}
		return result;
	}

	public boolean buy(Merchant m, Item i) {
		boolean result = false;
		if (m.inventory.contains(i) && i != m.equippedWeapon) {
			if (i.value < gold) {
				gold = gold - i.value;
				m.gold = m.gold + i.value;
				inventory.add(i);
				m.inventory.remove(i);
				System.out.println("You buy the merchant's " + i.name + " for "
						+ i.value + " gold.");
				result = true;
			} else {
				System.out
						.println("You do not have enough money to pay for that.");
			}
		} else {
			System.out.println("The merchant does not carry that item.");
		}
		return result;
	}
	
	public boolean drinkPotion(String s){
		boolean result = false;
		Potion p = Potion.getPotionByName(s);
		if(inventory.contains(p)){
		Object obj = Potion.getPotionValue(p);				
		p.drink(null, obj);
		inventory.remove(p);
		result = true;
		} else{
			System.out.println("That potion is not in your inventory");
		}
		return result;
	}
	
	public Weapon getInvWeaponByName(String input) {
		Weapon result = null;
		for (Item i : inventory) {
			if (i.name.equalsIgnoreCase(input)) {
				if (i instanceof Weapon) {
					result = (Weapon) i;
				}
			}
		}
		return result;
	}

	public Item getInvItemByName(String input) {
		Item result = null;
		for (Item i : inventory) {
			if (i.name.equalsIgnoreCase(input)) {
				result = i;
			}
		}
		return result;
	}

	public void showInventory() {
		System.out.println("Here is what is in your inventory:");
		System.out.println(gold + " gold");
		for (Item s : inventory) {
			if (s != equippedWeapon) {
				System.out.println(s.name);
			} else {
				System.out.println(s.name + "(equipped)");
			}
		}
	}
	

}
