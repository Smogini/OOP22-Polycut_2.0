package mvc.controller;

import mvc.model.GameObjectEnum;
import mvc.model.SliceableModel;

/**
 * Blade controller's interface, manages the user input.
 */
public interface BladeController {

    /**
     * Inserts the specified power up in the Map, initializing the TimerController for it.
     * @param powerUpType
     * @param duration
     */
    void insertPowerUp(GameObjectEnum powerUpType, int duration);

    /**
     * Method to cut the specified sliceable.
     * @param sliceable it can be a polygon, bomb or a specific power up.
     */
    void cutSliceable(SliceableModel sliceable);

    /**
     * Sets the bomb immunity.
     * @param immunity
     */
    void setBombImmunity(boolean immunity);

    /**
     * @return true if the user is currently immune to bombs, false otherwise.
     */
    boolean isBombImmunity();

    /**
     * @return true if the freeze power up is active, false otherwise.
     */
    boolean isFrozen();

    /**
     * Starts the timer for the power up.
     * @param powerUpType
     */
    void startPowerUpTimer(GameObjectEnum powerUpType);

    /**
     * Sets the power up effect.
     * @param powerUpType
     * @param enable
     */
    void setPowerUpStatus(GameObjectEnum powerUpType, boolean enable);
}
