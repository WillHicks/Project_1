import java.util.ArrayList;
public class Scroll extends Item{
public Spell spell = new Spell("",0,null);


public void teachSpell(Adventurer a){
	if (a != null) {
		System.out.println("a is not null");
		if (a.spellBook != null){
			System.out.println("spellBook is not null");
			a.spellBook.add(spell);
		}
	}
}

public static Scroll getScrollByName(String s, ArrayList<Item> inventory){
	Scroll result = null;
	for (Item i : inventory) {
		if (i.name.equalsIgnoreCase(s)) {
			if(i instanceof Scroll){
			result = (Scroll) i;
			} else{
				System.out.println("You cannot read that item.");
			}
		}
	}
	return result;
}

}
