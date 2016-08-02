package entities.obsticles.bricks;

import entities.AbstractEntity;
import images.Images;

import java.awt.*;

/**
 * Basic brick obstacle. It can be destroyed.
 */
public class Brick extends AbstractEntity {

    public static final int BRICK_WIDTH = Images.brick.getWidth();
    public static final int BRICK_HEIGHT = Images.brick.getHeight();

    public Brick(int x, int y) {
        super(x, y, BRICK_WIDTH, BRICK_HEIGHT);
    }

    @Override
    public void print(Graphics graphics) {
//        // Bounding box
//        graphics.setColor(Color.white);
//        graphics.drawRect(this.getX(), this.getY(), this.getWidth(), this.getHeight());
        graphics.drawImage(Images.brick, this.getX(), this.getY(), null);
    }

    @Override
    public boolean intersect(Rectangle rectangle) {
        return this.getBoundingBox().contains(rectangle);
    }
}