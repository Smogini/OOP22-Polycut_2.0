package mvc.model.impl;

import java.awt.geom.Point2D;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import mvc.controller.BladeController;
import mvc.controller.ScoreController;
import mvc.model.PowerUpModel;

/**
 * Abstract implementation of PowerUpModel, pre-defines the cut method for all classes that extend it.
 */
public abstract class AbstractPowerUp extends SliceableModelImpl implements PowerUpModel {

    private final ScoreController scoreController;
    private final BladeController bladeController;

    /**
     * Constructor of a power up.
     * @param nsides number of sides of the sliceable polygon.
     * @param position Point2D position of the sliceable, to update every timestep.
     * @param velocity Point2D vector of the new velocity of the object.
     * @param sliceableId the sliceable identifier.
     * @param scoreController
     * @param bladeController
     */
    @SuppressFBWarnings
    public AbstractPowerUp(final Integer nsides, final Point2D position,
                           final Point2D velocity, final Integer sliceableId,
                           final ScoreController scoreController, final BladeController bladeController) {
        super(nsides, position, velocity, sliceableId);
        this.scoreController = scoreController;
        this.bladeController = bladeController;
    }

    /**
     * {@inheritdoc}.
     */
    @Override
    public void cut() {
        this.scoreController.increaseScore(1);
        this.bladeController.startPowerUpTimer(getPowerUpType());
    }

}
