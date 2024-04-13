package mvc.model.impl;

import java.awt.geom.Point2D;

import mvc.controller.ScoreController;
import mvc.model.GameObjectEnum;

/**
 * Implementation class of the double points power up.
 */
public class DoublePointsPowerUp extends AbstractPowerUp {

    private final ScoreController scoreController;

    /**
     * Constructor of a double points power up.
     *
     * @param nsides number of sides of the sliceable polygon.
     * @param position Point2D position of the sliceable, to update every timestep.
     * @param velocity Point2D vector of the new velocity of the object.
     * @param sliceableId the sliceable identifier.
     * @param scoreController
     */
    public DoublePointsPowerUp(final Integer nsides, final Point2D position,
                               final Point2D velocity, final int sliceableId,
                               final ScoreController scoreController) {
        super(nsides, position, velocity, sliceableId, scoreController);
        this.scoreController = scoreController;
    }

    /**
     * {@inheritdoc}.
     */
    @Override
    public GameObjectEnum getPowerUpType() {
        return GameObjectEnum.DOUBLE_POINTS;
    }

    /**
     * {@inheritdoc}.
     */
    @Override
    public void cut() {
        this.scoreController.increaseScore(2);
    }

}
