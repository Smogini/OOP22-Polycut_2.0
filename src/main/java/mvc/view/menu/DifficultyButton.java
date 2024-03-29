package mvc.view.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import mvc.view.Menu;

/**
 * Creates the difficulty button and adds it to the menu.
 */
public class DifficultyButton extends MenuDecorator {

    /**
     * PlayButton constructor.
     * @param simpleMenu
     */
    public DifficultyButton(final Menu simpleMenu) {
        super(simpleMenu);
    }

    /**
     * {@inheritdoc}.
     */
    @Override
    public void display(final JFrame container, final JPanel buttonPanel) {
        final JButton difficultyButton = new JButton("Difficulty");
        difficultyButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent e) {
                final String[] options = {"Easy", "Medium", "Hard"};
                final int difficultyChoice = JOptionPane.showOptionDialog(
                    container,
                    "Select the difficulty",
                    "",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]);
                setDifficulty(difficultyChoice);
            }

        });
        decorateButton(difficultyButton);
        buttonPanel.add(difficultyButton);
        super.display(container, buttonPanel);
    }
}
