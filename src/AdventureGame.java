import java.util.ArrayList;
import java.util.Scanner;

public class AdventureGame {

	public static int myXCoord;
	public static int myYCoord;
	public static int NUM_OF_ROWS = 10;
	public static int NUM_OF_COLUMNS = 10;
	public static Location[][] bigMap;
	public static Scanner myScanner = new Scanner(System.in);
	public static int INITIAL_DAMAGE = 10;
	public static int INITIAL_HEALTH = 100;


	public static void dump() {
		for (int i = 0; i < NUM_OF_ROWS; i++) {
			System.out.println("i =" + i);
			for (int j = 0; j < NUM_OF_COLUMNS; j++) {
				System.out.print(bigMap[i][j].items.size());
			}
		}
	}

	public static void initialize() {
		bigMap = new Location[NUM_OF_ROWS][NUM_OF_COLUMNS];
		myXCoord = 5;
		myYCoord = 5;
		Adventurer.health = INITIAL_HEALTH;
		Adventurer.damage = INITIAL_DAMAGE;
		for (int i = 0; i < NUM_OF_ROWS; i++) {
			for (int j = 0; j < NUM_OF_COLUMNS; j++) {
				bigMap[i][j] = new Location();
				bigMap[i][j].environment = "a plain";
				bigMap[i][j].items.add("pumpkin");
				bigMap[i][j].mobs.add(creeper());
			}
		}
		bigMap[5][5].environment = "a forest";
		bigMap[5][6].environment = "a mountain";
		bigMap[4][5].environment = "a castle";
		bigMap[5][4].environment = "a canyon";
		bigMap[6][5].environment = "a meadow";
		System.out.println("Here is how you play the game. To move, type north, south, east, or west.\r"
		+ "To pick up an item, type 'pick up ', followed by the name of the item.\r"
		+ "To drop an item, type 'drop ', followed by the name of the item.\r"
		+ "To attack, type 'attack ', followed by the number of the mob you wish to attack, minus one.\r"
		+ "For example, if you wanted to attack the first mob listed, you would type:\r"
		+ "attack 0");
		System.out.println("Hello. What is your name?");
		Adventurer.name = myScanner.nextLine();
	}

	public static void main(String[] arguments) {
		initialize();
		while (Adventurer.health > 0) {
			System.out.println("OK, " + Adventurer.name + ", you have "
					+ Adventurer.health + " health.");
			System.out.println("You are curently at "
					+ showMyLocation(myXCoord, myYCoord,
							Location.convertLocToString(bigMap)));
			System.out.println(showSurroundings(myXCoord, myYCoord,
					Location.convertLocToString(bigMap)));
			System.out.println("The objects in the room are:");
			ArrayList<String> stuff = showAllObjects(myXCoord, myYCoord, bigMap);
			for (String s : stuff) {
				System.out.println(s);
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
			if (!adventurerMoved(answer)){
				if (playerMadeRealMove(answer)){
					determineMobAction();
				}
			}
		}
		System.out.println("You are dead. Game over.");
	}

	public static void determineMobAction() {
		ArrayList<Mob> enemies = bigMap[myXCoord][myYCoord].mobs;
		int length = enemies.size();
		if (length > 0) {
			for (int i = 0; i < length; i++) {
				Mob enemy = enemies.get(i);
				Adventurer.health = Adventurer.health - enemy.damage;
				System.out.println("The " + enemy.name + " attacks you for "
						+ enemy.damage + " damage");
			}
		}
	}

	public static boolean playerMadeRealMove(String answer){
		boolean result = false;	
		if(	optPickUp(answer)||optDrop(answer)||optSummary(answer)||optAttack(answer, bigMap[myXCoord][myYCoord].mobs)){
			result = true;
		} else{
			System.out.println("Sorry, that is not a command I recognize");
		}
		return result;
	}
	
	public static void determineMovement(String answer) {
		myXCoord = myXCoord + moveWestOrEast(answer);
		myYCoord = myYCoord + moveNorthOrSouth(answer);
	}

	public static boolean adventurerMoved (String answer){
		boolean result = false;
		if (answer.equals("north") || answer.equals("south") || answer.equals("east") || answer.equals("west")){
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
				if (restOfAnswer.matches("[0-9]+")) {
					int indicatedMob = Integer.parseInt(restOfAnswer);
					if(presentEnemies.size() > indicatedMob){
					int x = myXCoord;
					int y = myYCoord;
					ArrayList<Mob> enemiesOnTargetTile = bigMap[x][y].mobs;
					Mob theRightTarget = enemiesOnTargetTile.get(indicatedMob);
					result = Adventurer.attack(bigMap[myXCoord][myYCoord].mobs,
							theRightTarget, Adventurer.damage);
					result = true;
					}
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
				String object = answer.substring(8);
				result = Adventurer.pickUp(bigMap[myXCoord][myYCoord].items,
						object);
			}
		}
		return result;
	}

	public static boolean optDrop(String answer) {
		boolean result = false;
		if (answer.length() >= 6) {
			String key = answer.substring(0, 5);
			if (key.equals("drop ")) {
				String object = answer.substring(5);
				result = Adventurer.drop(bigMap[myXCoord][myYCoord].items,
						object);
			}
		}
		return result;
	}

	public static boolean optSummary(String answer) {
		boolean result = false;
		if (answer.equals("show")) {
			Adventurer.summarize();
		}
		return result;
	}

	public static ArrayList<String> showAllObjects(int x, int y,
			Location[][] map) {

		ArrayList<String> result = map[x][y].items;
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
		if (y <= NUM_OF_COLUMNS) {
			result = myMap[x][y + 1];
		}
		return result;
	}

	public static String whatIsToTheEast(int x, int y, String[][] myMap) {
		String result = "";
		if (x <= NUM_OF_ROWS) {
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
		creeper.damage = 50;
		return creeper;
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
