package mvc.model;

/**
 * Power Up interface.
 */

public interface PowerUpModel extends SliceableModel {

    /**
     * @return the power up's type as GameObjectEnum.
     */
    GameObjectEnum getPowerUpType();
}
