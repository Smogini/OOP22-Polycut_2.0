package mvc.controller.impl;

import java.util.Map;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import mvc.controller.BladeController;
import mvc.controller.TimerController;
import mvc.model.GameObjectEnum;
import mvc.model.SliceableModel;
import mvc.view.impl.TimerViewImpl;

/**
 * Blade controller implementation. Check the relative interface for the documentation.
 */
public class BladeControllerImpl implements BladeController {

    private final TimerViewImpl timerView;
    private final Map<GameObjectEnum, List<TimerController>> powerUpTimers;
    private boolean isBombImmunity;
    private boolean isFrozen;

    /**
     * BladeController's constructor.
     * @param timerView
     */
    @SuppressFBWarnings
    public BladeControllerImpl(final TimerViewImpl timerView) {
        this.timerView = timerView;
        this.powerUpTimers = new HashMap<>();
    }

    /**
     * {@inheritdoc}.
     */
    @Override
    public void cutSliceable(final SliceableModel sliceable) {
        sliceable.cut();
    }

    /**
     * {@inheritdoc}.
     */
    @Override
    public void setBombImmunity(final boolean immunity) {
        this.isBombImmunity = immunity;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public boolean isBombImmunity() {
        return this.isBombImmunity;
    }

    /**
     * {@inheritdoc}.
     */
    @Override
    public void startPowerUpTimer(final GameObjectEnum powerUpType) {
        final List<TimerController> timerControllerList = this.powerUpTimers.get(powerUpType);
        if (timerControllerList != null && !timerControllerList.isEmpty()) {
            final TimerController timerController = timerControllerList.get(timerControllerList.size() - 1);
            timerController.stopTimer();
            timerController.initializeTimer(powerUpType);
            timerController.startTimer();
        }
    }

    /**
     * {@inheritdoc}.
     */
    @Override
    public void insertPowerUp(final GameObjectEnum powerUpType, final int duration) {
        if (this.powerUpTimers.containsKey(powerUpType)) {
            removePowerUp(powerUpType);
        }
        final TimerController timerController = new TimerControllerImpl(this.timerView, this);
        timerController.setPowerUpDuration(duration);
        setPowerUpStatus(powerUpType, true);
        this.powerUpTimers.computeIfAbsent(powerUpType, l -> new ArrayList<>()).add(timerController);
    }

    /**
     * {@inheritdoc}.
     */
    @Override
    public void setPowerUpStatus(final GameObjectEnum powerUpType, final boolean enable) {
        switch (powerUpType) {
            case BOMB_IMMUNITY:
                this.isBombImmunity = enable;
                break;
            case FREEZE:
                this.isFrozen = enable;
                break;
            default:
                break;
        }
    }

    /**
     * Removes the specifiec powerUp from the Map, stopping the associated timer.
     * @param powerUpType
     */
    private void removePowerUp(final GameObjectEnum powerUpType) {
        final List<TimerController> timerControllerList = this.powerUpTimers.get(powerUpType);
        if (timerControllerList.isEmpty()) {
            return;
        }
        final TimerController timerController = timerControllerList.get(timerControllerList.size() - 1);
        timerController.stopTimer();
        this.powerUpTimers.get(powerUpType).clear();
    }

    /**
     * {@inheritdoc}.
     */
    @Override
    public boolean isFrozen() {
        return this.isFrozen;
    }

}
