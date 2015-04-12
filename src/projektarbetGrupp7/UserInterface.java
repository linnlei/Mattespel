package projektarbetGrupp7;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;


/**
 * The UserInterface class creates the base GUI: everything but the central panel.
 * 
 * @author Fifi Johansson, Jenny Forsberg
 * @version 2015-03-04
 */

public class UserInterface implements Observer{
	private GameEngine engine;
	private JFrame myFrame;
	private JButton exitButton;
	public JLabel moneyButton;
	private JPanelWithBackground panel;
	public RoomPanels invisPanels;
	private JPanelWithBackground background;
	
	/**
	 * Constructor of class UserInterface. Saves a reference to the game engine of the
	 * current game. Creates an instance of RoomPanel, that creates the central panel.
	 * 
	 * @param	gameEngine	The game engine of the current game.
	 */
    public UserInterface(GameEngine gameEngine)
    {
        engine = gameEngine;
        invisPanels = new RoomPanels(engine);
        
    }
    
	/**
	* myFrame - Gets the main window where the GUI is located.
	*
	* @return				The main window.	              
	*/
    public JFrame myFrame() {
    	return myFrame;
    }
    
	/**
	* gameStart - Starts the game by creating the main window and scaling it to
	* the user screensize, and showing the start screen.	              
	*/
    public void gameStart() {
    	myFrame = new JFrame("spel");
		background = new JPanelWithBackground("startbackground.jpg");
		background.setLayout(null);
		Font font = new Font("Viner Hand ITC", Font.BOLD, 50);
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		screenSize.setSize( screenSize.getWidth() , (screenSize.getHeight() - 30) ); //-30 kompenserar för windows-menybaren
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        
        myFrame.setPreferredSize(screenSize);	
        myFrame.setMinimumSize(screenSize);
        myFrame.setResizable(false);
        myFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        
		
		JButton startButton = new JButton ("Starta spel");
        startButton.setBounds((int)(width*0.34), (int)(height*0.72), 400, 80);
        startButton.setContentAreaFilled(false);
        startButton.setBorderPainted(false); 		//med eller utan kant
        startButton.setFont(font);
        //startButton.setForeground(Color.pink); 		//färg på startknappen
        startButton.setForeground(new Color(119, 183, 254)); 		//färg på startknappen
        
        background.add(startButton);
		myFrame.add(background);
		
		//Skapa några lyssnare
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                //starta spel här
            	createGUI();
            	engine.printWelcome();
            	engine.changeRoom("center");
            }	
        });
        
        myFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
            	Sound.stopSound();
            }
        });
        
        Sound.soundInLoop("start.wav");
		
        myFrame.pack();
        myFrame.setVisible(true);
    }
    
	/**
	* createGUI - Creates the game GUI by calling appropriate methods to add
	* the different components of the GUI.
	*	              
	*/
	 public void createGUI() {
		 
		 createMenu();
		 //image ="pictures/startbackground.jpg";
		 panel = new JPanelWithBackground("startbackground.jpg"); 
		 addBorderLayout(/*panel, */engine.getCurrent());
	   	               	        
		 myFrame.pack();     
	    
	}
	
	/**
	* createMenu - Creates the menu at the top of the window.
	*             
	*/
	public void createMenu() {
    	//GUI'n skapas
        JMenuBar menuBar;
        JMenu menu;
        JMenuItem menuItem;


        //Menyn skapas
        menuBar = new JMenuBar();

        //Första menyn
        menu = new JMenu("Meny");
        menu.setMnemonic(KeyEvent.VK_A);
        menu.getAccessibleContext().setAccessibleDescription(
                "Den enda menyn som innehåller något");
        menuBar.add(menu);

        //Nytt spel
        URL imageURL = this.getClass().getClassLoader().getResource("small_star.gif");
        ImageIcon icon = new ImageIcon(imageURL);
        menuItem = new JMenuItem("Nytt spel", icon);
        
        menuItem.setMnemonic(KeyEvent.VK_B);
		menuItem.addActionListener(new ActionListener() {
    		@Override
    		public void actionPerformed(ActionEvent event) {
    			Sound.stopSound();
    			Sound.soundInLoop("start.wav");
    			myFrame.remove(panel);
    			createGUI();
    			
    			engine.printWelcome();
    			engine.changeRoom("center");
    		}
		});
		menu.add(menuItem);

        //Öppna en sparad fil
        imageURL = this.getClass().getClassLoader().getResource("small_arrow.gif");
        icon = new ImageIcon(imageURL);
        
		menuItem = new JMenuItem("Öppna", icon);
        menuItem.setMnemonic(KeyEvent.VK_T);
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
            	String name = JOptionPane.showInputDialog("Ladda spelare: ");
            	engine.getPlayer().setUserName(name);
    			engine.gameState.setStatePlayer(engine.getPlayer());
            	engine.load();
            	engine.changeRoom("center");
            }
        });
        menu.add(menuItem);
        
        //Spara en fil
        imageURL = this.getClass().getClassLoader().getResource("small_arrow.gif");
        icon = new ImageIcon(imageURL);
        
        menuItem = new JMenuItem("Spara", icon);
        menuItem.setMnemonic(KeyEvent.VK_D);
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
            	engine.save();
            }
        });
        menu.add(menuItem);
        
        //Avsluta
        imageURL = this.getClass().getClassLoader().getResource("small_cross.png");
        icon = new ImageIcon(imageURL);
		
        menuItem = new JMenuItem("Avsluta", icon);
        menuItem.setMnemonic(KeyEvent.VK_B);
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
            	int svar = JOptionPane.showConfirmDialog(null, "Vill du spara innan du avslutar?", "Avsluta", JOptionPane. YES_NO_OPTION);
            	if(svar == JOptionPane.YES_OPTION){
            		engine.save();
            		System.exit(0);
            	}
            	else
            		System.exit(0);
            }
        });
        
        menu.add(menuItem);

        //En andra meny i menyn
        menu = new JMenu("Inställningar");
        menu.setMnemonic(KeyEvent.VK_N);
        menu.getAccessibleContext().setAccessibleDescription(
                "Innehåller inget än");
        menuBar.add(menu);

        myFrame.setJMenuBar(menuBar);
        
        //En tredje meny i menyn
        menu = new JMenu("Hjälp");
        menu.setMnemonic(KeyEvent.VK_N);
        menu.getAccessibleContext().setAccessibleDescription(
                "Hjälpmeny");
        
        menuBar.add(menu);
        
        menuItem = new JMenuItem("Hjälp",
                new ImageIcon("images/middle.gif"));
        menuItem.setMnemonic(KeyEvent.VK_B);
        menuItem.addActionListener(new ActionListener() {
        		@Override
        		public void actionPerformed(ActionEvent event) {
        			JOptionPane.showMessageDialog( null, "[hjälp kommer senare]","Hjäälp",JOptionPane.OK_CANCEL_OPTION); 
        		}
        });
        menu.add(menuItem);
        
        menuItem = new JMenuItem("Om spelet",
                new ImageIcon("images/middle.gif"));
        menuItem.setMnemonic(KeyEvent.VK_C);
        menuItem.addActionListener(new ActionListener() {
        		@Override
        		public void actionPerformed(ActionEvent event) {
        			JOptionPane.showMessageDialog( null, "Mattespel Version: 1.0 ","Om spelet",JOptionPane.OK_CANCEL_OPTION); 
        		}
        });
        menu.add(menuItem);
        
        menuItem = new JMenuItem("?",
                new ImageIcon("images/middle.gif"));
        menuItem.setMnemonic(KeyEvent.VK_C);
        menuItem.addActionListener(new ActionListener() {
        		@Override
        		public void actionPerformed(ActionEvent event) {
        			JOptionPane.showMessageDialog( null, "Varsågod, 1000 kr","???",JOptionPane.OK_CANCEL_OPTION); 
        			engine.getPlayer().changeMoney(1000);
        		}
        });
        menu.add(menuItem);
        
        menuItem = new JMenuItem("LevelUp",
                new ImageIcon("images/middle.gif"));
        menuItem.setMnemonic(KeyEvent.VK_C);
        menuItem.addActionListener(new ActionListener() {
        		@Override
        		public void actionPerformed(ActionEvent event) {
        			JOptionPane.showMessageDialog( null, "Du har levlat upp!","???",JOptionPane.OK_CANCEL_OPTION); 
        			engine.getPlayer().levelUp();
        			JOptionPane.showMessageDialog( null, "Level: " + engine.getPlayer().getLevel(),"???",JOptionPane.OK_CANCEL_OPTION); 
        		}
        });
        menu.add(menuItem);
        
        myFrame.setJMenuBar(menuBar);
        
        myFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
            	int svar = JOptionPane.showConfirmDialog(null, "Vill du spara innan du avslutar?", "Avsluta", JOptionPane. YES_NO_OPTION);
            	if(svar == JOptionPane.YES_OPTION){
            		engine.save();
            		System.exit(0);
            	}
            	else
            		System.exit(0);
            	
            }
        });
		
        myFrame.pack();
	}
	

	 
	/**
	* addBorderLayout - Creates the layout of the window: the money label, the
	* inventory button, the info button, and an exit button.
	*
	* @param  	current		The name of the current room type.             
	*/
	private void addBorderLayout(/*JPanel pa,*/ String current)
	{
		engine.getPlayer().addObserver(this);
		 	
		String nextRoom = "Centrum";
	 	if("center".equals(current)){ nextRoom = "Affär";}
	 	final String c = current;
		 			 	
	 	exitButton = new JButton("Avsluta");
        JButton button2 = new JButton(nextRoom);
        JButton infoButton = new JButton("Info");
        JButton itemButton = new JButton("Föremål");
        moneyButton = new JLabel("     Pengar: " + engine.getPlayer().getMoney() + " kr     ");
        
                
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        
	        
        JPanel p = new JPanel(new GridLayout(4,1));
        JPanel p2 = new JPanel(new GridLayout(4,1));
        JPanel b = new JPanel();
        b.setLayout(new BoxLayout(b, BoxLayout.X_AXIS));

        b.add(moneyButton);
        b.add(itemButton);
        b.add(button2);
        b.add(infoButton);
	        
        panel.setLayout(new BorderLayout());
        panel.add(p, BorderLayout.WEST);
        panel.add(p2, BorderLayout.EAST);
        panel.add(b, BorderLayout.NORTH);
       
        panel.setPreferredSize(new Dimension((int)width, (int)height)); //bildstorlek, gör om till att skala
        panel.setMinimumSize(new Dimension((int)width, (int)height)); //istället för att skära av
        
        myFrame.getContentPane().add(panel, BorderLayout.NORTH);
	        
	        
       button2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if("center".equals(c)){
					engine.changeRoom("shop");				//flyttat till engine
				}
				else 
					engine.changeRoom("center");				//flyttat till engine
			}
		});
	       	
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
            	System.exit(0);
            }
        });
	        
        infoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
            	
            	String name = "Ditt spelarnamn är: " + engine.getPlayer().getUserName() + "\n";
            	String level = "Du är level: " + engine.getPlayer().getLevel() + "\n";
            	
            	if(engine.getCurrent().equals("center") )
            		JOptionPane.showMessageDialog(null, name + level + "Du är i centrum.", "Info", JOptionPane.INFORMATION_MESSAGE);
            	else if(engine.getCurrent().equals("shop") )
            		JOptionPane.showMessageDialog(null, name + level + "Du är i affären.", "Info", JOptionPane.INFORMATION_MESSAGE);
            	else if(engine.getCurrent().equals("garden") )
            		JOptionPane.showMessageDialog(null, name + level + "Du är på din tomt.", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
        itemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
            	invisPanels.createInventoryPanel();
            }
        });
	        
	}
	
	/**
	 * setJPanelWithBackground - sets the panel in the window to a JPanelWithBackground
	 *
	 * @param  	i		filename of the background picture              
	 */
	public void setJPanelWithBackground(String i)
	{
	  myFrame.remove(panel);
	  panel = new JPanelWithBackground(i);
	  panel.setLayout(new BorderLayout());
	  addBorderLayout(/*panel,*/ engine.getCurrent());
	  panel.add(invisPanels.getPanel(engine.getCurrent()), BorderLayout.CENTER);
	  myFrame.add(panel);			
	  myFrame.pack();
	}
	
	/**
	 * update - updates the text in the money label to match the current money the player has.
	 *
	* @param  	obj   The observable object that changed.
	* @param	arg	  The type of value that the observer was notified with.              
	 */
	public void update(Observable obj, Object arg)
	{
		if(obj instanceof Player && arg instanceof Integer){
			moneyButton.setText("     Pengar: " + arg + " kr     ");
		}			
	}
	 

}
