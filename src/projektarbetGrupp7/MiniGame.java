package projektarbetGrupp7;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

/**
 * The MiniGame class creates a frame and most of its graphics.
 * This class contains an instance of the Player class.
 * 
 * 
 * @author  Linda Karlsson
 * @version 2015-03-05
 */
public class MiniGame implements ActionListener, MouseListener {
	public JFrame window;
	public JPanelWithBackground pan, pan2;
    public JButton startB;
    public JButton instructB, closeB, cash, replay, emptyButton;
    public JTextArea text =  new JTextArea();
    public JLabel contentPane;
     
	public static JTextArea pointsSoFarText = new JTextArea();
	private static JTextArea mathProblem = new JTextArea();

    static boolean cursorClick = false;
    
    public MiniGameActions miniGameActions;
    public Player player;

    
    /**
    * Class-constructor
    * 
    * @param   player	Current player
    */
    public MiniGame(Player player)
    {
    	this.player = player;
    	miniGameActions = new MiniGameActions(this);
    	window = new JFrame ("Whack A Mole-spel");
		pan = new JPanelWithBackground("falt.jpg");
		
		URL imageURL = this.getClass().getClassLoader().getResource("avsluta.png");
    	ImageIcon end = new ImageIcon(imageURL);
    	imageURL = this.getClass().getClassLoader().getResource("instruk.png");
    	ImageIcon inst = new ImageIcon(imageURL);
    	imageURL = this.getClass().getClassLoader().getResource("c.png");
    	ImageIcon icon = new ImageIcon(imageURL); 
    	imageURL = this.getClass().getClassLoader().getResource("start.png");
    	ImageIcon sticon = new ImageIcon(imageURL);
    	imageURL = this.getClass().getClassLoader().getResource("coinsS.png");
    	ImageIcon money = new ImageIcon(imageURL);
    	
    	closeB = new JButton(end);
    	startB = new JButton(icon);
    	instructB = new JButton(inst);
    	cash = new JButton(money);
    	
    	drawWindowWithThings();

    	startB.setRolloverIcon(sticon);
    	
    	closeB.addActionListener(this);
    	startB.addActionListener(this);
    	instructB.addActionListener(this);
    	
    	window.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
            	Sound.stopSound();
            	//Sound.soundInLoop("Forest.wav");
            }
        });
    	
    	window.pack();
    	window.setVisible(true);
    }
    
    /**
     * Adds components (buttons) to the visible JFrame and JPanel.
     */
    public void drawWindowWithThings(){

    	window.add(pan);

    	pan.setPreferredSize (new Dimension (700, 700));
    	pan.setLayout(new GridLayout(5,3));


    	closeB.setContentAreaFilled(false);
        closeB.setBorderPainted(false);
        
        startB.setContentAreaFilled(false);
        startB.setBorderPainted(false);
        
        instructB.setContentAreaFilled(false);
        instructB.setBorderPainted(false);
        
        cash.setContentAreaFilled(false);
        cash.setBorderPainted(false);
        
    	pan.add(closeB);
    	pan.add(instructB);
    	pan.add(startB);
     	pan.add(cash);
    	pan.add(text);
    }
    
    /**
     * Class-constructor
     * 
     * @param e		Action event
     */
    public void actionPerformed (ActionEvent e)
    {
		if (e.getSource() == startB)
		{
	    	window.remove(pan);

	    	Sound.playSomeSound("Randomize27.wav");
	    	startMoleGame();
		}
		else if (e.getSource() == closeB){
			if( miniGameActions.getScore() > 0 ){
				player.changeMoney(miniGameActions.getScore());
				JOptionPane.showMessageDialog( null, "Du vann " + miniGameActions.getScore() + 
						" kr!" ,"Poäng", JOptionPane.OK_CANCEL_OPTION); 
			}
			if(miniGameActions.getScore() >= 100){
				player.levelUp();
				JOptionPane.showMessageDialog( null, "Du har levlat upp!\n" + "Du är nu level: " +
						player.getLevel(),"Level up!",JOptionPane.OK_CANCEL_OPTION); 
			}
			window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));	
			Sound.stopSound();
			Sound.soundInLoop("Forest.wav");
		}
		else if (e.getSource() == instructB)
		{
			Sound.playSomeSound("Randomize8.wav");
			text.setFont(new Font("Serif", Font.BOLD + Font.ITALIC, 21));
			text.setText("  Spelinstruktioner:\n  Klicka på mullvaderna som visar rätt svar till matteproblemet\n  för att vinna pengar. " + ""
					+ "Rätt svar ger 10 kr och fel svar ger -2 kr. \n  Vinner du inget tas inga pengar bort. " + 
					"Om du får 100 poäng i sträck\n levlar du upp.");
		    text.setEditable(false);
		}
		else if (e.getSource() == replay)
		{
			pan2.remove(replay);
			window.remove(pan2);
			startMoleGame();
		}
	}
    
    /**
     * Changes the components of the window.
     */
    private void startMoleGame(){
    	pan2 = new JPanelWithBackground("falt.jpg");
    	pan2.setPreferredSize (new Dimension (700, 700));
    	pan2.setLayout(new GridLayout(6,5));
		window.add(pan2);

		setTheCursor();
    	createMole();

        pan2.addMouseListener(this);
		
        // ändrar texten i textrutorna
    	pointsSoFarText.setFont(new Font("Serif", Font.BOLD, 28));
    	mathProblem.setFont(new Font("Serif", Font.BOLD, 50));

    	window.pack();
    }

    /**
     * Changes the cursor into a hammer.
     */
	public void setTheCursor(){
		Toolkit toolkit = Toolkit.getDefaultToolkit();  
		if(cursorClick == true){
			
			URL imageURL = this.getClass().getClassLoader().getResource("hammer2.png");
			ImageIcon icon = new ImageIcon(imageURL);
			Image image = icon.getImage();
			
			Point hotSpot = new Point(0,0);
			Cursor cursor = toolkit.createCustomCursor(image, hotSpot, "hammer2");
			 
		    pan2.setCursor(cursor);
		    cursorClick = false;
		}
		else{
			URL imageURL = this.getClass().getClassLoader().getResource("hammer.png");
			ImageIcon icon = new ImageIcon(imageURL);
			Image image = icon.getImage();
	
			Point hotSpot = new Point(0,0);
			Cursor cursor = toolkit.createCustomCursor(image, hotSpot, "hammer");
			 
		    pan2.setCursor(cursor);}  
	}

    /**
     * Changes the cursor picture when the mouse button is pressed.
     */
	@Override
	public void mousePressed(MouseEvent e) {
		cursorClick = true;
		setTheCursor();
	}
    /**
     * Changes the cursor picture when the mouse button is released.
     */
	@Override
	public void mouseReleased(MouseEvent e) {
		setTheCursor();
	}
	
    /**
     * Overridden method
     */
	@Override
	public void mouseClicked(MouseEvent e) {	
	}

    /**
     * Overridden method
     */
	@Override
	public void mouseEntered(MouseEvent e) {
	}

    /**
     * Overridden method
     */
	@Override
	public void mouseExited(MouseEvent e) {
	}

	/**
	 * Creates a field of moles
	 */
	public void createMole(){
    	int b = 0;
    	
    	String label = "";

    	Random rand = new Random();
    	int j = rand.nextInt(9) + 3;
    	
    	pan2.removeAll();
    	
    	pan2.add(pointsSoFarText);
    	pointsSoFarText.setEditable(false);
    	
    	pan2.add(mathProblem);
    	mathProblem.setEditable(false);
    	
    	pan2.add(closeB);
    	
    	for( int i=0; i<12; i++ ){
    		b = rand.nextInt(101); // slumpade tal till mullvadar
    		label = "" + b;
    		
    		if( i < 3 ){
    			emptyButton = new JButton();
    			emptyButton.setOpaque(false);
    			emptyButton.setContentAreaFilled(false);
    			emptyButton.setBorderPainted(false);
        		pan2.add(emptyButton);
    		}
    		else if( i == j ){
    			miniGameActions.createMathProblemSolution();
    			Mole moleIm = new Mole(miniGameActions.getMathSolutionString(),this);
	        	pan2.add(moleIm);
	        	moleIm.addMouseListener(this);

    		}
    		else{
    			Mole moleIm = new Mole(label, this);
        		pan2.add(moleIm);
        		moleIm.addMouseListener(this);
    		}
    	}
    	window.pack();
	}

	/**
	 * Adds the option to replay the game.
	 */
	public void tryAgain(){
		replay = new JButton("Spela igen");
    	replay.addActionListener(this);
    	replay.setFont(new Font("Serif", Font.BOLD, 40));
    	replay.setForeground(Color.RED);
    	replay.setOpaque(false);
    	replay.setContentAreaFilled(false);
    	replay.setBorderPainted(false);
    	
		pan2.removeAll();
		pan2.add(replay);
		pan2.add(closeB);
		window.add(pan2);
		window.pack();
		
		//System.out.println("add replay");
	}
	
	/**
	 * Returns a JTextArea.
	 * 
	 * @return pointsSoFarText    the JTextArea which contains the points.
	 */
    public JTextArea getPointsSoFarText()
    {
    	return pointsSoFarText;
    }
    
	/**
	 * Returns a JTextArea.
	 * 
	 * @return mathProblem    the JTextArea which contains the math-problem.
	 */
    public JTextArea getMathProblemTextArea()
    {
    	return mathProblem;
    }
}
