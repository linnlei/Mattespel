package projektarbetGrupp7;


import javax.swing.JOptionPane;

/**
 * This class handles the communication between the graphic and logic part of the shop.
 * Checks if the player has enough money and is at the right level to buy the selected
 * item. Also checks so the player has entered a valid number and no invalid characters.
 * 
 * @author Linn Leiulfsrud
 * @version 2015-03-05
 */

public class ShopController {
    private GameEngine engine;

	/**
	 * Constructor of class ShopController. Stores a reference to the game engine so
	 * that the current shop can be reached.
	 * Is the controller part of the MVC design involving Shop, RoomPanels, and ShopController.
	 * 
	 * @param	engine	The game engine.
	 */
    ShopController(GameEngine engine) {
        this.engine  = engine;	
    }
    
	/**
	* buyControl - Checks the isInteger method to see if the entered value is valid, then
	* asks the player to confirm the purchase. Calls the money control.
	*
	* @param  	value			Amount to buy: a string entered by the player.
	* @param  	clickedItem		The item that the player selected.	              
	*/
    public void buyConfirm(String value, String clickedItem){
	
    	if( isInteger(value) ){		//Om man skrivit in siffror, fortsätter med kontrollen
    		int reply = JOptionPane.showConfirmDialog(null, "Vill du köpa " + value + " stycken?", "KÖP", JOptionPane.YES_NO_OPTION);
            
    		if ( reply == JOptionPane.YES_OPTION ) {
              buyControl(value, clickedItem);
            }
            else {
            }
    		
    	}
    	else if (value == null){ 
    		//JOptionPane.showMessageDialog(null, "Skriv in ett antal du vill köpa!", "KÖP", JOptionPane.OK_CANCEL_OPTION);
    	}
    	else{ 	//Om man skrivit in något annat än siffror
    		JOptionPane.showMessageDialog(null, "Du måste skriva in positiva siffror!", "KÖP", JOptionPane.OK_CANCEL_OPTION);
    	}
    }
    
	/**
	* buyControl - Finds the right item in the shop, checks so the player level is high enough, and
	* calls the buying methods in shop if the level is high enough.
	*
	* @param  	value			Amount to buy: a string entered by the player.
	* @param  	clickedItem		The item that the player selected.	              
	*/
    public void buyControl(String value, String clickedItem){
    	
    	
    	for(Item item : engine.shop.getShopItems().keySet() ){ //letar upp föremålet med rätt namn
    		if(clickedItem == item.getItemName()){
    			if(levelControl(item) == true){
    				engine.shop.calculatePrice(item, Integer.parseInt(value)); //anropar köpmetoderna i shop
    			}
    			else{JOptionPane.showMessageDialog(null, "Ditt level är för lågt!", "KÖP", JOptionPane.OK_CANCEL_OPTION);}
    				
    		}
    	}
    	
    }
  
	/**
	* isInteger - Checks if the entered string is a valid integer, and that it's 
	* a positive number.
	*
	* @param  	s		The value that the player entered.
	* @return			True if the string was a positive integer, false otherwise.	              
	*/
	public static boolean isInteger(String s) {	
	    try { 
	        Integer.parseInt(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    }
	    if( Integer.parseInt(s) >= 0 )
	    	return true;
	    else
	    	return false;
	}
	
	/**
	* levelControl - Checks if the player level is equal to or higher than
	* the item level of given item.
	*
	* @param  	item		The item to check the level of.
	* @return				True if the player level is higher than or equal to the item level.	            
	*/
	public boolean levelControl(Item item){
		if(engine.getPlayer().getLevel() >= item.getItemLevel()){
			return true;
		}
		else{
			return false;
		}
	}

}
