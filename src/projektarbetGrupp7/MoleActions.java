package projektarbetGrupp7;

import javax.swing.JTextArea;

/**
 * The MoleActions class handles the score in the mole-game.
 * The class contains an instance the MiniGame class.
 * 
 * @author Linda Karlsson
 * @version 2015-03-05
 */
public class MoleActions {
	private MiniGame miniGame;
    private static final String rightAns = "Antal poäng: ";
    
    /**
    * Class-constructor
    * 
    * @param   miniGame		instance of MiniGame
    */
	public MoleActions(MiniGame miniGame) {
		this.miniGame = miniGame;
		
	}

    /**
    * Updates the textarea which contains the score
    * 
    * @param	a    the textarea of the score that gets updated  
    */
	public void updateScore(JTextArea a) {
		miniGame.miniGameActions.setScore(10);
		a.setText("\n " + rightAns + miniGame.miniGameActions.getScore());
	}
	
    /**
    * Decreases the score by 2 and updates the amount of answers 
    * 
    * @param 	a    the score-textarea that gets updated     
    */
	public void wrongAnswere(JTextArea a) {
		miniGame.miniGameActions.setScore(-2);
		miniGame.miniGameActions.setWrongAnswers();
		a.setText("\n " + rightAns + miniGame.miniGameActions.getScore());
	}
}
