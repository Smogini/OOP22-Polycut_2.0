package mvc.controller.impl;

import javax.swing.Timer;
import javax.swing.JLabel;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import mvc.controller.BladeController;
import mvc.controller.TimerController;
import mvc.model.GameObjectEnum;
import mvc.model.TimerModel;
import mvc.model.impl.TimerModelImpl;
import mvc.view.impl.TimerViewImpl;

/**
 * Implementation class. Check TimerController interface for documentation.
 */
public class TimerControllerImpl implements TimerController {

    private static final int TIMER_DELAY = 1000;

    private final TimerModel timerModel;
    private final TimerViewImpl timerView;
    private final BladeController bladeController;
    private Timer timer;
    private int duration;

    /**
     * TimerController's constructor.
     * @param timerView
     * @param bladeController
     */
    @SuppressFBWarnings
    public TimerControllerImpl(final TimerViewImpl timerView, final BladeController bladeController) {
        this.timerModel = new TimerModelImpl();
        this.timerView = timerView;
        this.bladeController = bladeController;
        this.timer = new Timer(0, null);
    }

    /**
     * {@inheritdoc}.
     */
    @Override
    public void initializeTimer(final GameObjectEnum powerUpType) {
        final JLabel timerLabel = new JLabel();
        this.timerView.addLabel(timerLabel, powerUpType);
        this.timer = new Timer(TIMER_DELAY, e -> {
            int remainingTime = timerModel.getRemainigTime();
            remainingTime--;
            if (remainingTime <= 0) {
                stopTimer();
                this.bladeController.setPowerUpStatus(powerUpType, false);
                timerView.removeLabel(timerLabel, powerUpType);
                timerView.revalidate();
                timerView.repaint();
            }
            timerModel.setTimerDuration(remainingTime);
            timerView.updateTimerLabel(timerLabel, remainingTime, powerUpType.toString());
        });
    }

    /**
     * {@inheritdoc}.
     */
    @Override
    public void startTimer() {
        this.timerModel.setTimerDuration(duration);
        this.timer.restart();
    }

    /**
     * {@inheritdoc}.
     */
    @Override
    public void stopTimer() {
        this.timer.stop();
    }

    /**
     * {@inheritdoc}.
     */
    @Override
    public void setPowerUpDuration(final int duration) {
        this.duration = duration;
    }

}
