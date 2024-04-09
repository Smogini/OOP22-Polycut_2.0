package mvc.view;

import mvc.controller.BladeController;
import mvc.view.impl.GameAreaImpl;

/**
 * Interface that defines the frame where the game is displayed.
 */
public interface GameScreen {

    /**
     * Prepare the frame that contains all the game's elements.
     * @param bladeController
     * @return GameArea
     */
    GameAreaImpl createAndShowGui(BladeController bladeController);

    /**
     * @return the score
     */
    int getScoreValue();

    /**
     * Display the GameOver panel.
     */
    void gameOverPanel();

    /**
     * Set the new best score.
     * @param record the new player's record
     */
    void setNewBestScore(int record);

    /**
     * Get the current best score.
     * @return the best score of the player
     */
    int getCurrentBestScore();

    /**
     * @return the height of the screen
     */
    int getScreenHeight();

    /**
     * @return the width of the screen
     */
    int getScreenWidth();
}
