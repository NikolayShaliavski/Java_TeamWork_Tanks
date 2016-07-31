package entities.bonuses;

import images.Images;

import java.awt.*;

public class Freeze extends Bonus {

    private static final int FREEZE_WIDTH = Images.freeze.getWidth();
    private static final int FREEZE_HEIGHT = Images.freeze.getHeight();

    public Freeze(int x, int y) {
        super(x, y, FREEZE_WIDTH, FREEZE_HEIGHT);
    }

    @Override
    public void print(Graphics graphics) {
        graphics.drawImage(Images.freeze, this.x, this.y, FREEZE_WIDTH, FREEZE_HEIGHT, null);
    }
}
