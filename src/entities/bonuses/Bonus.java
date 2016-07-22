package entities.bonuses;

import contracts.Printable;
import entities.AbstractEntity;
import images.Images;

import java.awt.*;

public class Bonus extends AbstractEntity implements Printable{
    private static final int FREEZE_WIDTH = Images.freeze.getWidth();
    private static final int FREEZE_HEIGHT = Images.freeze.getHeight();

    public Bonus(int x, int y) {
        super(x, y, FREEZE_WIDTH, FREEZE_HEIGHT);
    }

    @Override
    public boolean intersect(Rectangle rectangle) {
        if (this.getBoundingBox().intersects(rectangle)) {
            return true;
        }
        return false;
    }

    @Override
    public void print(Graphics graphics) {
        graphics.drawImage(Images.freeze, this.x, this.y, FREEZE_WIDTH, FREEZE_HEIGHT, null);
    }
}
