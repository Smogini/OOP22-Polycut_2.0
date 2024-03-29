package mvc.view;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * It contains the method for displaying the menu on screen.
 */
public interface Menu {

    /**
     * Displays the menu on screen.
     * @param container
     * @param buttonPanel where the multiple buttons will be contained.
     */
    void display(JFrame container, JPanel buttonPanel);

    /**
     * @return the current difficulty
     */
    int getDifficulty();

    /**
     * Set the difficulty.
     * @param difficulty
     */
    void setDifficulty(int difficulty);
}
