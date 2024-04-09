package mvc.model.impl;

import java.awt.geom.Point2D;

import mvc.controller.BladeController;
import mvc.model.PowerUpModel;

public abstract class AbstractPowerUp extends SliceableModelImpl implements PowerUpModel {

    // private final BladeController bladeController;

    /**
     * Constructor of a power up.
     * @param nsides number of sides of the sliceable polygon.
     * @param position Point2D position of the sliceable, to update every timestep.
     * @param velocity Point2D vector of the new velocity of the object.
     * @param sliceableId the sliceable identifier.
     * @param bladeController
     */
    public AbstractPowerUp(final Integer nsides, final Point2D position,
                           final Point2D velocity, final Integer sliceableId,
                           final BladeController bladeController) {
        super(nsides, position, velocity, sliceableId);
        // this.bladeController = bladeController;
    }

    /**
     * {@inheritdoc}.
     */
    @Override
    public void applyPowerUp() {
        // this.bladeController.registerPowerUp(this);
    }

}
