package entities.bonuses;

import images.Images;

import java.awt.*;

/**
 * This item is dropped by the the enemy tank, when the player kills
 * 3 enemies.
 * <p>
 * When the player passes through this item, the currently spawn enemies,
 * freeze for some time. During that time they cannot do nothing.
 */
public class Freeze extends Bonus {

    private static final int FREEZE_WIDTH = Images.freeze.getWidth();
    private static final int FREEZE_HEIGHT = Images.freeze.getHeight();

    public Freeze(int x, int y) {
        super(x, y, FREEZE_WIDTH, FREEZE_HEIGHT);
    }

    @Override
    public void print(Graphics graphics) {
        graphics.drawImage(Images.freeze, this.getX(), this.getY(), FREEZE_WIDTH, FREEZE_HEIGHT, null);
    }
}