package mvc.view;

import mvc.view.impl.ScoreViewImpl;

/**
 * Interface that represents the Player's score.
 */
public interface ScoreView {

    /**
     * Gets the shown player's score.
     * @return The score of the Player.
     */
    int getScore();

    /**
     * Draw the new score.
     */
    void drawScore();

    /**
     * Increease the player's score.
     * @param points
     */
    void increaseScore(int points);

    /**
     * Decrease the player's score.
     * @param points
     */
    void decreaseScore(int points);

    /**
     * @return the istance of the Score class. Used to resolve spotbugs errors.
     */
    ScoreViewImpl getCurrScoreImpl();
}
