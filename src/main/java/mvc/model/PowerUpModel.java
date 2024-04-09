package mvc.model;

public interface PowerUpModel extends SliceableModel {

    void applyPowerUp();

    GameObjectEnum getPowerUpType();
}
