package mvc.controller.impl;

import mvc.controller.BladeController;
import mvc.controller.TimerController;
import mvc.model.SliceableModel;
import mvc.model.impl.TimerModelImpl;
import mvc.view.impl.TimerViewImpl;

/**
 * Blade controller implementation. Check the relative interface for the documentation.
 */
public class BladeControllerImpl implements BladeController {

    private static final int IMMUNITY_TIME = 10;

    private final TimerController powerUpTimer;
    private boolean isBombImmunity;

    /**
     * BladeController's constructor.
     * @param timerView
     */
    public BladeControllerImpl(final TimerViewImpl timerView) {
        this.powerUpTimer = new TimerControllerImpl(new TimerModelImpl(), timerView);
    }

    /**
     * {@inheritdoc}.
     */
    @Override
    public void cutSliceable(final SliceableModel sliceable) {
        sliceable.cut();
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void setBombImmunity(final boolean immunity) {
        if (immunity) {
            this.powerUpTimer.stopTimer();
            this.powerUpTimer.startTImer(IMMUNITY_TIME);
        }
        this.isBombImmunity = immunity;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public boolean isBombImmunity() {
        return this.isBombImmunity;
    }

}
