package projektarbetGrupp7;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.Timer;

/**
 * The Mole class creates a button and its graphics.
 * The class contains instances of the MoleActions-, MiniGame-, Timer- and
 * ImageIcon class.
 * 
 * @author Linda Karlsson
 * @version 2015-03-05
 */

public class Mole extends JButton implements ActionListener {
	private static final long serialVersionUID = 1L;
	private MoleActions actions;
    private Timer timer;
    private int animDuration = 2300;
    private long animStartTime;
    
    private int translateY = 0;
    private static final int y_max = 100;
    
    private MiniGame miniGame;
    private String moleAns;
    private ImageIcon molePic;
    private ImageIcon wrongAnsPic;
    
    /**
    * Class-constructor
    * Creates a mole-button and starts a timer.
    * 
    * @param label    a String which contains a number
    * @param miniGame   MiniGame
    */
    public Mole(String label, MiniGame miniGame) {
    	super(label);
        this.miniGame = miniGame;
        actions = new MoleActions(miniGame);
        moleAns = label;    	
    	
        setFont(new Font("Serif", Font.BOLD, 40));
        setForeground(new Color(7800));
        
        setOpaque(false);
    	setContentAreaFilled(false);
        setBorderPainted(false);
        
		URL imageURL = this.getClass().getClassLoader().getResource("dirt_pile.png");
		ImageIcon icon = new ImageIcon(imageURL);
	    Image img = icon.getImage();
	    Image newimg = img.getScaledInstance(110, 80,  java.awt.Image.SCALE_FAST);
	    ImageIcon newIcon = new ImageIcon(newimg);
        
        //wrongAnsPic = new ImageIcon("pictures/squirrel2.png");
	    wrongAnsPic = newIcon;
	    
	    imageURL = this.getClass().getClassLoader().getResource("nymole2.png");
	    molePic = new ImageIcon(imageURL);
	    
        setIcon(molePic);
        addActionListener(this);
        
        timer = new Timer(30, this);
        animStartTime = System.nanoTime() / 1000000;
        timer.start();
    }
    
    /**
     * This method shows the component on the position (0, translateY). 
     * 
     * @param g    the component that is shown
     */
    public void paint(Graphics g) {
        g.translate(0, translateY);
        super.paint(g);
    }
    
    /**
     * ActionPerformed handles the pressed button that stops the animation, 
     * points when the button containing the answer is right or wrong and 
     * when the player loose.
     * 
     * @param e    the actionevent
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(this)) {
                timer.stop();

                // Om den klickade mullvadens tal = rätt svar, lägg till 10 poäng
                // och skapa nya mullvadar
                if(moleAns.equals(miniGame.miniGameActions.getMathSolutionString())){
                	actions.updateScore(miniGame.getPointsSoFarText());
                	Sound.playSomeSound("coin.wav");
                	miniGame.createMole();
                }
                // Spelet förloras vid 5 fel
                else if(miniGame.miniGameActions.getWrongAnswers() > 3){
                	miniGame.tryAgain();
                	miniGame.miniGameActions.setWrongAnswers();
                }
                else{
                	actions.wrongAnswere(miniGame.getPointsSoFarText());
                	setIcon(wrongAnsPic);
                	Sound.playSomeSound("boing3.wav");
                }
                translateY = 0;
        } else {
            // Timer händelse
            // beräknar den passerade fraktionen
            long ongoingTime = System.nanoTime() / 1000000;
            long totalTime = ongoingTime - animStartTime;
            
            if (totalTime > animDuration) {
                animStartTime = ongoingTime;
            }
            
            float fraction = (float)totalTime / animDuration;
            fraction = Math.min(1.0f, fraction);
            
            // translateY går från 0 till y_max, fraktionen går från 0 till 1
            if (fraction < .5f) {
                translateY = (int)(y_max * (2 * fraction));
            } else {
                translateY = (int)(y_max * (2 * (1 - fraction)));
            }
            // updaterar komponenten med den nya positionen
            repaint();
        }
    }

}
