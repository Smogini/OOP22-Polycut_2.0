package mvc.view;

/**
 * Timer View interface.
 */
public interface TimerView {

    /**
     * Updates the label with the remaining time.
     * @param remainingTime
     */
    void updateTimerLabel(int remainingTime);

    /**
     * Hides the label.
     */
    void hideTimerLabel();
}
