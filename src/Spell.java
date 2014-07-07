import java.util.ArrayList;


public class Spell {
public static final ArrayList<Spell> SPELL_LIST = new ArrayList<Spell>();
	
	static {
		SPELL_LIST.add(new Spell("healing", 10,healing()));
	}
public String name;
public Effect effect;
public int cost;

public Spell(String name, int cost, Effect effect){
	this.name = name;
	this.cost = cost;
	this.effect = effect;
}

public static Spell getSpellByName(String input) {
	System.out.println("SPELL_LIST size is " + SPELL_LIST.size());
	Spell result = null;
	for (Spell i : SPELL_LIST) {
		if (i.name.equalsIgnoreCase(input)) {			
				result =  i;			
		}
	}
	return result;
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
