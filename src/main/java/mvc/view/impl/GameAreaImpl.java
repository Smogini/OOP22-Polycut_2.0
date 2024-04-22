package mvc.view.impl;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import mvc.controller.BladeController;
import mvc.model.GameObjectEnum;
import mvc.model.SliceableModel;
import mvc.model.impl.BombImpl;
import mvc.view.GameArea;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * GameArea class, it represents the playable area.
 */
public class GameAreaImpl extends JPanel implements GameArea {

    private static final long serialVersionUID = 0L;

    private final BladeController bladeController;
    private final Map<Integer, JLabel> labelMap = new HashMap<>();
    private final List<Integer> sliceablesID;

    /**
     * Constructor initiates a list of Bombs and Polygon present in the GameArea.
     * 
     * @param bladeController
     */
    @SuppressFBWarnings
    public GameAreaImpl(final BladeController bladeController) {
        this.bladeController = bladeController;
        this.sliceablesID = new ArrayList<>();
        this.setLayout(null);
    }

    /**
     * {@inheritdoc}.
     */
    @Override
    public void drawSliceable(final SliceableModel sliceable, final GameObjectEnum type) {
        final int sliceableId = sliceable.getSliceableId();
        final int sliceableHeight = SliceableView.SLICEABLE_SIZE;
        final int sliceableWidth = SliceableView.SLICEABLE_SIZE;
        final int posX = (int) sliceable.getPosition().getX();
        final int posY = (int) sliceable.getPosition().getY();
        final ImageIcon image = new ImageIcon(SliceableView.class.getResource(type.getImagePath()));

        final JLabel newSliceableLabel = new JLabel(image);
        newSliceableLabel.setBounds(posX, posY, sliceableWidth, sliceableHeight);
        newSliceableLabel.setEnabled(true);
        newSliceableLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(final MouseEvent e) {
                if (sliceable instanceof BombImpl) {
                    playSound("Audio/bomb.wav");
                } else {
                    playSound("Audio/swoosh.wav");
                }
                bladeController.cutSliceable(sliceable);
                sliceablesID.add(sliceableId);
                clean(sliceableId);
                revalidate();
                repaint();
            }
        });
        labelMap.put(sliceableId, newSliceableLabel);
        this.add(newSliceableLabel);
    }

    /**
     * Plays the audio track specified by filePath.
     * @param filePath
     */
    public static void playSound(final String filePath) {
            final InputStream soundStream = GameAreaImpl.class.getClassLoader().getResourceAsStream(filePath);
            final AudioInputStream audioInputStream;
            final Clip clip;
            try {
                audioInputStream = AudioSystem.getAudioInputStream(soundStream);
                clip = AudioSystem.getClip();
                clip.addLineListener(event -> {
                    if (event.getType() == LineEvent.Type.STOP) {
                        clip.close();
                        try {
                            audioInputStream.close();
                        } catch (final IOException e) {
                            return;
                        }
                    }
                });
                clip.open(audioInputStream);
                clip.start();
            } catch (final UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                return;
            }
    }

    /**
     * {@inheritdoc}.
     */
    @Override
    public void updatePosition(final Integer sliceableID, final Point2D newPosition) {
        final int sliceableHeight = SliceableView.SLICEABLE_SIZE;
        final int sliceableWidth = SliceableView.SLICEABLE_SIZE;

        labelMap.computeIfPresent(sliceableID, (key, label) -> {
            label.setBounds((int) newPosition.getX(), (int) newPosition.getY(), sliceableWidth, sliceableHeight);
            return label;
        });
        /* Repaint the game area, otherwise the labels remain attached to the panel */
        this.revalidate();
        this.repaint();
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void clean(final Integer sliceableID) {
        final var currLabel = labelMap.get(sliceableID);
        this.remove(Objects.requireNonNull(currLabel));
        labelMap.remove(sliceableID);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Integer> getSliced() {
        return new ArrayList<>(this.sliceablesID);
    }
}
