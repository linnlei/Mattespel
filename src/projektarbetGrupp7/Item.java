package projektarbetGrupp7;

import java.io.Serializable;

/**
 * The Item class represents the item in the game, with information such as
 * the item's picture, price, level, and name.
 * 
 * @author Linn Leiulfsrud
 * @version 2015-03-05
 */
public class Item implements Serializable{
	private static final long serialVersionUID = 1L;
	private int price;
	private int level;
	private String name;
	private String pictureFile;
	
	/**
	* Constructor of the class Item. Sets the newly created item to have
	* the supplied information.
	*
	* @param  	price		The item's price
	* @param  	level		The item's level	  
	* @param  	pictureFile	The filename of the item's picture
	* @param  	name		The item's name            
	*/
	public Item(int price, int level, String pictureFile, String name){
		this.price = price;
		this.level = level;
		this.name = name;
		this.pictureFile = pictureFile;
	}
	
	/**
	* getItemPicture - Gets the item's picture
	*
	* @return				The filename of the item's picture	              
	*/
	public String getItemPicture(){
		return pictureFile;
	}
	
	/**
	* getItemPrice - Gets the item's price
	*
	* @return				The item's price	              
	*/
	public int getItemPrice(){
		return price;
	}
	
	/**
	* getItemLevel - Gets the item's level
	*
	* @return				The item's level	              
	*/
	public int getItemLevel(){
		return level;
	}
	
	/**
	* getItemName - Gets the item's name
	*
	* @return				The item's name	              
	*/
	public String getItemName(){
		return name;
	}
	
	
	
}
