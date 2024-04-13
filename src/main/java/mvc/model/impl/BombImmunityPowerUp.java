package mvc.model.impl;

import java.awt.geom.Point2D;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import mvc.controller.GameWorldController;
import mvc.controller.ScoreController;
import mvc.model.GameObjectEnum;

/**
 * Implementation class of the bomb immunity power up.
 */
public class BombImmunityPowerUp extends AbstractPowerUp {

    private final GameWorldController gameController;

    /**
     * Constructor of a bomb immunity power up.
     * 
     * @param nsides number of sides of the sliceable polygon.
     * @param position Point2D position of the sliceable, to update every timestep.
     * @param velocity Point2D vector of the new velocity of the object.
     * @param sliceableId the sliceable identifier.
     * @param scoreController
     * @param gameController
     */
    @SuppressFBWarnings
    public BombImmunityPowerUp(final Integer nsides, final Point2D position, final Point2D velocity,
                               final Integer sliceableId, final ScoreController scoreController,
                               final GameWorldController gameController) {
        super(nsides, position, velocity, sliceableId, scoreController);
        this.gameController = gameController;
    }

    /**
     * {@inheritdoc}.
     */
    @Override
    public GameObjectEnum getPowerUpType() {
        return GameObjectEnum.BOMB_IMMUNITY;
    }

    /**
     * {@inheritdoc}.
     */
    @Override
    public void cut() {
        this.gameController.setBombImmunity(true);
        super.cut();
    }

}
