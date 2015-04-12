package projektarbetGrupp7;

import java.io.Serializable;
import java.util.Observable;

/**
 * The Player class contains the information regarding the current player, such as
 * their username, level, money, and a reference to the inventory.
 * (In gameplay, the inventory can be said to "belong" to the player, but class-wise 
 * the inventory object is not created in the player object.)
 * 
 * @author Linn Leiulfsrud
 * @version 2015-03-05
 */
public class Player extends Observable implements Serializable {
	private static final long serialVersionUID = 1L;
	private String userName;
	private int level;
	private int money;
	public Inventory myInventory;
	
	/**
	* Constructor of the class Player. Sets the initial value of the player's
	* level and money.	              
	*/
	public Player(){
		level = 1;
		money = 100;
	}
	
	/**
	* setInventory - Sets a reference to the inventory of the current game.
	*
	* @param  	in		The inventory of the current game          
	*/
	public void setInventory(Inventory in){
		myInventory = in;
	}
	
	/**
	* getUserName - Gets the player's name
	*
	* @return		The username of the player		              
	*/
	public String getUserName(){
		return userName;
	}
	
	/**
	* setUserName - sets the player's name
	*
	* @param  	name	A string to name the player              
	*/
	public void setUserName(String name){
		userName = name;
	}
	
	/**
	* getLevel - Gets the player's level
	*
	* @return		The level of the player		              
	*/
	public int getLevel(){
		return level;
	}
	
	/**
	* levelUp - Increases the player's level by 1
	*	              
	*/
	public void levelUp(){
		level = level + 1;
	}
	
	/**
	* getMoney - Gets the player's money
	*
	* @return		The money of the player		              
	*/
	public int getMoney(){
		return money;
	}
	
	/**
	* changeMoney - Increases or decreases the player's money
	*
	* @param  	value		The amount to change the money by             
	*/
	public void changeMoney(int value){ //value kan förstås vara både positivt och negativt
		money = money + value;
		setChanged();
		notifyObservers(money);
	}
}
