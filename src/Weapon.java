
public class Weapon extends Item {
	public int damage;
	public boolean equipped = false;
	public Effect effect;

	public Weapon() {
		effect = new Effect();
		effect.name = "damage";
		effect.longDescription = "Does damage to the target(s).";
	}

}
