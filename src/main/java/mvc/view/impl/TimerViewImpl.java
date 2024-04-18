package mvc.view.impl;

import javax.swing.JLabel;

import mvc.view.TimerView;

/**
 * Implementation class. Check TimerView interface for documentation.
 */
public class TimerViewImpl extends JLabel implements TimerView {

    private static final double serialVersionUID = 0L;

    /**
     * {@inheritdoc}.
     */
    @Override
    public void updateTimerLabel(final int duration) {
        this.setVisible(true);
        this.setText("Remaining " + duration + "s");
    }

    /**
     * {@inheritdoc}.
     */
    @Override
    public void hideTimerLabel() {
        this.setVisible(false);
    }

}
