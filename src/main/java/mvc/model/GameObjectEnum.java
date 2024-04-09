package mvc.model;

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
    DOUBLE_POINTS("double_pts", 143);

    private final String imagePath;
    private final int height;

    GameObjectEnum(final String imageName, final int height) {
        this.imagePath = "/GraphicElements/" + imageName + ".png";
        this.height = height;
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
