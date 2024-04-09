package mvc.model.impl;

import java.awt.geom.Point2D;
import java.util.Random;

import mvc.controller.LivesController;
import mvc.controller.ScoreController;
import mvc.model.PowerUpModel;
import mvc.model.SliceableFactory;

/**
 * {@inheritDoc}.
 */
public class SliceableFactoryImpl implements SliceableFactory {

    private static final Random RANDOM = new Random();
    private static final Integer MAX_SIDES = 4;
    private static final Integer BOMB_SIDES = 0;
    private static final double MIN_X_VELOCITY = 30.0;
    private static final double MIN_Y_VELOCITY = 85.0;
    private static final double INC_X_RATE = 10.0;
    private static final double INC_Y_RATE = 40.0;

    private final LivesController livesController;
    private final ScoreController scoreController;

    private Point2D startPositionNext;
    private Point2D startVelocityNext;
    private final Integer screenWidth;
    private final Integer screenHeight;
    private final Integer difficulty;

    /**
     * 
     * @param width
     * @param height
     * @param difficulty
     * @param livesController
     * @param scoreController
     */
    public SliceableFactoryImpl(final Integer width, final Integer height, final int difficulty,
                                final LivesController livesController, final ScoreController scoreController) {
        this.screenWidth = width;
        this.screenHeight = height;
        this.difficulty = difficulty;
        this.livesController = livesController;
        this.scoreController = scoreController;
    }

    private double calcRandomX() {
        double randX = 0.0;
        switch (this.difficulty) {
            case 0:
                randX = MIN_X_VELOCITY + (MIN_X_VELOCITY + INC_X_RATE / 3 - MIN_X_VELOCITY) * RANDOM.nextDouble();
                break;
            case 1:
                randX = MIN_X_VELOCITY + (MIN_X_VELOCITY + INC_X_RATE / 2 - MIN_X_VELOCITY) * RANDOM.nextDouble();
                break;
            case 2:
                randX = MIN_X_VELOCITY + (MIN_X_VELOCITY + INC_X_RATE - MIN_X_VELOCITY) * RANDOM.nextDouble();
                break;
            default:
                throw new IllegalArgumentException();
        }
        return randX;
    }

    private double calcRandomY() {
        double randY = 0.0;
        switch (this.difficulty) {
            case 0:
                randY = MIN_Y_VELOCITY + (MIN_Y_VELOCITY + INC_Y_RATE / 3 - MIN_Y_VELOCITY) * RANDOM.nextDouble();
                break;
            case 1:
                randY = MIN_Y_VELOCITY + (MIN_Y_VELOCITY + INC_Y_RATE / 2 - MIN_Y_VELOCITY) * RANDOM.nextDouble();
                break;
            case 2:
                randY = MIN_Y_VELOCITY + (MIN_Y_VELOCITY + INC_Y_RATE - MIN_Y_VELOCITY) * RANDOM.nextDouble();
                break;
            default:
                throw new IllegalArgumentException();
        }
        return randY;
    }

    /**
     * Calculates all the different information regarding the spawn of new sliceable (position, velocity).
     */
    private void doCalc() {
        final double randomX = calcRandomX();
        final double randomY = calcRandomY();
        final double spawnX = RANDOM.nextInt(screenWidth);

        this.startPositionNext = new Point2D.Double(spawnX, screenHeight);
        this.startVelocityNext = spawnX > (double) screenWidth / 2
                                ? new Point2D.Double(-randomX, randomY)
                                : new Point2D.Double(randomX, randomY);
    }

    /**
     * {@inheritDoc}.
     * @return new Bomb
     */
    @Override
    public BombImpl createBomb(final int bombId) {
        this.doCalc();
        return new BombImpl(BOMB_SIDES, startPositionNext, startVelocityNext, bombId, livesController);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public PolygonImpl createPolygon(final int polygonId) {
        this.doCalc();
        return new PolygonImpl(RANDOM.nextInt(MAX_SIDES) + 3, startPositionNext, startVelocityNext, polygonId, scoreController);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public PowerUpModel createPowerUp(final int powerUpId) {
        this.doCalc();
        // final int powerUpChoice = RANDOM.nextInt() % 5;
        // return new DoublePointsPowerUp(-1, startPositionNext, startVelocityNext, powerUpId);
        return null;
    }

}
