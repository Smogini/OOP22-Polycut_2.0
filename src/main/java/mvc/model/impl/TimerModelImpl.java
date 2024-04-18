package mvc.model.impl;

import mvc.model.TimerModel;

/**
 * Implementation class. Check TimerModel interface for documentation.
 */
public class TimerModelImpl implements TimerModel {

    private int remainingTime;

    /**
     * {@inheritdoc}.
     */
    @Override
    public void setTimerDuration(final int duration) {
        this.remainingTime = duration;
    }

    /**
     * {@inheritdoc}.
     */
    @Override
    public int getRemainigTime() {
        return this.remainingTime;
    }

}
