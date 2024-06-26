package mvc.model;

import org.junit.jupiter.api.Test;

import mvc.controller.BladeController;
import mvc.controller.LivesController;
import mvc.controller.ScoreController;
import mvc.controller.impl.BladeControllerImpl;
import mvc.controller.impl.LivesControllerImpl;
import mvc.controller.impl.ScoreControllerImpl;
import mvc.model.impl.SliceableFactoryImpl;
import mvc.view.impl.GameScreenImpl;
import mvc.view.impl.TimerViewImpl;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

class SliceableFactoryImplTest {
    private final LivesController livesController = new LivesControllerImpl();
    private final ScoreController scoreController = new ScoreControllerImpl();
    private final TimerViewImpl timerView = new TimerViewImpl();
    private final GameScreenImpl screen = new GameScreenImpl(livesController, scoreController, timerView);
    private final BladeController bladeController = new BladeControllerImpl(timerView);
    private final SliceableFactoryImpl factory = new SliceableFactoryImpl(screen.getScreenWidth(), screen.getScreenHeight(), 0,
                                                                          livesController, scoreController,
                                                                          bladeController);

    /**
     * Test class of the Sliceable Factory createBomb method. It controls that every Bomb created
     * respect the parameters given and is usable.
     */
    @Test
    void createBombTest() {
        /*Creating the first two bombs and controlling their position and velocity being different*/
        final SliceableModel bomb1 = factory.createBomb(0);
        final SliceableModel bomb2 = factory.createBomb(1);
        assertNotEquals(bomb1.getPosition(), bomb2.getPosition());
        assertNotEquals(bomb1.getVelocity(), bomb2.getVelocity());
    }

    /**
     * Test class of the Sliceable Factory createPolygon method. It controls that every Polygon created
     * respect the parameters given and is usable.
     */
    @Test
    void createPolygonTest() {
        /*Creating the first two polygons and controlling their position and velocity being different*/
        final SliceableModel poly1 = factory.createPolygon(0);
        final SliceableModel poly2 = factory.createPolygon(1);
        assertNotEquals(poly1.getPosition(), poly2.getPosition());
        assertNotEquals(poly1.getVelocity(), poly2.getVelocity());
    }
}
