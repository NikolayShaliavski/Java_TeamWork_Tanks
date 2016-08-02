package entities.bonuses;

import contracts.Printable;
import entities.AbstractEntity;

import java.awt.*;

public abstract class Bonus extends AbstractEntity implements Printable {

    protected Bonus(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    public boolean intersect(Rectangle rectangle) {
        return this.getBoundingBox().intersects(rectangle);
    }
}