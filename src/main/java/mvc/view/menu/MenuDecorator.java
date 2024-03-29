package mvc.view.menu;

import mvc.view.Menu;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 * MenuDecorator class models a simple menu without nothing but the screen, is used for decorate the menu with other objects.
 */
public class MenuDecorator extends SimpleMenu {

    private static final int BUTTON_FONT_SIZE = 25;
    private static final int BUTTON_FONT_STYLE = Font.PLAIN;

    private final Menu simpleMenu;

    /**
     * 
     * @param simpleMenu
     */
    @SuppressFBWarnings
    public MenuDecorator(final Menu simpleMenu) {
        this.simpleMenu = simpleMenu;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void display(final JFrame container, final JPanel buttonPanel) {
        simpleMenu.display(container, buttonPanel);
    }

    /**
     * Decorates the specified button.
     * @param button
     */
    protected void decorateButton(final JButton button) {
        button.setFont(new Font(button.getFont().getName(), BUTTON_FONT_STYLE, BUTTON_FONT_SIZE));
        button.setOpaque(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getDifficulty() {
        return this.simpleMenu.getDifficulty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setDifficulty(final int difficulty) {
        this.simpleMenu.setDifficulty(difficulty);
    }

}
