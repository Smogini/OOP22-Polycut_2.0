package mvc.view;

import javax.swing.JLabel;

import mvc.model.GameObjectEnum;

/**
 * Timer View interface.
 */
public interface TimerView {

    /**
     * Updates the specified label with the remaining time.
     * @param label
     * @param remainingTime
     * @param powerUpType
     */
    void updateTimerLabel(JLabel label, int remainingTime, GameObjectEnum powerUpType);

    /**
     * Attach the label to the panel.
     * @param label
     * @param powerUpType
     */
    void addLabel(JLabel label, GameObjectEnum powerUpType);

    /**
     * Removes the label from the panel.
     * @param label
     * @param powerUpType
     */
    void removeLabel(JLabel label, GameObjectEnum powerUpType);
}
