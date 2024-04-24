package mvc.model;

/**
 * Enum For the Polygon's Type.
 */
public enum GameObjectEnum {
    /**
     * Bomb.
     */
    BOMB("bomb", 0),
    /**
     * 3 sides.
     */
    TRIANGLE("triangle", 3),
    /**
     * 4 sides.
     */
    SQUARE("square", 4),
    /**
     * 5 sides.
     */
    PENTAGON("pentagon", 5),
    /**
     * 6 sides.
     */
    HEXAGON("hexagon", 6),
    /**
     * Double points.
     */
    DOUBLE_POINTS("double_pts", 7),
    /**
     * Bomb immunity.
     */
    BOMB_IMMUNITY("bomb_immunity", 8),
    /**
     * Freeze.
     */
    FREEZE("freeze", 9),
    /**
     * Increase speed.
     */
    INCREASE_SPEED("inc_speed", 10),
    /**
     * Lose points.
     */
    LOSE_POINTS("lose_pts", 11),
    /**
     * Double score.
     */
    DOUBLE_SCORE("double_score", 12);

    private final String imageName;
    private final int sides;

    GameObjectEnum(final String imageName, final int sides) {
        this.imageName = imageName;
        this.sides = sides;
    }

    /**
     * @param sides
     * @return the GameObject type associated to the sliceable side.
     */
    public static GameObjectEnum getSliceableType(final int sides) {
        for (final var elem : GameObjectEnum.values()) {
            if (elem.sides == sides) {
                return elem;
            }
        }
        return null;
    }

    /**
     * @param sliceable
     * @return the sides of the specified sliceable.
     */
    public static int getSliceableSides(final GameObjectEnum sliceable) {
        for (final var elem : GameObjectEnum.values()) {
            if (elem.equals(sliceable)) {
                return elem.sides;
            }
        }
        return -1;
    }

    /**
     * @return the absolute image path as a String.
     */
    public String getImagePath() {
        return "/GraphicElements/" + imageName + ".png";
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
            case INCREASE_SPEED:
                return "Increase speed";
            case LOSE_POINTS:
                return "Lose points";
            case DOUBLE_SCORE:
                return "Double score";
            default:
                throw new IllegalArgumentException();
        }
    }

}
