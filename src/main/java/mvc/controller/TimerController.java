package mvc.controller;

/**
 * Timer Controller interface.
 */
public interface TimerController {

    /**
     * Starts the timer.
     * @param duration of the timer.
     */
    void startTImer(int duration);

    /**
     * Stops the timer.
     */
    void stopTimer();
}
