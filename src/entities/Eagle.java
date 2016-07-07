package entities;

import images.Images;

import java.awt.*;

public class Eagle extends Entity {
    public static final int EAGLE_WIDTH = Images.eagle.getWidth();
    public static final int EAGLE_HEIGHT = Images.eagle.getHeight();

    private static final int EAGLE_INITIAL_HEALTH = 10;

    public int health;

    public Eagle(int x, int y) {
        super(x, y, EAGLE_WIDTH, EAGLE_HEIGHT);
        this.health = EAGLE_INITIAL_HEALTH;
    }

    @Override
    public boolean intersect(Rectangle rectangle) {
        return this.getBoundingBox().intersects(rectangle);
    }

    @Override
    public void print(Graphics graphics) {
        graphics.drawImage(Images.eagle, this.x, this.y, null);
    }
}