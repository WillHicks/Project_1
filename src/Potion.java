import java.util.ArrayList;
public class Potion extends Item{
	public static final ArrayList<Potion> POTION_LIST = new ArrayList<Potion>();
	
	static {
		POTION_LIST.add(new Potion("potion of healing", 10, 1,healing(), 4, 10));
		POTION_LIST.add(new Potion("singing potion", 10, 1,singing(), 4, 10));
		POTION_LIST.add(new Potion("whistling potion", 10, 1,whistling(), 4, 10));
		POTION_LIST.add(new Potion("Hemlock", 5,1, new Effect("Invisibility", "", 2, 3), 4,  53));
		POTION_LIST.add(new Potion("Sherlock", 5,1, new Effect("Invisibility", "", 2, 3), 32, 5));
		POTION_LIST.add(new Potion("Wolfsbane", 5, 1,new Effect("Invisibility", "", 2, 3), 99, 3));
		POTION_LIST.add(new Potion("Orange Juice", 5,1, new Effect("Invisibility", "", 2, 3), 7,  3));
	}
	
	public Effect effect;
	public int rarity = 0;
	public int shelfLife = 0;
	
	public Potion(String theName, float theValue, float theWeight, Effect theEffect, int theRarity, int theShelfLife) {
		name = theName;
		weight = theWeight;
		value = theValue;
		effect = theEffect;
		rarity = theRarity;
		shelfLife = theShelfLife;
	}
	
	public static Potion getPotionByName(String input) {
		System.out.println("POTION_LIST size is " + POTION_LIST.size());
		Potion result = null;
		for (Potion i : POTION_LIST) {
			if (i.name.equalsIgnoreCase(input)) {			
					result =  i;			
			}
		}
		return result;
	}
	
	public static Object getPotionValue(Potion p){
		Object result = null;
		if(p.name.equalsIgnoreCase("potion of healing")){
			result = 10;
		}
		return result;
	}
	
	public void drink(Element elem, Object newVal) {
		effect.go(elem, newVal);
	}
	
	public static Effect healing() {
		Effect healing = null;		
		healing = new Effect("Healing") {
			@Override public void go(Element elem, Object newVal) {
				AdventureGame.mainAdv.health = (int) (AdventureGame.mainAdv.health + (Integer) newVal);
			}
		};
		return healing;
	}
	public static Effect singing() {
		Effect eff = null;		
		eff = new Effect("Singing") {
			@Override public void go(Element elem, Object newVal) {
				System.out.println("You sing about your favorite int, " + (Integer) newVal);
			}
		};
		return eff;
	}
	public static Effect whistling() {
		Effect eff = null;		
		eff = new Effect("Whistling") {
			@Override public void go(Element elem, Object newVal) {
				System.out.println("You whistle about your favorite Float, " + (Float) newVal);
			}
		};
		return eff;
	}
}
