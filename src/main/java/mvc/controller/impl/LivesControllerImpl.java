package mvc.controller.impl;

import mvc.controller.LivesController;
import mvc.view.impl.LiveImpl;

/**
 * Lives controller implementation. Check the relative interface for the documentation.
 */
public class LivesControllerImpl implements LivesController {

    private final LiveImpl lives;

    /**
     * LivesControllerImpl constructor's.
     */
    public LivesControllerImpl() {
        this.lives = new LiveImpl();
    }

    /**
     * {@inheritdoc}.
     */
    @Override
    public void decreaseLives(final int lives) {
        this.lives.decreaseLives(lives);
    }

    /**
     * {@inheritdoc}.
     */
    @Override
    public int getCurrentLives() {
        return this.lives.getLivesCounter();
    }

    /**
     * {@inheritdoc}.
     */
    @Override
    public LiveImpl getLiveInstance() {
        return this.lives.getCurrLiveImpl();
    }

}
