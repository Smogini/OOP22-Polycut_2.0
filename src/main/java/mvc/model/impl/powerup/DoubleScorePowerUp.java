package mvc.model.impl.powerup;

import java.awt.geom.Point2D;

import mvc.controller.BladeController;
import mvc.controller.ScoreController;
import mvc.model.GameObjectEnum;
import mvc.model.impl.AbstractPowerUp;

/**
 * Implementation class of the double score power up.
 */
public class DoubleScorePowerUp extends AbstractPowerUp {

    private static final int DOUBLE_SCORE_TIME = 10;

    /**
     * Constructor of a double score power up.
     *
     * @param nsides number of sides of the sliceable polygon.
     * @param position Point2D position of the sliceable, to update every timestep.
     * @param velocity Point2D vector of the new velocity of the object.
     * @param sliceableId the sliceable identifier.
     * @param scoreController
     * @param bladeController
     */
    public DoubleScorePowerUp(final Integer nsides, final Point2D position, final Point2D velocity, final Integer sliceableId,
                            final ScoreController scoreController, final BladeController bladeController) {
        super(nsides, position, velocity, sliceableId, DOUBLE_SCORE_TIME, scoreController, bladeController);
    }

    /**
     * {@inheritdoc}.
     */
    @Override
    public GameObjectEnum getPowerUpType() {
        return GameObjectEnum.DOUBLE_SCORE;
    }

}
