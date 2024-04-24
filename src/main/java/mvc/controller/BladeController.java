package mvc.controller;

import java.util.Map;

import mvc.model.GameObjectEnum;
import mvc.model.SliceableModel;

/**
 * Blade controller's interface, manages the user input.
 */
public interface BladeController {

    /**
     * Method to cut the specified sliceable.
     * @param sliceable it can be a polygon, bomb or a specific power up.
     */
    void cutSliceable(SliceableModel sliceable);

    /**
     * Starts the timer for the power up.
     * @param powerUpType
     */
    void startPowerUpTimer(GameObjectEnum powerUpType);

    /**
     * Sets the power up effect.
     * @param powerUpType
     * @param duration
     * @param enable
     */
    void setPowerUpStatus(GameObjectEnum powerUpType, int duration, boolean enable);

    /**
     * @return the hashmap of every powerup status.
     */
    Map<GameObjectEnum, Boolean> getEnabledPowerUp();

    /**
     * @param powerUpType
     * @return true if the specified powerUpType in enabled, false otherwise.
     */
    boolean isPowerUpEnabled(GameObjectEnum powerUpType);
}
