package mvc.model;

import mvc.model.impl.BombImpl;
import mvc.model.impl.PolygonImpl;

/**
 * Factory pattern used to create different Sliceable objects: bombs and polygons.
 * It simplifies the multiple creation of objects.
 */
public interface SliceableFactory {
    /**
     * Called by the game world when in need to create a new Bomb.
     * @param bombId
     * @return new Sliceable.
     */
    BombImpl createBomb(int bombId);

    /**
     * Called by the game world when in need to create a new Polygon.
     * @param polygonId
     * @return new Polygon.
     */
    PolygonImpl createPolygon(int polygonId);

    /**
     * Called by the game world when in need to create a new Power up.
     * @param powerUpId
     * @return new Power up.
     */
    PowerUpModel createPowerUp(int powerUpId);
}
