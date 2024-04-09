package mvc.controller;

import mvc.view.impl.ScoreViewImpl;

/**
 * Controller that manages the logic of the user's score.
 */
public interface ScoreController {

    /**
     * Gets the shown player's score.
     * @return The score of the Player.
     */
    int getScore();

    /**
     * Increease the player's score.
     * @param points
     */
    void increaseScore(int points);

    /**
     * @return the istance of the Score class. Used to resolve spotbugs errors.
     */
    ScoreViewImpl getScoreInstance();
}
