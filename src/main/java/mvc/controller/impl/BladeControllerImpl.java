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
    private final Map<GameObjectEnum, Boolean> powerUpEnabled;

    /**
     * BladeController's constructor.
     * @param timerView
     */
    @SuppressFBWarnings
    public BladeControllerImpl(final TimerViewImpl timerView) {
        this.timerView = timerView;
        this.powerUpTimers = new HashMap<>();
        this.powerUpEnabled = new HashMap<>(Map.of(
                GameObjectEnum.BOMB_IMMUNITY, false,
                GameObjectEnum.FREEZE, false,
                GameObjectEnum.INCREASE_SPEED, false,
                GameObjectEnum.DOUBLE_SCORE, false
            ));
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
     * Inserts the specified power up in the Map, initializing the TimerController for it.
     * @param powerUpType
     * @param duration
     */
    private void insertPowerUp(final GameObjectEnum powerUpType, final int duration) {
        if (this.powerUpTimers.containsKey(powerUpType)) {
            removePowerUp(powerUpType);
        }
        final TimerController timerController = new TimerControllerImpl(this.timerView, this);
        timerController.setPowerUpDuration(duration);
        this.powerUpTimers.computeIfAbsent(powerUpType, l -> new ArrayList<>()).add(timerController);
    }

    /**
     * {@inheritdoc}.
     */
    @Override
    public void setPowerUpStatus(final GameObjectEnum powerUpType, final int duration, final boolean enable) {
        if (duration > 0) {
            insertPowerUp(powerUpType, duration);
        }
        this.powerUpEnabled.replace(powerUpType, enable);
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
     * {@inheritodc}.
     */
    @Override
    public Map<GameObjectEnum, Boolean> getEnabledPowerUp() {
        return new HashMap<>(this.powerUpEnabled);
    }

    /**
     * {@inheritdoc}.
     */
    @Override
    public boolean isPowerUpEnabled(final GameObjectEnum powerUpType) {
        return this.powerUpEnabled.get(powerUpType);
    }

}
