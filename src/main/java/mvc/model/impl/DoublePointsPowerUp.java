package mvc.model.impl;

import java.awt.geom.Point2D;

import mvc.controller.BladeController;
import mvc.model.GameObjectEnum;

/**
 * Implementation class of the double points power up.
 */
public class DoublePointsPowerUp extends AbstractPowerUp {

    /**
     * Constructor of a double points power up.
     *
     * @param nsides number of sides of the sliceable polygon.
     * @param position Point2D position of the sliceable, to update every timestep.
     * @param velocity Point2D vector of the new velocity of the object.
     * @param sliceableId the sliceable identifier.
     * @param bladeController
     */
    public DoublePointsPowerUp(final Integer nsides, final Point2D position,
                               final Point2D velocity, final int sliceableId,
                               final BladeController bladeController) {
        super(nsides, position, velocity, sliceableId, bladeController);
    }

    /**
     * {@inheritdoc}.
     */
    @Override
    public void applyPowerUp() {
        super.applyPowerUp();
    }

    /**
     * {@inheritdoc}.
     */
    @Override
    public GameObjectEnum getPowerUpType() {
        return GameObjectEnum.DOUBLE_POINTS;
    }

}
