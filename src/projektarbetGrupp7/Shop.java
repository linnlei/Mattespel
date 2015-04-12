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
	private HashMap<Item, Boolean> shopItems; //Boolean = f�r spelaren k�pa f�rem�let?
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
		
		for(Item item : inventory.keySet() ){	//L�gger in alla f�rem�l i aff�ren
			shopItems.put(item, false);			//Men s�tter att inget kan k�pas �n
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
		
		for(Item item : shopItems.keySet() ){				//G�r igenom alla f�rem�l
			if(player.getLevel() >= item.getItemLevel()){	//Kollar om spelarens level >= f�rem�lens level
				shopItems.put(item, true);					//S�tter att f�rem�l f�r k�pas om ens level �r tillr�ckligt h�gt
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
    		JOptionPane.showMessageDialog(null, "Du har inte r�d!", "", JOptionPane.OK_CANCEL_OPTION);
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
		
		/*if ( shopItems.get(boughtItem) ){*/ 	//dvs om f�rem�let man klickat p� �r true = f�r k�pas
			player.changeMoney((-1) * numberOfItems * boughtItem.getItemPrice()); 	//drar bort pengar fr�n spelarens pl�nbok
			player.myInventory.updateInventory(boughtItem, numberOfItems); 	//l�gger till r�tt antal f�rem�l i ryggs�cken
		/*}
		else{
			JOptionPane.showMessageDialog(null, "... men du har f�r l�gt level.", "", JOptionPane.INFORMATION_MESSAGE);
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
