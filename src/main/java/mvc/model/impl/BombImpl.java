package mvc.model.impl;

import java.awt.geom.Point2D;

import mvc.controller.LivesController;

/**
 * Bomb implementation.
 */
public class BombImpl extends SliceableModelImpl {

    private final LivesController livesController;
    private boolean immunity;

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
        this.immunity = false;
    }

    /**
     * {@inheritdoc}.
     */
    @Override
    public void cut() {
        if (!this.immunity) {
            this.livesController.decreaseLives(1);
        }
    }

    /**
     * Set the bomb immunity.
     * @param immunity if the player is immune
     */
    public void setImmunity(final boolean immunity) {
        this.immunity = immunity;
    }

}
