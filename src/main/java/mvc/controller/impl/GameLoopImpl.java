package mvc.controller.impl;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import mvc.App;
import mvc.controller.GameLoop;
import mvc.controller.LivesController;
import mvc.model.PowerUpModel;
import mvc.model.SliceableModel;
import mvc.model.impl.PolygonImpl;
import mvc.view.impl.GameScreenImpl;
import mvc.view.impl.LiveImpl;
import mvc.view.GameArea;

import javax.swing.Timer;
import java.util.Random;

/**
 *Implementation class of GameLoop interface.
 *Check the relative interface for the documentation.
 */
public class GameLoopImpl implements GameLoop {

    private static final Integer INITIAL_SPAWN_TIME = 3000;
    private static final Double DT = 0.2;
    private static final Integer REDRAW_DELAY = 10;
    private static final Double SLICEABLE_PERCENTAGE = 0.3;
    // private static final Double POWERUP_PERCENTAGE = 0.8;
    private static final Integer DECREASE_50 = 50;
    private static final Integer DECREASE_100 = 100;
    private static final Integer DECREASE_150 = 150;
    private static final Integer HALF_SEC = 500;
    private static final Integer N_POINTS = 10;

    private Integer decreaseFactor = 0;
    private Integer spawnTime;
    private Integer lastScore = 0;

    private final GameWorldControllerImpl world;
    private final PhysicControllerImpl physics;
    private final GameScreenImpl screen;
    private final LiveImpl lives;
    private final Timer gameTimer;
    private final Timer redrawTimer;
    private final Random rand = new Random();

    /**
     * Constructor.
     * @param world game world controller.
     * @param livesController lives controller.
     * @param screen the GameScreen.
     */
    @SuppressFBWarnings
    public GameLoopImpl(final GameWorldControllerImpl world, final LivesController livesController, final GameScreenImpl screen) {
        this.lives = livesController.getLiveInstance();
        this.screen = screen;
        this.world = world;
        this.spawnTime = INITIAL_SPAWN_TIME;
        this.physics = new PhysicControllerImpl(DT, world);
        final GameArea area = screen.createAndShowGui(world.getBladeController());

        if (area != null) {
            this.gameTimer = new Timer(spawnTime, e -> this.loop(area));
            this.redrawTimer = new Timer(REDRAW_DELAY, e -> this.redraw(area));
            redrawTimer.setRepeats(true);
            redrawTimer.start();
            gameTimer.setRepeats(true);
            gameTimer.start();
        } else {
            this.gameTimer = null;
            this.redrawTimer = null;
        }
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void loop(final GameArea area) {
        final double choice = this.rand.nextDouble();
        this.incrementDifficulty();
        final int id = this.rand.nextInt();
        // final SliceableModel sliceable = choice > SLICEABLE_PERCENTAGE && choice < POWERUP_PERCENTAGE ? this.world.createPolygon(id)
        //                                 : choice > POWERUP_PERCENTAGE ? this.world.createPowerUp(id) : this.world.createBomb(id);
        final SliceableModel sliceable = choice > SLICEABLE_PERCENTAGE ? this.world.createPolygon(id)
                                                                       : this.world.createBomb(id);
        area.drawSliceable(sliceable, sliceable.getSides());
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void redraw(final GameArea area) {
        gameOver();
        area.getSliced().stream().forEach(i -> world.outOfBoundDelete(i));
        physics.updateSliceablesPosition();
        this.world.getSliceables().stream()
                                .filter(SliceableModel::isOutOfBound)
                                .findFirst()
                                .ifPresent(sliceable -> {
                                    area.clean(sliceable.getSliceableId());
                                    world.outOfBoundDelete(sliceable.getSliceableId());
                                    if (sliceable instanceof PolygonImpl || sliceable instanceof PowerUpModel) {
                                        this.lives.decreaseLives(1);
                                    }
                                });
        this.world.getSliceables()
                    .forEach(s -> area.updatePosition(s.getSliceableId(), s.getPosition(), s.getSides()));
    }

    /**
     * Control if the game is finished, and starts a new App.
     */
    private void gameOver() {
        if (this.lives.getLivesCounter() > 0) {
            return;
        }
        gameTimer.stop();
        redrawTimer.stop();
        screen.setNewBestScore(screen.getCurrentBestScore());
        screen.gameOverPanel();
        App.initializeGame();
    }

    /**
     * Increment the difficulty every n points.
     */
    private void incrementDifficulty() {

        if (spawnTime - decreaseFactor <= HALF_SEC) {
            spawnTime = HALF_SEC;
            return;
        }

        final int score = screen.getScoreValue();

        if (score % N_POINTS == 0 && score != lastScore) {
            spawnTime -= decreaseFactor;
            this.lastScore = score;
            gameTimer.stop();
            gameTimer.setDelay(spawnTime);
            gameTimer.restart();
        }
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void setDifficulty(final int difficulty) {
        switch (difficulty) {
            case 0:
                this.decreaseFactor = DECREASE_50;
                break;
            case 1:
                this.decreaseFactor = DECREASE_100;
                break;
            case 2:
                this.decreaseFactor = DECREASE_150;
                break;
            default:
                throw new IllegalArgumentException("Incorrect difficulty value!");
        }
    }

}
