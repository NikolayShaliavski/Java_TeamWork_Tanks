package entities.obsticles.bricks;

import entities.Entity;
import images.Images;

import java.awt.*;

public class Steel extends Entity {
    public static final int STEEL_WIDTH = Images.steel.getWidth();
    public static final int STEEL_HEIGHT = Images.steel.getHeight();

    public Steel(int x, int y) {
        super(x, y, STEEL_WIDTH, STEEL_HEIGHT);
    }

    @Override
    public boolean intersect(Rectangle rectangle) {
//        // Bounding box
//        graphics.setColor(Color.white);
//        graphics.drawRect(this.x, this.y, this.width, this.height);
        return this.getBoundingBox().intersects(rectangle);
    }

    @Override
    public void print(Graphics graphics) {
        graphics.drawImage(Images.steel, this.x, this.y, null);
    }
}