package mvc.model;

/**
 * Timer Model interface, defines the main methods to manage a timer.
 */
public interface TimerModel {

    /**
     * Set the duration of the timer.
     * @param duration
     */
    void setTimerDuration(int duration);

    /**
     * @return the timer's remaining time.
     */
    int getRemainigTime();
}
