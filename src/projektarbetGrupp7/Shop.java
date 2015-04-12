package projektarbetGrupp7;

import java.util.HashMap;

import javax.swing.JOptionPane;

/**
 * The Shop class sets up the logic of the shop. It creates the list of items in the shop and sets
 * the items as either available or unavailable at the player's current level. It also takes care of
 * the exchange of money and items when buying.
 * 
 * @author Linn Leiulfsrud
 * @version 2015-03-05
 */

public class Shop extends Room{
	private static final long serialVersionUID = 1L;
	private HashMap<Item, Boolean> shopItems; //Boolean = får spelaren köpa föremålet?
	private Player player;
	String shopPicture;
	
	/**
	 * Constructor of class Shop. Puts all available items of the game into the list of items
	 * to be stored in the shop. Saves a reference to the current player.
	 * Is the model part of the MVC design involving Shop, RoomPanels, and ShopController.
	 * 
	 * @param	inventory	The inventory of the current game.
	 * @param	player		The current player.
	 */
	public Shop(HashMap<Item, Integer> inventory, Player player){
		shopItems = new HashMap<Item, Boolean>();
		this.player = player;
		shopPicture = "wood_shelves3.png";
		
		for(Item item : inventory.keySet() ){	//Lägger in alla föremål i affären
			shopItems.put(item, false);			//Men sätter att inget kan köpas än
		}
		
		updateShop();
	}
	
	/**
	* getShopItems - Returns the items stored in the shop.
	*
	* @return			Returns the Hashmap of the shop's items and their availability.         
	*/
	public HashMap<Item, Boolean> getShopItems(){
		return shopItems;
	}
	
	/**
	* updateShop - Updates the availability of the items by checking the player level.           
	*/
	private void updateShop(){
		
		for(Item item : shopItems.keySet() ){				//Går igenom alla föremål
			if(player.getLevel() >= item.getItemLevel()){	//Kollar om spelarens level >= föremålens level
				shopItems.put(item, true);					//Sätter att föremål får köpas om ens level är tillräckligt högt
			}
		}
		
	}
	
	/**
	* calculatePrice - Calculates the total price of a purchase.
	*
	* @param	boughtItem		The type of item that the player wants to buy.
	* @param	numberOfItems	The amount of items the player wants to buy.              
	*/
	public void calculatePrice(Item boughtItem, int numberOfItems){
		int price = 0;
		price = numberOfItems * boughtItem.getItemPrice();
		JOptionPane.showMessageDialog(null, "Det blir " + price + " kronor.", "", JOptionPane.OK_CANCEL_OPTION);
		
		if(player.getMoney() >= price){
    		buy(boughtItem, numberOfItems);
    	}
    	else{
    		JOptionPane.showMessageDialog(null, "Du har inte råd!", "", JOptionPane.OK_CANCEL_OPTION);
    	}
	}
	
	/**
	* buy - Checks if the item is available, subtracts the total from the player's money, and adds the
	* 		right amount of items to the inventory.
	*
	* @param	boughtItem		The type of item that the player wants to buy.
	* @param	numberOfItems	The amount of items the player wants to buy.            
	*/
	public void buy(Item boughtItem, int numberOfItems){ 
		
		/*if ( shopItems.get(boughtItem) ){*/ 	//dvs om föremålet man klickat på är true = får köpas
			player.changeMoney((-1) * numberOfItems * boughtItem.getItemPrice()); 	//drar bort pengar från spelarens plånbok
			player.myInventory.updateInventory(boughtItem, numberOfItems); 	//lägger till rätt antal föremål i ryggsäcken
		/*}
		else{
			JOptionPane.showMessageDialog(null, "... men du har för lågt level.", "", JOptionPane.INFORMATION_MESSAGE);
		}*/
	}
	
	/**
	* getPicture - Gets the background picture of the room.
	*
	* @param  	current		The name of the room type.
	* @return				The filename of the background picture.		              
	*/
	public String getPicture(String current)
	{
		return shopPicture;
	}
	
}
