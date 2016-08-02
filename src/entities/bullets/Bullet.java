package entities.bullets;

import contracts.Updatable;
import entities.AbstractEntity;
import images.Images;

import java.awt.*;

public class Bullet extends AbstractEntity implements Updatable {

    public static final int BULLET_WIDTH = Images.bullet.getWidth();
    public static final int BULLET_HEIGHT = Images.bullet.getHeight();

    private int direction;
    private int speed;

    public Bullet(int x, int y, int direction, int speed) {
        super(x, y, BULLET_WIDTH, BULLET_HEIGHT);
        this.direction = direction;
        this.speed = speed;
    }

    @Override
    public void print(Graphics graphics) {
        graphics.drawImage(Images.bullet, this.getX(), this.getY(), BULLET_WIDTH, BULLET_HEIGHT, null);

//        // Bounding box
//        graphics.setColor(Color.WHITE);
//        graphics.drawRect(
//                (int) this.getBoundingBox().getX(),
//                (int) this.getBoundingBox().getY(),
//                (int) this.getBoundingBox().getWidth(),
//                (int) this.getBoundingBox().getHeight());
    }

    @Override
    public void update() {
        if (this.direction == 1) {
            this.setY(this.getY() - this.speed);
        } else if (this.direction == 2) {
            this.setY(this.getY() + this.speed);
        } else if (this.direction == 3) {
            this.setX(this.getX() - this.speed);
        } else if (this.direction == 4) {
            this.setX(this.getX() + this.speed);
        }

        this.getBoundingBox().setBounds(
                this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }

    @Override
    public boolean intersect(Rectangle rectangle) {
        return this.getBoundingBox().intersects(rectangle);
    }

    public int getDirection() {
        return this.direction;
    }
}