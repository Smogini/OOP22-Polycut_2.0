package mvc.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Enum For the Polygon's Type.
 */
public enum GameObjectEnum {
    /**
     * Bomb.
     */
    BOMB("bomb"),
    /**
     * 3 sides.
     */
    TRIANGLE("triangle"),
    /**
     * 4 sides.
     */
    SQUARE("square"),
    /**
     * 5 sides.
     */
    PENTAGON("pentagon"),
    /**
     * 6 sides.
     */
    HEXAGON("hexagon"),
    /**
     * Double points.
     */
    DOUBLE_POINTS("double_pts"),
    /**
     * Bomb immunity.
     */
    BOMB_IMMUNITY("bomb_immunity"),
    /**
     * Freeze.
     */
    FREEZE("freeze");

    private static final Map<Integer, GameObjectEnum> SIDES_MAP = new HashMap<>();

    private final String imagePath;

    static {
        SIDES_MAP.put(0, BOMB);
        SIDES_MAP.put(3, TRIANGLE);
        SIDES_MAP.put(4, SQUARE);
        SIDES_MAP.put(5, PENTAGON);
        SIDES_MAP.put(6, HEXAGON);
        SIDES_MAP.put(7, DOUBLE_POINTS);
        SIDES_MAP.put(8, BOMB_IMMUNITY);
        SIDES_MAP.put(9, FREEZE);
    }

    GameObjectEnum(final String imageName) {
        this.imagePath = "/GraphicElements/" + imageName + ".png";
    }

    /**
     * @param sides
     * @return the GameObject type associated to the sliceable side.
     */
    public static GameObjectEnum getSliceableType(final int sides) {
        return SIDES_MAP.getOrDefault(sides, null);
    }

    /**
     * @param sliceable
     * @return the sides of the specified sliceable.
     */
    public static int getSliceableSides(final GameObjectEnum sliceable) {
        final Optional<Integer> numberOfSides = SIDES_MAP.entrySet().stream()
            .filter(entry -> entry.getValue() == sliceable)
            .map(Map.Entry::getKey)
            .findFirst();
        return numberOfSides.orElse(-1);
    }

    /**
     * @return the absolute image path as a String.
     */
    public String getImagePath() {
        return this.imagePath;
    }

    /**
     * {@inheritdoc}.
     */
    @Override
    public String toString() {
        switch (this) {
            case BOMB_IMMUNITY:
                return "Bomb immunity";
            case FREEZE:
                return "Freeze";
            case DOUBLE_POINTS:
                return "Double points";
            default:
                throw new IllegalArgumentException();
        }
    }

}
