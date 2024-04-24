package mvc.view.impl;

import mvc.view.ScoreView;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import java.awt.Font;
import java.io.IOException;
import java.awt.FontFormatException;
import java.io.InputStream;

/**
 * Score class. Count the score of the game.
 */
public final class ScoreViewImpl extends JLabel implements ScoreView {

    private static final double serialVersionUID = 0L;
    private static final int FONT_SIZE = 35;
    private static final int RIGHT_GAP = 15;

    private int currentScore;

    /**
     * Constructor sets up the font and the default score of 0.
     */
    public ScoreViewImpl() {
        this.currentScore = 0;
        this.setText("Score: " + currentScore);
        this.setFont(getPersonalizedFont());
        this.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, RIGHT_GAP));
    }

    /**
     * @return the font specified in the method.
     */
    public static Font getPersonalizedFont() {
        try (InputStream fontInputStream =
                ScoreViewImpl.class.getResourceAsStream("/GraphicElements/Orbitron/Orbitron-VariableFont_wght.ttf")) {
            if (fontInputStream == null) {
                throw new IllegalStateException("Font file not found.");
            }

            return Font.createFont(Font.TRUETYPE_FONT, fontInputStream).deriveFont(Font.PLAIN, FONT_SIZE);
        } catch (FontFormatException | IOException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * Score getter.
     * @return current score.
     */
    @Override
    public int getScore() {
        return currentScore;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void increaseScore(final int points) {
        currentScore += points;
        this.drawScore();
    }

    /**
     * {@inheritodc}.
     */
    @Override
    public void decreaseScore(final int points) {
        if (this.currentScore - points < 0) {
            this.currentScore = 0;
        } else {
            this.currentScore -= points;
        }
        this.drawScore();
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void drawScore() {
        this.setText("Score: " + currentScore);
    }

    /**
     * {@inheritdoc}.
     */
    @Override
    public ScoreViewImpl getCurrScoreImpl() {
        return this;
    }
}
