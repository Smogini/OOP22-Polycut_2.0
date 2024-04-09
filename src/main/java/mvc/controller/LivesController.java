package mvc.controller;

import mvc.view.impl.LiveImpl;

/**
 * Controller that manages the logic of the user's lives.
 */
public interface LivesController {

    /**
     * @return the current amount of lives.
     */
    int getCurrentLives();

    /**
     * @return the istance of the lives label.
     */
    LiveImpl getLiveInstance();

    /**
     * Decrease the amount of lives specified by 'lives'.
     * @param lives
     */
    void decreaseLives(int lives);
}
