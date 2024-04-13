package mvc.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Enum For the Polygon's Type.
 */
public enum GameObjectEnum {
    /**
     * 3 sides.
     */
    TRIANGLE("triangle", 88),
    /**
     * 4 sides.
     */
    SQUARE("square", 100),
    /**
     * 5 sides.
     */
    PENTAGON("pentagon", 97),
    /**
     * 6 sides.
     */
    HEXAGON("hexagon", 112),
    /**
     * Bomb.
     */
    BOMB("bomb", 100),
    /**
     * Double points.
     */
    DOUBLE_POINTS("double_pts", 143),
    /**
     * Bomb immunity.
     */
    BOMB_IMMUNITY("bomb_immunity", 143);

    private static final Map<Integer, GameObjectEnum> SIDES_MAP = new HashMap<>();

    private final String imagePath;
    private final int height;

    static {
        SIDES_MAP.put(0, GameObjectEnum.BOMB);
        SIDES_MAP.put(3, GameObjectEnum.TRIANGLE);
        SIDES_MAP.put(4, GameObjectEnum.SQUARE);
        SIDES_MAP.put(5, GameObjectEnum.PENTAGON);
        SIDES_MAP.put(6, GameObjectEnum.HEXAGON);
        SIDES_MAP.put(7, GameObjectEnum.DOUBLE_POINTS);
        SIDES_MAP.put(8, GameObjectEnum.BOMB_IMMUNITY);
    }

    GameObjectEnum(final String imageName, final int height) {
        this.imagePath = "/GraphicElements/" + imageName + ".png";
        this.height = height;
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
     * @return the sliceable's height.
     */
    public int getHeight() {
        return this.height;
    }

}
