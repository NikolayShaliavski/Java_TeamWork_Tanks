package entities.obsticles.bricks;

import entities.AbstractEntity;
import images.Images;

import java.awt.*;

/**
 * Steel obstacle. It cannot be destroyed.
 */
public class Steel extends AbstractEntity {

    public static final int STEEL_WIDTH = Images.steel.getWidth();
    public static final int STEEL_HEIGHT = Images.steel.getHeight();

    public Steel(int x, int y) {
        super(x, y, STEEL_WIDTH, STEEL_HEIGHT);
    }

    @Override
    public boolean intersect(Rectangle rectangle) {
        return this.getBoundingBox().intersects(rectangle);
    }

    @Override
    public void print(Graphics graphics) {
//        // Bounding box
//        graphics.setColor(Color.white);
//        graphics.drawRect(this.getX(), this.getY(), this.getWidth(), this.getHeight());
        graphics.drawImage(Images.steel, this.getX(), this.getY(), null);
    }
}