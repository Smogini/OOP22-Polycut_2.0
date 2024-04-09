package mvc.controller.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import mvc.controller.BladeController;
import mvc.controller.GameLoop;
import mvc.controller.GameWorldController;
import mvc.controller.LivesController;
import mvc.controller.ScoreController;
import mvc.model.SliceableModel;
import mvc.model.PowerUpModel;
import mvc.model.SliceableFactory;
import mvc.model.impl.SliceableFactoryImpl;
import mvc.view.impl.GameScreenImpl;

/**
 * Implementation class of the GameWorld controller.
 * Check GameWorldController documentation.
 */
public class GameWorldControllerImpl implements GameWorldController {

    private final BladeController blade;
    private final GameScreenImpl screen;
    private final SliceableFactory factory;
    private final LivesController livesController;

    private List<SliceableModel> polygons;
    private List<SliceableModel> bombs;
    private final int difficulty;

    /**
     * Constructor of the game world.
     * @param difficulty
     */
    public GameWorldControllerImpl(final int difficulty) {
        this.blade = new BladeControllerImpl();
        this.livesController = new LivesControllerImpl();
        final ScoreController scoreController = new ScoreControllerImpl();
        this.screen = new GameScreenImpl(livesController, scoreController);
        this.factory = new SliceableFactoryImpl(screen.getScreenWidth(), screen.getScreenHeight(), difficulty,
                                                livesController, scoreController);
        this.polygons = new ArrayList<>();
        this.bombs = new ArrayList<>();
        this.difficulty = difficulty;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<SliceableModel> getPolygons() {
        return new ArrayList<>(this.polygons);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void setPolygons(final List<SliceableModel> updatedList) {
        this.polygons = new ArrayList<>(updatedList);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<SliceableModel> getBombs() {
        return new ArrayList<>(this.bombs);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void setBombs(final List<SliceableModel> updatedList) {
        this.bombs = new ArrayList<>(updatedList);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public SliceableModel createPolygon(final int sliceableId) {
        final SliceableModel polygon = factory.createPolygon(sliceableId);
        this.polygons.add(polygon);
        // this.blade.registerSliceable(polygon);
        return polygon;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public SliceableModel createBomb(final int bombId) {
        final SliceableModel bomb = factory.createBomb(bombId);
        this.bombs.add(bomb);
        // this.blade.registerSliceable(bomb);
        return bomb;
    }

    /**
     * {@inheritdoc}.
     */
    @Override
    public PowerUpModel createPowerUp(final int powerUpID) {
        final PowerUpModel powerUp = factory.createPowerUp(powerUpID);
        this.polygons.add(powerUp);
        // this.blade.registerPowerUp(powerUp);
        return powerUp;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void startLoop() {
        final GameLoop gameLoop = new GameLoopImpl(this, this.livesController, screen);
        gameLoop.setDifficulty(this.difficulty);
    }

    /**
     * Delete the sliceable object from the list by its ID.
     * @param <T> extends SliceableModel class
     * @param sliceables the list of sliceables
     * @param sliceableId the ID of the sliceable
     */
    private <T extends SliceableModel> void deleteSliceableById(final List<T> sliceables,
                                                                final Integer sliceableId) {
        sliceables.removeIf(sliceable -> sliceable.getSliceableId() == sliceableId);
        // this.blade.unregisterSliceableByID(sliceableId);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void outOfBoundDelete(final int sliceableId) {
        deleteSliceableById(this.polygons, sliceableId);
        deleteSliceableById(this.bombs, sliceableId);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<SliceableModel> getSliceables() {
        return Stream.concat(getPolygons().stream(), getBombs().stream())
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public BladeController getBladeController() {
        return this.blade;
    }

}
