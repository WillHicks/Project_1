import java.util.ArrayList;
public class Potion extends Item{
	private static final ArrayList<Potion> POTION_LIST = new ArrayList<Potion>();
	
	{
		POTION_LIST.add(new Potion("potion of healing", 10, 1,healing(), 4, 10));
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
	
	public static Effect healing() {
		Effect healing = null;		
		healing = new Effect("Healing") {
			@Override public void go(Element elem, Object newVal) {
				AdventureGame.mainAdv.health = (int) (AdventureGame.mainAdv.health + (Float) newVal);
			}
		};
		return healing;
	}
}
