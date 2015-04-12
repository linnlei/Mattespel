package projektarbetGrupp7;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;
import java.util.Map.Entry;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import java.awt.Toolkit;



import javax.swing.JTextField;

/**
 * This class creates different central panels for the different rooms.
 * 
 * @author Fifi Johansson, Linn Leiulfsrud, Jenny Forsberg
 * @version 2015-03-04
 */

public class RoomPanels implements Observer{
	private GameEngine engine;
	private JPanel panelClickable;
	private ArrayList<JButton> itemButtons = new ArrayList<JButton>();
	private HashMap<Item, JButton> panelButtons2 = new  HashMap<Item, JButton>();
	private HashMap<String, Integer> rightPanel = new  HashMap<String, Integer>();
	
	private ShopController shopControl;
	private GardenController gardenController;
	private JButton itemsLeft2;
	
	
	/**
	 * Constructor of class RoomPanels. Saves a reference to the game engine of the
	 * current game. Creates the controllers for the garden and the shop, and adds
	 * the observer to the gardenController.
	 * 
	 * @param	e	The game engine of the current game.
	 */
	public RoomPanels(GameEngine e)
	{
		engine = e;
		shopControl = new ShopController(engine);
		gardenController = new GardenController(e.userInventory, engine);
		gardenController.addObserver(this);
		
	}
	/**
	* getPanel - Creates and returns the correct panel for the current room.
	*
	* @param  	current 	String representation of the the current room.
	* 	 
	* @return			    A JPanel for the current room     .           
	*/
	public JPanel getPanel(String current)
	{
		if(current.equals("center")) panelClickable = createCenterPanel();
		else if(current.equals("shop")) panelClickable = createShopPanel();
		else if(current.equals("garden")) panelClickable = createGardenPanel();
			
		return panelClickable;
	}
	
	/**
	* createCenterPanel - Creates the panel for the central room.
	* 	 
	* @return			A JPanel for the center.                
	*/
	private JPanel createCenterPanel()
	{
		Sound.stopSound();
		Sound.soundInLoop("Forest.wav");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		screenSize.setSize( screenSize.getWidth() , (screenSize.getHeight() - 30) ); //-30 kompenserar för windows-menybaren
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        
        Font font = new Font("Viner Hand ITC", Font.BOLD, 25);
        
        
	   	JPanel panel = new JPanel();    
	    panel.setOpaque(false);
	    panel.setLayout(null);
	    
	    //Tar in skyltbilden, skalar om den, sparar som newIcon
    	URL imageURL = this.getClass().getClassLoader().getResource("skylt.png");
    	ImageIcon icon = new ImageIcon(imageURL);
	    Image img = icon.getImage();
	    Image newimg = img.getScaledInstance(200, 100,  java.awt.Image.SCALE_SMOOTH);
	    ImageIcon newIcon = new ImageIcon(newimg);
	    
	    JButton clickButton = new JButton ("Affär", newIcon);
	    
	    clickButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				engine.changeRoom("shop");				
				
			}
		});
	    clickButton.setHorizontalTextPosition(JButton.CENTER);
	    clickButton.setVerticalTextPosition(JButton.CENTER);
	    clickButton.setBounds((int)(width * 0.15),(int)(height * 0.2),(int)(width * 0.15),(int)(height * 0.15));//x,y,width,height
	    clickButton.setContentAreaFilled(false);
	    clickButton.setFont(font);
	    clickButton.setForeground(Color.white); 		//färg på skyltens text
	    clickButton.setBorderPainted(false); //med eller utan kant
	    panel.add(clickButton);
	    
	    JButton clickButton2 = new JButton ("Trädgård", newIcon);
	    
	    clickButton2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				engine.changeRoom("garden");			
				
			}
		});
	    clickButton2.setHorizontalTextPosition(JButton.CENTER);
	    clickButton2.setVerticalTextPosition(JButton.CENTER);
	    clickButton2.setBounds((int)(width * 0.60),(int)(height * 0.70),(int)(width * 0.15),(int)(height * 0.15));//x,y,width,height
	    clickButton2.setContentAreaFilled(false);
	    clickButton2.setFont(font);
	    clickButton2.setForeground(Color.white); 		//färg på skyltens text
	    clickButton2.setBorderPainted(false); //med eller utan kant
	    panel.add(clickButton2);
	    
	    JButton clickButton3 = new JButton ("Minispel", newIcon);
	    
	    clickButton3.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new MiniGame(engine.getPlayer());
			}
		});
	    clickButton3.setBounds((int)(width * 0.80),(int)(height * 0.20),(int)(width * 0.15),(int)(height * 0.15));//x,y,width,height
	    clickButton3.setContentAreaFilled(false);
	    clickButton3.setFont(font);
	    clickButton3.setForeground(Color.white); 		//färg på skyltens text
	    clickButton3.setHorizontalTextPosition(JButton.CENTER);
	    clickButton3.setVerticalTextPosition(JButton.CENTER);
	    clickButton3.setBorderPainted(false); //med eller utan kant
	    panel.add(clickButton3);
	    
	    return panel;    
	}
	

	/**
	* createShopPanel - Creates the panel for the shop.
	* 	 
	* @return			A JPanel for the shop.                
	*/
	private JPanel createShopPanel()
	{
		Sound.stopSound();
		Sound.soundInLoop("Colorful_Vacation.wav");
		HashMap<Item, Boolean> shopItems = engine.shop.getShopItems();
		
		//**************************Skapa paneler**************************
		JPanel panel = new JPanel();    
		JPanel gridPanel = new JPanel(); 
		JPanel downPanel = new JPanel(); 
		
	    panel.setOpaque(false);
	    gridPanel.setOpaque(false);
	    downPanel.setOpaque(false);
	    
	    panel.setLayout(new BorderLayout());
	    downPanel.setLayout(new GridLayout(1,10));
	    gridPanel.setLayout(new GridLayout(5,5,40,20));
	    
	    downPanel.setPreferredSize(new Dimension(100, 100)); 
	    
	    
	    //**************************Skapa knappar**************************
	    	    
	    for(Item item : shopItems.keySet() ){ 
	    	
	    	ImageIcon icon;
	    	
	    	if (shopControl.levelControl(item)){
	    		URL imageURL = this.getClass().getClassLoader().getResource(item.getItemPicture());
		    	icon = new ImageIcon(imageURL);
	    	}
	    	else{
	    		URL imageURL = this.getClass().getClassLoader().getResource("cross_grey.png");
		    	icon = new ImageIcon(imageURL);
	    	}
	    	
	    	Font font = new Font("Verdana", Font.BOLD, 12);
	    	JButton tempButton = new JButton (item.getItemName(), icon);
	    	tempButton.setFont(font);
	    	tempButton.setForeground(Color.white);
	    	
	    	JTextField itemText;
	    	
	    	if ( shopControl.levelControl(item)){
	    		itemText = new JTextField("Pris: " + item.getItemPrice() + " kr\n Level: " + item.getItemLevel());
	    	}
	    	else{
	    		itemText = new JTextField("Låses upp på level " + item.getItemLevel());
	    	}
	    	itemText.setHorizontalAlignment(JTextField.CENTER);
	    	itemText.setEditable(false);
	    	itemText.setOpaque(false);	
	    	itemText.setFont(font);
	    	itemText.setForeground(Color.white);
	    	itemText.setBorder(null);
	    	
	    	tempButton.setContentAreaFilled(false);
	    	tempButton.setBorderPainted(false);
	    	
	    	JPanel itemGrid = new JPanel();
	    	itemGrid.setOpaque(false);
	    	itemGrid.setLayout(new GridLayout(2,1));
	    	
	    	itemGrid.add(tempButton);
	    	itemGrid.add(itemText);
	    	
	    	tempButton.addActionListener(new ActionListener()  {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					String clickedItem = new String();			//Sträng för att spara namnet på det man klickat på
					for(JButton button : itemButtons){			//Letar igenom vilken knapp man klickat på
						if(arg0.getSource().equals(button)){	//När knappen hittats...
							clickedItem = button.getText();		//...så sparar man föremålets namn för den knappen
						}
					}
					String inputValue = JOptionPane.showInputDialog("Hur många vill du köpa?");
					shopControl.buyConfirm(inputValue, clickedItem);	//Startar controllern med föremålet samt antal
				}
			});
	    	
	    	gridPanel.add(itemGrid);
	    	itemButtons.add(tempButton); //sparar knappen i en arraylist för att man ska kunna leta igenom knapparna
		}
	    
	    
	  //**************************Skapa tomma knappar för resten av utrymmena**************************
	    int buffer = 25 - shopItems.size();
	    int j = 0;
	    for(j = 1; j <= buffer; j++ ){ //lägger till tomma knappar för att fylla skärmen
	    	JButton tempButton = new JButton ();
	    	tempButton.setContentAreaFilled(false);
	    	tempButton.setBorderPainted(false);
	    	gridPanel.add(tempButton);
	    	itemButtons.add(tempButton); //för att kunna iterera igenom knapparna
		}
	    
	  //**************************Övriga menyknappar etc***********************************************
	    Font menuFont = new Font("Verdana", Font.BOLD, 16);
	   
	    JButton centrumButton = new JButton ("Tillbaka till centrum");
	    centrumButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				engine.changeRoom("center");	
				
			}
		});
	    centrumButton.setContentAreaFilled(false);
	    centrumButton.setFont(menuFont);
	    centrumButton.setForeground(Color.white);
	    downPanel.add(centrumButton);
	    
	    JButton gardenButton = new JButton ("Till trädgården");
	    gardenButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				engine.changeRoom("garden");			
				
			}
		});
	    
	    gardenButton.setFont(menuFont);
	    gardenButton.setForeground(Color.white);
	    gardenButton.setContentAreaFilled(false);
	    downPanel.add(gardenButton);
	    
	    JButton inventoryButton = new JButton ("Ryggsäck");
	    inventoryButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				createInventoryPanel();
				
			}
		});
	    
	    inventoryButton.setContentAreaFilled(false);
	    inventoryButton.setFont(menuFont);
	    inventoryButton.setForeground(Color.white);
	    
	    downPanel.add(inventoryButton);
	    
	    panel.add(gridPanel, BorderLayout.CENTER);
	    panel.add(downPanel, BorderLayout.SOUTH);

	    return panel;
	}
	
	/**
	* createGardenPanel - creates the panel for the garden.
	* 	 
	* @return			  A JPanel for the garden.                
	*/
	private JPanel createGardenPanel()
	{
		Sound.stopSound();
		Sound.soundInLoop("beachsummer_image.wav");
        
		engine.userInventory.addObserver(this);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setOpaque(false);
	    
	    JPanel showInventory;
	    
	    int rad = 0;
	    int column =0;
	    int lopnr=0;
	      
	    HashMap<Item, Integer> inventory = engine.userInventory.getInventory();
	    gardenController.setInventory(engine.userInventory);
	    
        Set<Entry<Item, Integer>> pairs = inventory.entrySet();
        
        for(Item item : inventory.keySet()){
			engine.userInventory.updateInventory(item, 0);
        }
        
	    
	    //*****************Skapar det osynlliga rutnätet med knappar*************************************'
	    
	    for( int j=0;j<43;j++){    	//bredd
	    		    
		    for( int i=0;i<23;i++)	//höjd
		    {
		    	lopnr++;// Varje knapp får ett eget nummer för att kunna lokalisera dess position		    		    
			    final int nr = lopnr;
			    
			    String s = "buildable"; // Kom ihåg om det finns en tegelsten på positionen eller inte
			    if(gardenController.getIcon(nr)!= null)  s= "unbuildable";
			    final String startState = s;
			    
			    final ImageIcon icon3;
			    if (gardenController.getIcon(nr)!= null)
			    {
			    	URL imageURL = this.getClass().getClassLoader().getResource(gardenController.getIcon(nr));
					icon3 = new ImageIcon(imageURL);
			    	//icon3 = new ImageIcon("pictures/" + gardenController.getIcon(nr));
			    	
			    }
			    else
			    	icon3 =null;
			   
		    				    
			    final JButton clickButton = new JButton(icon3);
			    
			    clickButton.setBounds(column,rad,30,30);
			    clickButton.setContentAreaFilled(false);		//Osynlighet
			    clickButton.setBorderPainted(false);			//Osynlighet
			    
			    
			    clickButton.addActionListener(new ActionListener() {
			    	String state = startState; // Håller koll på om det går att bygga eller om det redan finns en tegelsten där
			    	String takenImageString;
			    	ImageIcon takenImage; 
			    	@Override
					public void actionPerformed(ActionEvent arg0) {
						if(state.equals("buildable"))
						{
							takenImageString = gardenController.getTakenImage();	// Hämta stringen för den bild på tegelsten som användaren valt
							URL imageURL = this.getClass().getClassLoader().getResource(takenImageString);
							takenImage = new ImageIcon(imageURL);
							//takenImage = new ImageIcon("pictures/" +takenImageString);
							if(takenImage!=null && (gardenController.getTakenImage())!=null){
								state="unbuildable";
								clickButton.setIcon(takenImage);
								gardenController.build(nr);
								takenImage = null;
							}						
						}
						else if(state.equals("unbuildable")){
							state="buildable";
							clickButton.setIcon(null);
							clickButton.setContentAreaFilled(false);
							gardenController.remove(nr);
						}
					}
			    });	
					
			    panel.add(clickButton);
			    rad = rad + 30;    		    
		    }
		   
		    column = column + 30;
		    rad =0;
						   	    		    
		}
	    
        
	  //*************************************Panelen vid sidan av rutnätet*********************************************** 	
	    
        
        
	    showInventory = new JPanel();
	    showInventory.setLayout(null);
	    showInventory.setBounds(1279,0, 81, 1000);
	    
	    int buttonNr=0;
	    
	    // Nollställer för ny loop
	    rad = 30;
	    column = 30;
   
        //Fixar fram det som ska visas på sidopanelen
        for(Entry<Item, Integer> entry: pairs){
	        buttonNr++;
	        	
	        //final int bNr = buttonNr;
	    	// Visa bilder på tillgänliga Item
	        final Item item= entry.getKey();	
	        
	        URL imageOfItemString = this.getClass().getClassLoader().getResource(item.getItemPicture());
	    	final ImageIcon imageOfItem= new ImageIcon(imageOfItemString);
	    	rightPanel.put(item.getItemPicture(), buttonNr);
	    	
	    	final JButton showItem;
	    	showItem = new JButton(imageOfItem);
		    showItem.setBounds(column, rad,30,30);
		    
		    //Vad bildknappen ska göra
		    showItem.addActionListener(new ActionListener() {
		    	
		    	@Override
				public void actionPerformed(ActionEvent arg0) {
		    		gardenController.take(item.getItemPicture());
					//presentItem = bNr;
				}
		    });	
			    
		    //Visa antal  tillgänliga Items
		    itemsLeft2 = new JButton();
		    itemsLeft2.setText("" + entry.getValue());
		    itemsLeft2.setBounds(column-15, rad+30,60,30);
		    itemsLeft2.setContentAreaFilled(false);		//Osynlighet
		    itemsLeft2.setBorderPainted(false);			//Osynlighet
		    showInventory.add(itemsLeft2);
		    			    
		    //Spara referens till just denna rutan
		    panelButtons2.put(item, itemsLeft2);
		     
		    //Lägg till bild och antal på panelen vid sidan av rutnätet
		    showInventory.add(showItem);
		    
		    // Byt till ny position
		    rad= rad +60;
	    }
        

        
        panel.add(showInventory);
        return panel;  
	}
	
	/**
	* createInventoryPanel - creates the window for the inventory.
	* 	          
	*/
	public void createInventoryPanel()
	{
		JFrame inventory = new JFrame("Ryggsäck");
		inventory.setPreferredSize(new Dimension(600,400));	
		HashMap<Item, Integer> items = engine.getPlayer().myInventory.getInventory();
		
		JPanelWithBackground invBG = new JPanelWithBackground("inventoryBG.jpg");
		inventory.add(invBG);
		invBG.setLayout(new GridLayout(3,1));
		
		JLabel title = new JLabel("Ryggsäck");
	    title.setFont(new Font("Viner Hand ITC", Font.BOLD, 50));
	    title.setHorizontalAlignment(SwingConstants.CENTER);
	    invBG.add(title);
	    
		JPanel panel = new JPanel();
	    panel.setOpaque(false);
	    panel.setLayout(new GridLayout(4,4));
	    invBG.add(panel);
		
		for (Entry<Item, Integer> entry : items.entrySet() ) {
			
			if(entry.getKey().getItemLevel() <= engine.getPlayer().getLevel()){
				URL imageURL = this.getClass().getClassLoader().getResource(entry.getKey().getItemPicture());
				ImageIcon iconapprove = new ImageIcon(imageURL);
				
				JLabel imglabelapprove1 = new JLabel(entry.getValue().toString());
			    imglabelapprove1.setIcon(iconapprove);
			    imglabelapprove1.setHorizontalAlignment(JLabel.CENTER);
			    panel.add(imglabelapprove1);
			}
		}
		
		inventory.pack();
		inventory.setVisible(true);
		inventory.setLocationRelativeTo(null);
	}
	
	/**
	* update - updates the gardenpanel when the inventory changes, via an observer relationship
	* between RoomPanels and Inventory.
	*
	* @param  	obj   The inventory that changes.
	* @param	arg	  The item which is moved between inventory and garden.                 
	*/
	public void update(Observable obj, Object arg)
	{
		Item temp; 
		temp = new Item(200, 2, "fish.png", "tomt item");	//empty item that is overwritten
		
		if ( obj instanceof Inventory && arg instanceof Item ){
			temp = (Item)arg;
			if(panelButtons2.get(arg) != null)
				panelButtons2.get(arg).setText("" + engine.userInventory.getInventory().get(temp));
		}
			
	}
}
	
