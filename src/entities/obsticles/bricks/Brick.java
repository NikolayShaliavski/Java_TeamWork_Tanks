package entities.obsticles.bricks;

import entities.Entity;
import images.Images;

import java.awt.*;

public class Brick extends Entity {
    public static final int BRICK_WIDTH = Images.brick.getWidth();
    public static final int BRICK_HEIGHT = Images.brick.getHeight();

    public Brick(int x, int y) {
        super(x, y, BRICK_WIDTH, BRICK_HEIGHT);
    }

    @Override
    public void print(Graphics graphics) {
//        // Bounding box
//        graphics.setColor(Color.white);
//        graphics.drawRect(this.x, this.y, this.width, this.height);
        graphics.drawImage(Images.brick, this.x, this.y, null);
    }

    @Override
    public boolean intersect(Rectangle rectangle) {
        return this.getBoundingBox().contains(rectangle);
    }
}