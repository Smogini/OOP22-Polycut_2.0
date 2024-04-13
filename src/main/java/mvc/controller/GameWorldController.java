package mvc.controller;

import java.util.List;

import mvc.model.PowerUpModel;
import mvc.model.SliceableModel;
import mvc.model.impl.BombImpl;

/**
 * Main controller of the game. Manages all the model and view of the game.
 */
public interface GameWorldController {
    /**
     * @return a list of the polygons in game.
     */
    List<SliceableModel> getPolygons();

    /**
     * Update the current list of polygons with a new one.
     * @param updatedList the new list.
     */
    void setPolygons(List<SliceableModel> updatedList);

    /**
     * Getter of the bombs.
     * @return a list of the bombs in game.
     */
    List<BombImpl> getBombs();

    /**
     * Update the current list of bombs with a new one.
     * @param updatedList the new list.
     */
    void setBombs(List<BombImpl> updatedList);

    /**
     * Creates a single polygon.
     * @param polygonId the polygon identifier.
     * @return the newly created polygon.
     */
    SliceableModel createPolygon(int polygonId);

    /**
     * Creates a single bomb.
     * @param bombId the bomb identifier.
     * @return the newly created bomb.
     */
    SliceableModel createBomb(int bombId);

    /**
     * Creates a single power up.
     * @param powerUpID the power up identifier.
     * @return the newly created power up.
     */
    PowerUpModel createPowerUp(int powerUpID);

    /**
     * Initialize the game screen and starts the game loop.
     */
    void startLoop();

    /**
     * 
     * @param sliceableId
     */
    void outOfBoundDelete(int sliceableId);

    /**
     * Creates a new list from the concatenation of bombs and sliceables.
     * @return a list of all sliceables
     */
    List<SliceableModel> getSliceables();

    /**
     * @return the controller of the blade.
     */
    BladeController getBladeController();

    /**
     * Set the bomb immunity.
     * @param immunity if the player is immune
     */
    void setBombImmunity(boolean immunity);
}
