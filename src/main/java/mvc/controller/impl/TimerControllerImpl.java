package mvc.controller.impl;

import javax.swing.Timer;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import mvc.controller.TimerController;
import mvc.model.TimerModel;
import mvc.view.TimerView;

/**
 * Implementation class. Check TimerController interface for documentation.
 */
public class TimerControllerImpl implements TimerController {

    private static final int TIMER_DELAY = 1000;

    private final TimerModel timerModel;
    private final TimerView timerView;
    private final Timer timer;

    /**
     * TimerController's constructor.
     * @param timerModel
     * @param timerView
     */
    @SuppressFBWarnings
    public TimerControllerImpl(final TimerModel timerModel, final TimerView timerView) {
        this.timerModel = timerModel;
        this.timerView = timerView;
        this.timer = new Timer(TIMER_DELAY, e -> {
            int remainingTime = timerModel.getRemainigTime();
            remainingTime--;
            if (remainingTime <= 0) {
                stopTimer();
            }
            timerModel.setTimerDuration(remainingTime);
            timerView.updateTimerLabel(remainingTime);
        });
    }

    /**
     * {@inheritdoc}.
     */
    @Override
    public void startTImer(final int duration) {
        this.timerModel.setTimerDuration(duration);
        this.timer.restart();
    }

    /**
     * {@inheritdoc}.
     */
    @Override
    public void stopTimer() {
        this.timerView.hideTimerLabel();
        this.timer.stop();
    }

}
