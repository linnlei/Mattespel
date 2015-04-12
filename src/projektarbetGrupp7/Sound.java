package projektarbetGrupp7;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 * The Sound class handles the music and sounds in the game.
 * 
 * @author Linda Karlsson, Fifi Johansson
 * @version 2015-03-05
 * @see Clip
 */
public class Sound {
	static Clip clip;
	static Clip clipLoop;
	
	  /**
     * Class-constructor
     */
	public Sound(){
		
	}
    /**
     * Plays sounds/music. Works only with wave-files.
     * 
     * @param url    the source-name of the sound
     */
	public static synchronized void playSomeSound(final String url) {
		  new Thread(new Runnable() {
		    public void run() {
		      try {
		        clip = AudioSystem.getClip();
		        AudioInputStream inputStream = AudioSystem.getAudioInputStream(this.getClass().getResource(url) );	
		        
		        clip.open(inputStream);
		        clip.start(); 
		      } catch (Exception e) {
		        System.err.println(e.getMessage());
		      }
		    }
		  }).start();
	}
	
    /**
     * Continuously plays sounds/music. Works only with wave-files.
     * 
     * @param url    the source-name of the sound
     */
	public static synchronized void soundInLoop(final String url) {
		new Thread(new Runnable() {
			    public void run() {
			      try {
			    	clipLoop = AudioSystem.getClip();
			    	
			    	AudioInputStream inputStream = AudioSystem.getAudioInputStream(this.getClass().getResource(url) );	

			        clipLoop.open(inputStream);
			        clipLoop.start(); 
			        clipLoop.loop(1000);
			      } catch (Exception e) {
			        System.err.println(e.getMessage());
			      }
			    }
			  }).start();
	}
	
    /**
     * Stops sounds/music-loop. 
     * 
     * @see soundInLoop
     */
	public static synchronized void stopSound() {
		clipLoop.stop();
	}
}
