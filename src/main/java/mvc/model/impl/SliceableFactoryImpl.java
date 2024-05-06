package mvc.model.impl;

import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import mvc.controller.BladeController;
import mvc.controller.LivesController;
import mvc.controller.ScoreController;
import mvc.model.GameObjectEnum;
import mvc.model.PowerUpModel;
import mvc.model.SliceableFactory;

/**
 * {@inheritDoc}.
 */
public class SliceableFactoryImpl implements SliceableFactory {

    private static final Random RANDOM = new Random();
    private static final Integer MAX_SIDES = 4;
    private static final double MIN_X_VELOCITY = 30.0;
    private static final double MIN_Y_VELOCITY = 85.0;
    private static final double INC_X_RATE = 10.0;
    private static final double INC_Y_RATE = 50.0;

    private final List<Class<? extends AbstractPowerUp>> powerUpClasses;
    private final LivesController livesController;
    private final ScoreController scoreController;
    private final BladeController bladeController;

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
     * @param bladeController
     */
    @SuppressFBWarnings
    public SliceableFactoryImpl(final Integer width, final Integer height, final int difficulty,
                                final LivesController livesController, final ScoreController scoreController,
                                final BladeController bladeController) {
        this.screenWidth = width;
        this.screenHeight = height;
        this.difficulty = difficulty;
        this.livesController = livesController;
        this.scoreController = scoreController;
        this.bladeController = bladeController;
        this.powerUpClasses = scanPowerUps();
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
        return new BombImpl(0, startPositionNext, startVelocityNext, bombId, livesController);
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
        final int powerUpChoice = RANDOM.nextInt(powerUpClasses.size());
        final Class<? extends AbstractPowerUp> selectedClass = powerUpClasses.get(powerUpChoice);
        final GameObjectEnum powerUpType = getPowerUpType(selectedClass.getSimpleName());

        try {
            final var constructor = selectedClass.getConstructor(
                    Integer.class, Point2D.class, Point2D.class,
                    Integer.class, ScoreController.class, BladeController.class
                );

            return constructor.newInstance(
                    GameObjectEnum.getSliceableSides(powerUpType),
                    startPositionNext, startVelocityNext, powerUpId, this.scoreController, this.bladeController
                );
        } catch (final NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            return null;
        }
    }

    /**
     * @param powerUpClassName
     * @return the GameObjectEnum associated with the specified class name.
     */
    private GameObjectEnum getPowerUpType(final String powerUpClassName) {
        final String[] parts = powerUpClassName.replace("PowerUp", "").split("(?=[A-Z])");
        final String enumName = String.join("_", parts).toUpperCase(Locale.getDefault());
        return GameObjectEnum.valueOf(enumName);
    }

    /**
     * This method gets the resources based on the URI.
     * This method is used when the program is run from a jar file.
     * 
     * @param packageName
     * @param packagePath
     * @return a list of power up classes.
     */
    private List<Class<? extends AbstractPowerUp>> scanPowerUpsFromURI(final String packageName,
                                                                                final String packagePath) {
        final List<Class<? extends AbstractPowerUp>> powerUps = new ArrayList<>();

        try {
            final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            final Enumeration<URL> resources = classLoader.getResources(packagePath);

            while (resources.hasMoreElements()) {
                final URI uri = resources.nextElement().toURI();

                if ("jar".equals(uri.getScheme())) {
                    try (FileSystem fs = FileSystems.newFileSystem(uri, Collections.emptyMap())) {
                        final Path packageDir = fs.getPath(packagePath);
                        Files.walk(packageDir)
                            .filter(Files::isRegularFile)
                            .forEach(path -> {
                                final Path fileName = path.getFileName();
                                if (fileName == null) {
                                    return;
                                }
                                final String className = packageName + "." + fileName.toString().replace(".class", "");
                                try {
                                    final Class<?> clazz = Class.forName(className);
                                    if (AbstractPowerUp.class.isAssignableFrom(clazz)) {
                                        powerUps.add(clazz.asSubclass(AbstractPowerUp.class));
                                    }
                                } catch (final ClassNotFoundException e) {
                                    return;
                                }
                            });
                    }
                }
            }
        } catch (final IOException | URISyntaxException e) {
            return powerUps;
        }

        return powerUps;
    }

    /**
     * This method gets the resources based on classpath.
     * 
     * @param packagePath
     * @return a list of power up classes.
     */
    private List<Class<? extends AbstractPowerUp>> scanPowerUpsFromClassPath(final String packagePath) {
        final List<Class<? extends AbstractPowerUp>> powerUps = new ArrayList<>();

        try (InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(packagePath)) {
            if (inputStream != null) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"))) {
                    String className = reader.readLine();
                    while (className != null) {
                        className = className.replace(".class", "");
                        className = packagePath.replace('/', '.') + "." + className.replace('/', '.');
                        final Class<?> clazz = Class.forName(className);
                        if (AbstractPowerUp.class.isAssignableFrom(clazz)) {
                            powerUps.add(clazz.asSubclass(AbstractPowerUp.class));
                        }
                        className = reader.readLine();
                    }
                }
            }
        } catch (final IOException | ClassNotFoundException e) {
            return powerUps;
        }

        return powerUps;
    }

    /**
     * Initializes a list of power up classes present in the "mvc.model.impl.powerup" package.
     * So when a power up is added to the project it automatically adds it to the factory, without write it manually.
     * 
     * @return a list of power up classes.
     */
    private List<Class<? extends AbstractPowerUp>> scanPowerUps() {
        final String packageName = "mvc.model.impl.powerup";
        final String packagePath = packageName.replace('.', '/');
        final String classPath = SliceableFactoryImpl.class.getResource("").getProtocol();

        if ("jar".equals(classPath)) {
            return scanPowerUpsFromURI(packageName, packagePath);
        } else {
            return scanPowerUpsFromClassPath(packagePath);
        }
    }

}
