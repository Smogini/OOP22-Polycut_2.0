package mvc.controller;

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
     * Set the bomb immunity.
     * @param immunity if the player is immune
     */
    void setBombImmunity(boolean immunity);

    /**
     * @return true if the user is currently immune to bombs, false otherwise.
     */
    boolean isBombImmunity();
}
