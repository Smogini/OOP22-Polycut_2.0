package mvc.view.impl;

import java.util.Map;
import java.util.HashMap;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.BorderFactory;

import mvc.model.GameObjectEnum;
import mvc.view.TimerView;

/**
 * Implementation class. Check TimerView interface for documentation.
 */
public class TimerViewImpl extends JPanel implements TimerView {

    private static final double serialVersionUID = 0L;

    private final Map<GameObjectEnum, JLabel> labelsMap;
    private static final int LEFT_GAP = 300;

    /**
     * TimerView's constructor. Initialize the label shown on screen.
     */
    public TimerViewImpl() {
        this.labelsMap = new HashMap<>();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setOpaque(false);
        this.setBorder(BorderFactory.createEmptyBorder(0, LEFT_GAP, 0, 0));
    }

    /**
     * {@inheritdoc}.
     */
    @Override
    public void updateTimerLabel(final JLabel label, final int remainingTime, final String powerUpType) {
        final var componentsList = this.getComponents();
        for (final var component : componentsList) {
            if (component instanceof JLabel && component.equals(label)) {
                final JLabel specificLabel = (JLabel) component;
                specificLabel.setText(powerUpType + ": " + remainingTime + "s");
                specificLabel.setVisible(remainingTime > 0);
                return;
            }
        }
    }

    /**
     * {@inheritdoc}.
     */
    @Override
    public void addLabel(final JLabel label, final GameObjectEnum powerUpType) {
        if (this.labelsMap.containsKey(powerUpType)) {
            removeLabel(this.labelsMap.get(powerUpType), powerUpType);
        }
        label.setFont(ScoreViewImpl.getPersonalizedFont());
        this.labelsMap.put(powerUpType, label);
        this.add(label);
        this.revalidate();
        this.repaint();
    }

    /**
     * {@inheritdoc}.
     */
    @Override
    public void removeLabel(final JLabel label, final GameObjectEnum powerUpType) {
        this.labelsMap.remove(powerUpType);
        this.remove(label);
        this.revalidate();
        this.repaint();
    }

}
