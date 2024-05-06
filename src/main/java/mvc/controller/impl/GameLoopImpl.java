package mvc.controller.impl;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import mvc.App;
import mvc.controller.BladeController;
import mvc.controller.GameLoop;
import mvc.controller.LivesController;
import mvc.model.GameObjectEnum;
import mvc.model.PowerUpModel;
import mvc.model.SliceableModel;
import mvc.model.impl.PolygonImpl;
import mvc.view.impl.GameScreenImpl;
import mvc.view.impl.LiveImpl;
import mvc.view.GameArea;

import javax.swing.Timer;

import java.util.Random;
import java.util.List;
import java.util.Arrays;

/**
 *Implementation class of GameLoop interface.
 *Check the relative interface for the documentation.
 */
public class GameLoopImpl implements GameLoop {

    private static final Integer INITIAL_SPAWN_TIME = 3000;
    private static final Double DT = 0.2;
    private static final Integer REDRAW_DELAY = 10;
    private static final Double SLICEABLE_PERCENTAGE = 0.8;
    private static final Double POWERUP_PERCENTAGE = 0.1;
    private static final Integer DECREASE_50 = 50;
    private static final Integer DECREASE_100 = 100;
    private static final Integer DECREASE_150 = 150;
    private static final Integer HALF_SEC = 500;
    private static final Integer N_POINTS = 5;

    private Integer decreaseFactor = 0;
    private Integer spawnTime;
    private Integer lastScore = 0;

    private final GameWorldControllerImpl world;
    private final PhysicControllerImpl physics;
    private final GameScreenImpl screen;
    private final LiveImpl lives;
    private final BladeController bladeController;

    private final Timer gameTimer;
    private final Timer redrawTimer;
    private final Random rand = new Random();

    /**
     * Constructor.
     * @param world game world controller.
     * @param livesController lives controller.
     * @param screen the GameScreen.
     * @param bladeController
     */
    @SuppressFBWarnings
    public GameLoopImpl(final GameWorldControllerImpl world, final LivesController livesController, final GameScreenImpl screen,
                        final BladeController bladeController) {
        this.lives = livesController.getLiveInstance();
        this.screen = screen;
        this.world = world;
        this.bladeController = bladeController;
        this.spawnTime = INITIAL_SPAWN_TIME;
        this.physics = new PhysicControllerImpl(DT, world);
        final GameArea area = screen.createAndShowGui(bladeController);

        if (area != null) {
            this.gameTimer = new Timer(spawnTime, e -> this.loop(area));
            this.redrawTimer = new Timer(REDRAW_DELAY, e -> this.redraw(area));
            redrawTimer.setRepeats(true);
            gameTimer.setRepeats(true);
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
        if (this.bladeController.isPowerUpEnabled(GameObjectEnum.FREEZE)) {
            this.redrawTimer.stop();
            return;
        } else if (this.bladeController.isPowerUpEnabled(GameObjectEnum.INCREASE_SPEED)) {
            this.restartGameTimer(spawnTime / 2);
        }
        if (!this.redrawTimer.isRunning()) {
            this.redrawTimer.start();
        }
        final double choice = this.rand.nextDouble();
        final int id = this.rand.nextInt();
        this.incrementDifficulty();
        final SliceableModel sliceable = choice <= SLICEABLE_PERCENTAGE && choice > POWERUP_PERCENTAGE
                                        ? this.world.createPolygon(id) : choice <= POWERUP_PERCENTAGE
                                        ? this.world.createPowerUp(id) : this.world.createBomb(id);
        area.drawSliceable(sliceable, GameObjectEnum.getSliceableType(sliceable.getSides()));
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
                                    if (sliceable instanceof PolygonImpl
                                        && !causesLifeLoss(sliceable, GameObjectEnum.LOSE_POINTS)) {
                                        this.lives.decreaseLives(1);
                                    }
                                });
        this.world.getSliceables()
                    .forEach(s -> area.updatePosition(s.getSliceableId(), s.getPosition()));
    }

    /**
     * @param sliceable
     * @param powerUpType
     * @return true if the sliceable is a power up and causes to the player to lose a life, false otherwise.
     */
    private boolean causesLifeLoss(final SliceableModel sliceable, final GameObjectEnum... powerUpType) {
        final GameObjectEnum sliceableAsGameObject = GameObjectEnum.getSliceableType(sliceable.getSides());
        final List<GameObjectEnum> list = Arrays.asList(powerUpType);
        return sliceable instanceof PowerUpModel
            && !list.contains(sliceableAsGameObject);
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
     * Restart the game timer with the specified delay.
     * @param delay
     */
    private void restartGameTimer(final int delay) {
        gameTimer.stop();
        gameTimer.setDelay(delay);
        gameTimer.restart();
    }

    /**
     * Increment the difficulty every n points.
     */
    private void incrementDifficulty() {

        if (this.bladeController.isPowerUpEnabled(GameObjectEnum.INCREASE_SPEED)) {
            return;
        }

        if (spawnTime - decreaseFactor <= HALF_SEC) {
            spawnTime = HALF_SEC;
            restartGameTimer(spawnTime);
            return;
        }

        final int score = screen.getScoreValue();

        if (score % N_POINTS == 0 && score != lastScore) {
            spawnTime -= decreaseFactor;
            this.lastScore = score;
            restartGameTimer(spawnTime);
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
                this.decreaseFactor = DECREASE_50;
        }
    }

    /**
     * {@inheritdoc}.
     */
    @Override
    public void startTimers() {
        this.gameTimer.start();
        this.redrawTimer.start();
    }

}
