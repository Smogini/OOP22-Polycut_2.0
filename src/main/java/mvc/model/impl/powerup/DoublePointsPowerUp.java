package mvc.model.impl.powerup;

import java.awt.geom.Point2D;

import mvc.controller.BladeController;
import mvc.controller.ScoreController;
import mvc.model.GameObjectEnum;
import mvc.model.impl.AbstractPowerUp;

/**
 * Implementation class of the double points power up.
 */
public class DoublePointsPowerUp extends AbstractPowerUp {

    private static final int INCREASE_FACTOR = 2;

    private final ScoreController scoreController;

    /**
     * Constructor of a double points power up.
     *
     * @param nsides number of sides of the sliceable polygon.
     * @param position Point2D position of the sliceable, to update every timestep.
     * @param velocity Point2D vector of the new velocity of the object.
     * @param sliceableId the sliceable identifier.
     * @param scoreController
     * @param bladeController
     */
    public DoublePointsPowerUp(final Integer nsides, final Point2D position, final Point2D velocity, final Integer sliceableId,
                            final ScoreController scoreController, final BladeController bladeController) {
        super(nsides, position, velocity, sliceableId, 0, scoreController, bladeController);
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
        this.scoreController.increaseScore(INCREASE_FACTOR);
    }

}
