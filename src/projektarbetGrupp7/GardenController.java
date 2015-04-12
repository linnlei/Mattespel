package projektarbetGrupp7;

import java.util.HashMap;
import java.util.Observable;

/**
 * This class handles the communication between Garden, Inventory and RoomPanels.
 * 
 * @author Jenny Forsberg
 * @version 2015-03-04
 */

public class GardenController extends Observable{
	String takenImage;
	GameEngine engine;
	HashMap<Item, Integer> inventory;	
		
	public GardenController(Inventory in, GameEngine engine)
	{
		inventory = in.getInventory(); 
		this.engine  = engine;
	}
	

	/**
	* setInventory - Updates GardenControllers inventory.
	*
	* @param  	in  	Inventory with the latest changes.
	* 		              
	*/
	public void setInventory(Inventory in){
		inventory = in.getInventory(); 
	}
	

	/**
	* getInventory - Gets the inventory saved in gardenController.
	*	
	* @return		A hashmap with the items and the current amount of them.                
	*/
	public HashMap< Item,Integer> getInventory()
	{
		return inventory;
	}
	

	/**
	* getIcon - Gets the image of a brick saved at a specific place in the garden.
	*
	* @param  	lopnr   The position of a specific place in the garden.
	* 	 
	* @return			A string that represents an image of a brick.                
	*/
	public String getIcon(int lopnr)
	{
		return engine.garden.getGardenIcon(lopnr);
	}
	
	
	/**
	* remove - Removes the image of a brick from a specific place in the garden
	* 		   and informs the present inventory of the change.
	*
	* @param  lopnr   The position of a specific place in the garden.
	*
	*/
	public void remove(int lopnr)
	{
		String icon = engine.garden.getGardenIcon(lopnr);
		engine.garden.removeItem(lopnr);
		
		for(Item i: inventory.keySet())
		{
			if(i.getItemPicture().equals(icon))
			{
				engine.userInventory.updateInventory(i, 1);
			}
		
		 }
	 }
	
	
	/**
	* take - Remembers what image of an item  the user has picked up.
	*
	* @param  imageOfItem   A string that represents the image of a brick.
	*
	*/
	public void take(String imageOfItem)
	{
		takenImage = imageOfItem;
	}
	
	
	/**
	* getTakenImage - Gets the taken image of a brick if it's allowed.
	*
	* @return 	A string that represents the image of a brick, or null.
	*
	*/
	public String getTakenImage()
	{
		for(Item i: inventory.keySet())
		{
			if(i.getItemPicture().equals(takenImage))
			{
				if(inventory.get(i)!=null)
				{
					if(inventory.get(i) > 0 )
					{
						return takenImage;
					}
				}
			}
		}
		
		return null;
	}
	
	
	/**
	* build - Saves the taken image of a brick at a specific place in the garden
	* 		  and informs the inventory of the change.
	*
	* @param  	lopnr   The position of a specific place in the garden.
	* 	                 
	*/
	public void build(int lopnr)
	{
		if(takenImage != null)
		{
			engine.garden.addItem(lopnr, takenImage);
			
			for(Item i: inventory.keySet())
			{
				if(i.getItemPicture().equals(takenImage))
				{
					engine.userInventory.updateInventory(i, -1);
				}
		    }
		}
	}
	
}
