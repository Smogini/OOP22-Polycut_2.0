package mvc.model.impl;

import java.awt.geom.Point2D;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import mvc.controller.BladeController;
import mvc.controller.ScoreController;
import mvc.model.GameObjectEnum;

/**
 * Implementation class of the freeze power up.
 */
public class FreezePowerUp extends AbstractPowerUp {

    private static final int FREEZE_TIME = 5;

    private final BladeController bladeController;

    /**
     * Constructor of a freeze power up.
     * 
     * @param nsides number of sides of the sliceable polygon.
     * @param position Point2D position of the sliceable, to update every timestep.
     * @param velocity Point2D vector of the new velocity of the object.
     * @param sliceableId the sliceable identifier.
     * @param scoreController
     * @param bladeController
     */
    @SuppressFBWarnings
    public FreezePowerUp(final Integer nsides, final Point2D position, final Point2D velocity, final Integer sliceableId,
                        final ScoreController scoreController, final BladeController bladeController) {
        super(nsides, position, velocity, sliceableId, scoreController, bladeController);
        this.bladeController = bladeController;
    }

    /**
     * {@inheritdoc}.
     */
    @Override
    public GameObjectEnum getPowerUpType() {
        return GameObjectEnum.FREEZE;
    }

    /**
     * {@inheritdoc}.
     */
    @Override
    public void cut() {
        this.bladeController.insertPowerUp(getPowerUpType(), FREEZE_TIME);
        super.cut();
    }

}
