package mvc.controller;

import mvc.model.GameObjectEnum;

/**
 * Timer Controller interface.
 */
public interface TimerController {

    /**
     * Creates a new timer for the specified power up.
     * @param powerUpType
     */
    void initializeTimer(GameObjectEnum powerUpType);

    /**
     * Sets the initial delay of the timer.
     * @param duration
     */
    void setPowerUpDuration(int duration);

    /**
     * Starts the timer.
     */
    void startTimer();

    /**
     * Stops the timer.
     */
    void stopTimer();
}
