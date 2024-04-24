package mvc.model.impl;

import java.awt.geom.Point2D;

import mvc.controller.ScoreController;

/**
 * Polygon Implementation.
 */
public class PolygonImpl extends SliceableModelImpl {

    private final ScoreController scoreController;

    private boolean doubleScore;

    /**
     * Constructor of a polygon.
     * 
     * @param nsides number of sides of the sliceable polygon.
     * @param position Point2D position of the sliceable, to update every timestep.
     * @param velocity Point2D vector of the new velocity of the object.
     * @param sliceableId the sliceable identifier.
     * @param scoreController
     */
    public PolygonImpl(final Integer nsides, final Point2D position,
                       final Point2D velocity, final int sliceableId,
                       final ScoreController scoreController) {
        super(nsides, position, velocity, sliceableId);
        this.scoreController = scoreController;
    }

    /**
     * Sets true if the double score power up is active, false otherwise.
     * @param enable
     */
    public void setDoubleScore(final boolean enable) {
        this.doubleScore = enable;
    }
    /**
     * {@inheritdoc}.
     */
    @Override
    public void cut() {
        if (this.doubleScore) {
            this.scoreController.increaseScore(2);
        } else {
            this.scoreController.increaseScore(1);
        }
    }

}
