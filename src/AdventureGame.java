import java.util.ArrayList;
import java.util.Scanner;

public class AdventureGame {

	public static int myXCoord;
	public static int myYCoord;
	public static int NUM_OF_ROWS = 10;
	public static int NUM_OF_COLUMNS = 10;
	public static Location[][] bigMap;
	public static Scanner myScanner = new Scanner(System.in);
	public static int INITIAL_DAMAGE = 5;
	public static int INITIAL_HEALTH = 100;
	public static int turn = 0;
	public static Adventurer mainAdv = new Adventurer();

	public static void dump() {
		for (int i = 0; i < NUM_OF_ROWS; i++) {
			System.out.println("i =" + i);
			for (int j = 0; j < NUM_OF_COLUMNS; j++) {
				System.out.print(bigMap[i][j].items.size());
			}
		}
	}

	public static void initialize() {
		bigMap = new Location[NUM_OF_COLUMNS][NUM_OF_ROWS];
		myXCoord = 5;
		myYCoord = 5;
		mainAdv.health = INITIAL_HEALTH;
		mainAdv.damage = INITIAL_DAMAGE;
		for (int i = 0; i < NUM_OF_ROWS; i++) {
			for (int j = 0; j < NUM_OF_COLUMNS; j++) {
				bigMap[i][j] = new Location();
				bigMap[i][j].environment = "a plain";
				bigMap[i][j].items.add(pumpkin());
				bigMap[i][j].mobs.add(creeper());
			}
		}
		bigMap[5][5].environment = "a forest";
		bigMap[5][6].environment = "a mountain";
		bigMap[4][5].environment = "a castle";
		bigMap[5][4].environment = "a canyon";
		bigMap[6][5].environment = "a meadow";
		bigMap[5][5].items.add(ironSword());
		bigMap[5][6].items.add(ironSword());
		bigMap[5][6].trap = bearTrap();
		/*System.out
				.println("Here is how you play the game. To move, type go north, south, east, or west.\r"
						+ "To pick up an item, type 'pick up ', followed by the name of the item.\r"
						+ "To drop an item, type 'drop ', followed by the name of the item.\r"
						+ "To attack, type 'attack ', followed by the number of the mob you wish to attack, minus one.\r"
						+ "For example, if you wanted to attack the first mob listed, you would type:\r"
						+ "attack 0");*/
		System.out.println("Hello. What is your name?");
		mainAdv.name = myScanner.nextLine();
	}

	public static void main(String[] arguments) {
		initialize();
		System.out.println("You are curently at "
				+ showMyLocation(myXCoord, myYCoord,
						Location.convertLocToString(bigMap)));
		System.out.println(showSurroundings(myXCoord, myYCoord,
				Location.convertLocToString(bigMap)));
		while (mainAdv.health > 0) {
			System.out.println("You have "
					+ mainAdv.health + " health.");
			
			System.out.println("The objects in the room are:");
			ArrayList<Item> stuff = showAllObjects(myXCoord, myYCoord, bigMap);
			for (Item s : stuff) {
				System.out.println(s.name);
			}
			System.out.println("The mobs in the room are:");
			ArrayList<String> enemies = showAllMobs(allMobs(myXCoord, myYCoord,
					bigMap));
			for (String s : enemies) {
				System.out.println(s);
			}

			System.out.println("what do you want to do?");
			String answer = myScanner.nextLine();
			determineMovement(answer);
			if (!adventurerMoved(answer)) {
				if (playerMadeRealMove(answer)) {
					determineMobAction();
					turn++;
				}
			} else {
				activateTraps();
				System.out.println("You are curently at "
						+ showMyLocation(myXCoord, myYCoord,
								Location.convertLocToString(bigMap)));
				System.out.println(showSurroundings(myXCoord, myYCoord,
						Location.convertLocToString(bigMap)));
			}
		}
		System.out.println("You are dead. Game over.");
	}

	public static void activateTraps() {
		Trap t = bigMap[myXCoord][myYCoord].trap;
		if (t != null) {
			mainAdv.health = mainAdv.health - t.damage;
			System.out.println("A " + t.name + " activates!");
			System.out.println("You take " + t.damage + " damage.");
			bigMap[myXCoord][myYCoord].trap = null;
		}
	}

	public static void determineMobAction() {
		ArrayList<Mob> enemies = bigMap[myXCoord][myYCoord].mobs;
		int length = enemies.size();
		if (length > 0) {
			for (int i = 0; i < length; i++) {
				Mob enemy = enemies.get(i);
				mainAdv.health = mainAdv.health - enemy.damage;
				System.out.println("The " + enemy.name + " attacks you for "
						+ enemy.damage + " damage");
			}
		}
	}

	public static boolean playerMadeRealMove(String answer) {
		boolean result = false;
		if (optPickUp(answer) || optDrop(answer) || optSummary(answer)
				|| optAttack(answer, bigMap[myXCoord][myYCoord].mobs)
				|| optEquip(answer) || optUnequip(answer)) {
			result = true;
		} else if (optSummary(answer)) {

		} else {
			System.out.println("Sorry, that is not a command I recognize");
		}
		return result;
	}

	public static void determineMovement(String answer) {
		String key = answer.substring(3);
		if(key.equals("north") &&  myYCoord != NUM_OF_ROWS - 1){
			myYCoord = myYCoord + 1;
		}else if(key.equals("east") && myXCoord != NUM_OF_COLUMNS - 1){
			myXCoord = myXCoord + 1;
		}else if(key.equals("south") && myYCoord != 0){
			myYCoord = myYCoord - 1;
		}else if(key.equals("west") && myXCoord != 0){
			myXCoord = myXCoord - 1;
		}else{
			System.out.println("You cannot go that way.");
		}
	}

	public static boolean adventurerMoved(String answer) {
		boolean result = false;
		String key = answer.substring(0, 3);
		if (key.equals("go ")) {
			result = true;
		}
		return result;
	}

	public static boolean optAttack(String answer, ArrayList<Mob> presentEnemies) {
		boolean result = false;
		if (answer.length() >= 8) {
			String key = answer.substring(0, 7);
			if (key.equals("attack ")) {
				String restOfAnswer = answer.substring(7);
				Mob indicatedMob = bigMap[myXCoord][myYCoord]
						.getMobByName(restOfAnswer);
				if (mainAdv.attack(bigMap[myXCoord][myYCoord].mobs,
						indicatedMob, mainAdv.damage,
						bigMap[myXCoord][myYCoord])) {
					result = true;
				}

			}
		}
		return result;
	}

	public static boolean optEquip(String answer) {
		boolean result = false;
		if (answer.length() >= 7) {
			String key = answer.substring(0, 6);
			if (key.equals("equip ")) {
				String restOfAnswer = answer.substring(6);
				Weapon indicatedWeapon = mainAdv
						.getInvWeaponByName(restOfAnswer);
				if (indicatedWeapon != null) {
					if (mainAdv.equip(indicatedWeapon, INITIAL_DAMAGE)) {
						result = true;
					}
				} else {
					System.out.println("That item cannot be equipped");
				}
			}
		}
		return result;
	}

	public static boolean optUnequip(String answer) {
		boolean result = false;
		if (answer.length() >= 9) {
			String key = answer.substring(0, 8);
			if (key.equals("unequip ")) {
				String restOfAnswer = answer.substring(8);
				Weapon indicatedWeapon = mainAdv
						.getInvWeaponByName(restOfAnswer);
				if (indicatedWeapon != null) {
					if (mainAdv.unequip(indicatedWeapon)) {
						result = true;
					}
				} else {
					System.out.println("That item is not a weapon");
				}
			}
		}
		return result;
	}

	public static boolean optPickUp(String answer) {
		boolean result = false;
		if (answer.length() >= 9) {
			String key = answer.substring(0, 8);
			if (key.equals("pick up ")) {
				String restOfAnswer = answer.substring(8);
				if (restOfAnswer.equals("all")) {
					result = mainAdv.pickUpAll(bigMap[myXCoord][myYCoord].items);
				} else {
					Item object = bigMap[myXCoord][myYCoord]
							.getLocalItemByName(restOfAnswer);
					result = mainAdv.pickUp(bigMap[myXCoord][myYCoord].items,
							object);
				}
			}
		}
		return result;
	}

	public static boolean optDrop(String answer) {
		boolean result = false;
		if (answer.length() >= 6) {
			String key = answer.substring(0, 5);
			if (key.equals("drop ")) {
				String restOfAnswer = answer.substring(5);
				if (restOfAnswer.equals("all")) {
					result = mainAdv.dropAll(bigMap[myXCoord][myYCoord].items);
				} else {
				Item object = mainAdv.getInvItemByName(restOfAnswer);
				result = mainAdv.drop(bigMap[myXCoord][myYCoord].items,
						object);
				}
			}
		}
		return result;
	}

	public static boolean optSummary(String answer) {
		boolean result = false;
		if (answer.equals("show")) {
			mainAdv.summarize();
			result = true;
		}
		return result;
	}

	public static ArrayList<Item> showAllObjects(int x, int y, Location[][] map) {

		ArrayList<Item> result = map[x][y].items;
		return result;
	}

	public static ArrayList<String> showAllMobs(ArrayList<Mob> creatures) {
		ArrayList<String> out = new ArrayList<String>();
		for (Mob m : creatures) {
			out.add(m.name); // kind must be a String
		}
		return out;
	}

	public static ArrayList<Mob> allMobs(int x, int y, Location[][] map) {
		ArrayList<Mob> result = map[x][y].mobs;
		return result;
	}

	public static int moveWestOrEast(String direction) {
		int result = 0;
		if (direction.equals("east")) {
			result = 1;
		} else if (direction.equals("west")) {
			result = -1;
		}
		return result;
	}

	public static int moveNorthOrSouth(String direction) {
		int result = 0;
		if (direction.equals("north")) {
			result = 1;
		} else if (direction.equals("south")) {
			result = -1;
		}
		return result;
	}

	public static String showMyLocation(int x, int y, String[][] myMap) {
		return myMap[x][y];
	}

	public static String showSurroundings(int x, int y, String[][] myMap) {
		String nResult = whatIsToTheNorth(x, y, myMap);
		String eResult = whatIsToTheEast(x, y, myMap);
		String sResult = whatIsToTheSouth(x, y, myMap);
		String wResult = whatIsToTheWest(x, y, myMap);

		return "To your north is " + nResult + ", to your east is " + eResult
				+ ",\rto your south is " + sResult + ", to your west is "
				+ wResult;

	}

	public static String whatIsToTheNorth(int x, int y, String[][] myMap) {
		String result = "";
		if (y < NUM_OF_ROWS - 1) {
			result = myMap[x][y + 1];
		}
		return result;
	}

	public static String whatIsToTheEast(int x, int y, String[][] myMap) {
		String result = "";
		if (x < NUM_OF_COLUMNS - 1) {
			result = myMap[x + 1][y];
		}
		return result;
	}

	public static String whatIsToTheSouth(int x, int y, String[][] myMap) {
		String result = "";
		if (y > 0) {
			result = myMap[x][y - 1];
		}
		return result;
	}

	public static String whatIsToTheWest(int x, int y, String[][] myMap) {
		String result = "";
		if (x > 0) {
			result = myMap[x - 1][y];
		}
		return result;
	}

	public static Mob creeper() {
		Mob creeper = new Mob();
		creeper.name = "creeper";
		creeper.health = 1;
		creeper.damage = 10;
		creeper.loot.add(creeperHead());
		return creeper;
	}

	public static Item pumpkin() {
		Item pumpkin = new Item();
		pumpkin.name = "pumpkin";
		pumpkin.weight = 1;
		pumpkin.value = 1;
		return pumpkin;
	}

	public static Item creeperHead() {
		Item pumpkin = new Item();
		pumpkin.name = "creeper head";
		pumpkin.weight = 2;
		pumpkin.value = 0.1F;
		return pumpkin;
	}

	public static Weapon ironSword() {
		Weapon ironSword = new Weapon();
		ironSword.name = "iron sword";
		ironSword.weight = 3;
		ironSword.value = 10;
		ironSword.damage = 10;
		return ironSword;
	}

	public static Trap bearTrap() {
		Trap bearTrap = new Trap();
		bearTrap.name = "bear trap";
		bearTrap.damage = 1;
		return bearTrap;
	}

	public static String directionAsString(int myX, int myY, int x, int y) {

		String result = "";
		if (y < myY) {
			result = "north";
		} else if (y > myY) {
			result = "south";
		}
		if (x < myX) {
			result += "west";
		} else if (x > myX) {
			result += "east";
		}
		return result;
	}
}
