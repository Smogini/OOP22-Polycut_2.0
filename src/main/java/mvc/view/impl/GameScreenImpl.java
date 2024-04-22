package mvc.view.impl;

import mvc.controller.BladeController;
import mvc.controller.LivesController;
import mvc.controller.ScoreController;
import mvc.view.GameScreen;
import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;

/**
 * GameScreen class, it represents the PlayButton generated GUI.
 */
public class GameScreenImpl implements GameScreen {

    private final LiveImpl livesLabel;
    private final ScoreViewImpl scoreLabel;
    private final TimerViewImpl timerPanel;
    private final JFrame frame;
    private final Dimension screenSize;

    /**
     * GameScreen constructor.
     * 
     * @param livesController
     * @param scoreController
     * @param timerView
     */
    @SuppressFBWarnings
    public GameScreenImpl(final LivesController livesController, final ScoreController scoreController,
                          final TimerViewImpl timerView) {
        frame = new JFrame("Polygon Cutter");
        this.screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.livesLabel = livesController.getLiveInstance();
        this.scoreLabel = scoreController.getScoreInstance();
        this.timerPanel = timerView;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameAreaImpl createAndShowGui(final BladeController bladeController) {
        // Configuration of the Frame Behavior
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        frame.setResizable(true);

        // Adding control to close the frame
        frame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(final KeyEvent e) {

            }

            @Override
            public void keyPressed(final KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    frame.dispose();
                    Runtime.getRuntime().exit(0);
                }
            }

            @Override
            public void keyReleased(final KeyEvent e) {

            }
        });

        //Background
        final var path = LiveImpl.getDeafultPath() + "background.jpg";
        final URL url = GameScreenImpl.class.getClassLoader().getResource(path);
        if (url != null) {
            frame.setContentPane(new JLabel(new ImageIcon(url)));
        }

        // Setting the main Layout of the Frame
        frame.setLayout(new BorderLayout());

        // Setting-up Lives and Score
        final var upperPanel = new JPanel();
        upperPanel.setLayout(new BorderLayout());
        upperPanel.setOpaque(false);
        frame.add(upperPanel, BorderLayout.NORTH);

        // Lives on the left
        upperPanel.add(livesLabel, BorderLayout.WEST);

        upperPanel.add(timerPanel, BorderLayout.CENTER);
        // this.frame.add(timerPanel, BorderLayout.WEST);

        // Score on the right
        upperPanel.add(scoreLabel, BorderLayout.EAST);

        //Adding the GameArea where Sliceable will be drawn
        final var middleArea = new GameAreaImpl(bladeController);
        frame.add(middleArea, BorderLayout.CENTER);
        middleArea.setOpaque(false);

        frame.setVisible(true);

        return middleArea;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getScoreValue() {
        return this.scoreLabel.getScore();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void gameOverPanel() {
        JOptionPane.showMessageDialog(
                this.frame, "Game Over!\nPunteggio: " + this.scoreLabel.getScore()
                        + "\nRecord: " + this.getCurrentBestScore());
        this.frame.dispose();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void setNewBestScore(final int record) {
        if (record < scoreLabel.getScore()) {
            try (BufferedWriter recordWriter = new BufferedWriter(
                    new OutputStreamWriter(new FileOutputStream("record.txt"), StandardCharsets.UTF_8))) {
                recordWriter.write(Integer.toString(scoreLabel.getScore()));
            } catch (IOException e) {
                return;
            }
        }
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public int getCurrentBestScore() {
        try (BufferedReader recordReader = new BufferedReader(
                new InputStreamReader(new FileInputStream("record.txt"), StandardCharsets.UTF_8))) {
            return Integer.parseInt(recordReader.readLine());
        } catch (IOException | NumberFormatException e) {
            return 0;
        }
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public int getScreenHeight() {
        return this.screenSize.height;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public int getScreenWidth() {
        return this.screenSize.width;
    }

}
