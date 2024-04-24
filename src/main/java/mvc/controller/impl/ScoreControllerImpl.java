package mvc.controller.impl;

import mvc.controller.ScoreController;
import mvc.view.impl.ScoreViewImpl;

/**
 * Score controller implementation. Check the relative interface for the documentation.
 */
public class ScoreControllerImpl implements ScoreController {

    private final ScoreViewImpl score;

    /**
     * ScoreController constructor.
     */
    public ScoreControllerImpl() {
        this.score = new ScoreViewImpl();
    }

    /**
     * {@inheritdoc}.
     */
    @Override
    public int getScore() {
        return this.score.getScore();
    }

    /**
     * {@inheritdoc}.
     */
    @Override
    public void increaseScore(final int points) {
        this.score.increaseScore(points);
    }

    /**
     * {@inheritdoc}.
     */
    @Override
    public void decreaseScore(final int points) {
        this.score.decreaseScore(points);
    }

    /**
     * {@inheritdoc}.
     */
    @Override
    public ScoreViewImpl getScoreInstance() {
        return this.score.getCurrScoreImpl();
    }

}
