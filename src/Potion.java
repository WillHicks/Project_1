import java.util.ArrayList;
public class Potion extends Item{
	public static final ArrayList<Potion> POTION_LIST = new ArrayList<Potion>();
	
	static {
		POTION_LIST.add(new Potion("potion of healing", 10, 1,healing(), 4, 10));
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
	
	public void drink(Element elem,int intensity, Object newVal) {
		effect.go(elem,intensity,  newVal);
	}
	
	public static Effect healing() {
		Effect healing = null;			
		healing = new Effect("Healing", 0, 10) {
			
			@Override public void go(Element elem, int intensity, Object newVal) {
				AdventureGame.mainAdv.health = (int) (AdventureGame.mainAdv.health + (Integer) newVal + intensity);
			}
		};
		return healing;
	}
	
}
