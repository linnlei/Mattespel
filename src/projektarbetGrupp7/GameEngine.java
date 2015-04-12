package projektarbetGrupp7;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.swing.*;

/**
 * The GameEngine class sets the game up at startup, creates the different rooms, and 
 * handles the change between them when the player moves to another room. 
 * It also handles saving and loading.
 * 
 * @author Linn Leiulfsrud
 * @version 2015-03-05
 */
public class GameEngine {
	private Player user;
	private UserInterface gui;
	private String current;
	public Inventory userInventory;
	
	//Rum:
	public Shop shop;
	public Room center, garden, minigame1;
	
	
	/**
	 * The State class stores the current state of the player, the garden lot, and
	 * the inventory. This state is used when saving and loading.
	 * 
	 * @author Linda Karlsson
	 * @version 2015-03-05
	 */
	public static class State implements Serializable{
		private static final long serialVersionUID = 1L;
		//Player, Garden, och Inventory måste vara Serializable för att kunna skrivas till fil
		Player player = new Player();
		Room gard = new Garden();
		Inventory inven = new Inventory();
		
		public Player getSavedPlayer(){
			return player;
		}
		public void setStatePlayer(Player user){
			player = user;
		}
		
		public Room getSavedGarden(){
			return gard;
		}
		public void setStateGarden(Room garden){
			gard = garden;
		}
		
		public Inventory getSavedInventory(){
			return inven;
		}
		public void setStateInventory(Inventory inventory){
			inven = inventory;
		}
	}
    
	public State gameState;
	
	
	/**
	 * Constructor of class GameEngine. Creates instances of State, Inventory, UserInterface,
	 * and Player. It then calls the necessary methods in UserInterface and Player to setup
	 * the GUI and the player inventory.
	 * 
	 */
	public GameEngine() {
		gameState = new State();
		userInventory = new Inventory();
		gui = new UserInterface(this);
		gui.gameStart();
		user = new Player();
		user.setInventory(userInventory);
	}

	/**
	* save - saves the state of the current game's player, garden lot, and inventory.
	*	              
	*/
	public void save(){
		//System.out.println("" + gameState.getSavedPlayer().getMoney());
		gameState.setStatePlayer(user);
		gameState.setStateGarden(garden);
		gameState.setStateInventory(userInventory);
		try{
			FileOutputStream saveFile = new FileOutputStream("" + gameState.player.getUserName() + ".sav");
			ObjectOutputStream save = new ObjectOutputStream( saveFile );
		
			save.writeObject( gameState.player );
			save.writeObject( gameState.gard );
			save.writeObject( gameState.inven );
			
			saveFile.close();
			save.close();
		}
    			
		catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog( null, "Hoppsan, något gick fel vid sparandet!","Spara",JOptionPane.OK_CANCEL_OPTION);
		}
	}
    
	/**
	* load - loads the state of a saved game's player, garden lot, and inventory.
	*	              
	*/	
    public void load(){
    	try{
    		FileInputStream saveFile = new FileInputStream("" + gameState.player.getUserName() + ".sav");
			//FileInputStream saveFile = new FileInputStream( "saves/" + gameState.player.getUserName() + ".sav" );
			ObjectInputStream load = new ObjectInputStream( saveFile );
	
			gameState.player = (Player) load.readObject();
			gameState.gard = (Garden) load.readObject();
			gameState.inven = (Inventory) load.readObject();
			
			saveFile.close();
			load.close();
			
			if( !(gameState.getSavedPlayer().equals(user)) ){
				user = gameState.getSavedPlayer();
				garden = gameState.getSavedGarden();
				userInventory = gameState.getSavedInventory();
				JOptionPane.showMessageDialog( null, "Din sparfil har laddats in!","Ladda",JOptionPane.OK_CANCEL_OPTION);
			}
			else{}
			
		}
    		
    	catch(Exception e){
    		JOptionPane.showMessageDialog( null, "Du är en ny spelare!","Ny spelare",JOptionPane.OK_CANCEL_OPTION);
    	}

    }
	

	
	/**
	* printWelcome - Shows the welcome pop-up where the player enters their name, then
	* uses the name to set up the game state.
	*            
	*/
	public void printWelcome() {
		String name;
		//JOptionPane.showMessageDialog(gui.myFrame(), "Välkommen till vårt spel!", "", JOptionPane.INFORMATION_MESSAGE);
		name = JOptionPane.showInputDialog(gui.myFrame(), "Vad är ditt namn? \n "
				+ "(För att ladda in sparfil, skriv in samma namn som när du spelade då)", "Ladda/ny spelare", JOptionPane.QUESTION_MESSAGE);
		stateSetup(name);
		
	}
	
	/**
	* stateSetup - Sets up the game state by setting the username to the entered
	* name and trying to load. If the load was succesful, the saved state is loaded
	* into the game. If not, the game is run with the new, blank state. After the
	* load has been attempted, the rooms are created with this information.
	*
	* @param  	name		The name the player enters at startup.	              
	*/
	public void stateSetup(String name){
		user.setUserName(name);
		gameState.setStatePlayer(user);
		load();		
		createRooms();
	}
	
	/**
	* createRooms - Creates all the rooms of the game. Loads the garden lot with a saved garden lot
	* if a savefile was loaded.
	*            
	*/
	private void createRooms() {
      
        center = new Room();
        garden = gameState.getSavedGarden();
        minigame1 = new Room();
        shop = new Shop(userInventory.getInventory(), user);
        
        current = "center";		//startar spelet i centrum
    }
	
	/**
	* changeRoom - When moving to a new room, this method calls for the
	* methods in the GUI class to set up the correct center panel for the room.
	*
	* @param  	current		The name of the room type.              
	*/
	public void changeRoom(String current)
	{
		Room room; 
		setCurrent(current);
		if(current.equals("center")) room = center; 
		else if(current.equals("shop")) room = shop;
		else if(current.equals("garden")) room = garden;
		else room = minigame1;
		gui.setJPanelWithBackground(room.getPicture(current));
	}
	
	/**
	* setCurrent - Sets the current room to another room, thus "moving"
	* to another room of the game.
	*
	* @param  	room		The name of the room type.	              
	*/
	public void setCurrent(String room) {
		current = room;
	}
	
	/**
	* getCurrent - Gets the room type that the player is currently in.
	*
	* @return				The name of the room type.              
	*/
	public String getCurrent() {
		return current;
	}
	
	/**
	* getPlayer - Gets the player of the current game.
	*
	* @return				The player of the current game.             
	*/
	public Player getPlayer(){
		return user;
	}
	
	
}
