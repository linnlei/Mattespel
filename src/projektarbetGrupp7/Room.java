package projektarbetGrupp7;

import java.io.Serializable;
import java.util.HashMap;


/**
 * The Room class is the base class for the rooms.
 * 
 * @author Jenny Forsberg
 * @version 2015-03-04
 */

public class Room implements Serializable{
	private static final long serialVersionUID = 1L;



	public Room()
	{
	}
	
	
	
	/**
	* getPicture - Gets the background picture of the room.
	*
	* @param  current 	The name of the the current room.	
	* @return			A string of a picture filename.                
	*/	
	public String getPicture(String current)
	{
		if(current.equals("center"))
		{
			return "stig.jpg";
		}
			
		else 
		{
			return "sno.jpg";
		}
		
	}
	
	/**
	* add - Saves a picture of a brick at at specific place in the garden.  
	* (Method overridden in Garden)
	*
	* @param  lopnr   The position of a specific place in the garden.
	* @param  icon    A string that represents a picture of a brick.
	* 
	*/
	public void addItem(int lopnr, String icon)
	{

	}
	
		
	/**
	* removeItem - removes a picture of a brick at at specific place in the garden.
	* (Method overridden in Garden)
	* 
	* @param  lopnr   the position of a specific place in the garden.
	*
	*/
	public void removeItem(int lopnr)
	{
		
	}
	
	/**
	* getGardenIcon - gets the picture of a brick saved at a specific place in the garden.
	* (Method overridden in Garden)
	*
	* @param  	lopnr   the position of a specific place in the garden.
	* @return			a string that represents the picture saved at this position.                
	*/
	public String getGardenIcon(int lopnr)
	{
		return null;
	}
	
	
	
	/**
	* getGardenItems - gets all the pictures and their places in garden .
	* (Method overridden in Garden)
	*
	* @return  	A hashmap where picture filenames are saved with the index of the buttons where they are saved. 
	*       
	*/	
	public HashMap<Integer, String> getGardenItems()
	{
		return null;
	}
	    

}


