package entities.bonuses;

import images.Images;

import java.awt.*;

/**
 * This item is dropped by the player and whenever an enemy tank
 * passes through it, it dies.
 */
public class Bomb extends Bonus {

    private static final int BOMB_WIDTH = Images.bomb.getWidth();
    private static final int BOMB_HEIGHT = Images.bomb.getHeight();
    private static final int BOMB_DAMAGE = 20;

    public Bomb(int x, int y) {
        super(x, y, BOMB_WIDTH, BOMB_HEIGHT);
    }

    @Override
    public void print(Graphics graphics) {
        graphics.drawImage(Images.bomb, this.getX(), this.getY(), BOMB_WIDTH, BOMB_HEIGHT, null);
    }

    public int getDamage() {
        return BOMB_DAMAGE;
    }
}