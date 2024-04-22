package mvc.view;

import java.awt.geom.Point2D;
import java.util.List;

import mvc.model.GameObjectEnum;
import mvc.model.SliceableModel;

/**
 * GameArea interface, models methods for drawing and updating sliceables.
 */
public interface GameArea {

    /**
     * Create The Sliceable and attach the listener.
     * @param sliceable the sliceable to show on the screen.
     * @param type of Sliceable, to manage mouse listener and dimensions.
     */
    void drawSliceable(SliceableModel sliceable, GameObjectEnum type);

    /**
     * Update the position of the sliceable.
     * @param sliceableID of the sliceable
     * @param newPosition of the sliceable
     */
    void updatePosition(Integer sliceableID, Point2D newPosition);

    /**
     * Clean the area.
     * @param sliceableID the object to remove
     */
    void clean(Integer sliceableID);

    /**
     * @return a list of ID of sliced elements.
     */
    List<Integer> getSliced();
}
