package mvc.model.impl;

import java.awt.geom.Point2D;

import mvc.controller.LivesController;

/**
 * Bomb implementation.
 */
public class BombImpl extends SliceableModelImpl {

    private final LivesController livesController;

    /**
     * Constructor of a bomb.
     *
     * @param nsides number of sides of the sliceable polygon.
     * @param position Point2D position of the sliceable, to update every timestep.
     * @param velocity Point2D vector of the new velocity of the object.
     * @param sliceableId the sliceable identifier.
     * @param livesController
     */
    public BombImpl(final Integer nsides, final Point2D position,
                    final Point2D velocity, final int sliceableId,
                    final LivesController livesController) {
        super(nsides, position, velocity, sliceableId);
        this.livesController = livesController;
    }

    /**
     * {@inheritdoc}.
     */
    @Override
    public void cut() {
        this.livesController.decreaseLives(1);
    }
}
