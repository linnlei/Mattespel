package projektarbetGrupp7;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Observable;


/**
 * The Inventory class holds the inventory, which consists of all the items
 * in the game and a number representing how many of that item the player has
 * in their backpack.
 * 
 * @author Fifi Johansson
 * @version 2015-03-05
 */
public class Inventory extends Observable implements Serializable {
	private static final long serialVersionUID = 1L;
	private HashMap<Item, Integer> items;
	
	/**
	* Constructor of class Inventory. Creates a hashmap and fills it
	* with the items of the game.	              
	*/
	public Inventory() {
		items = new HashMap<Item, Integer>();
		createInventory();
	}
	
	/**
	* getInventory - Gets the inventory of the current game
	*
	* @return	items		The hashmap of items		              
	*/
	public HashMap<Item, Integer> getInventory() {
		return items;
	}
	
	/**
	* updateInventory - Adds or removes items from the inventory by
	* changing the amount of the item the player owns.
	*
	* @param  	item		The item to change amount of
	* @param  	amount		How many to add/remove	              
	*/
	public void updateInventory(Item item, Integer amount) {
		int total = (items.get(item)) + amount;
		items.put(item, total);
		setChanged();
		notifyObservers(item);
	}
	
	/**
	* createInventory - Creates new items and puts them in the inventory hashmap            
	*/
	private void createInventory(){
		Item blueBrick = new Item(10, 1, "BrickBlue.png", "Blå tegelsten");
		Item redBrick = new Item(20, 1, "BrickRed.png", "Röd tegelsten");
		Item pyramid1 = new Item(40, 2, "pyramid_vertical.png", "Vertikal pyramidsten");
		Item pyramid2 = new Item(40, 2, "pyramid_horizontal.png", "Horisontell pyramidsten");
		Item greenBrick = new Item(10, 1, "BrickGreen.png", "Grön tegelsten");
		Item pinkBrick = new Item(20, 1, "BrickPink.png", "Rosa tegelsten");
		Item turkosBrick = new Item(20, 1, "BrickTurkos.png", "Turkos tegelsten");
		Item yellowBrick = new Item(10, 1, "BrickYellow.png", "Gul tegelsten");
		Item blackBrick = new Item(30, 1, "BrickBlack.png", "Svart tegelsten");
		Item whiteBrick = new Item(30, 1, "BrickWhite.png", "Vit tegelsten");
		
		items.put(pyramid2, 0);
		items.put(pyramid1, 0);
		items.put(redBrick, 0);
		items.put(blueBrick, 0);
		items.put(greenBrick, 0);
		items.put(pinkBrick, 0);
		items.put(turkosBrick, 0);
		items.put(yellowBrick, 0);
		items.put(blackBrick, 0);
		items.put(whiteBrick, 0);
	}
	
}

	
	

