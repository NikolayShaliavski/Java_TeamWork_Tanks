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
        graphics.drawImage(Images.bullet, this.x, this.y, BULLET_WIDTH, BULLET_HEIGHT, null);
//        graphics.fillOval(this.x, this.y, this.width, this.height);
//
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
            this.y -= this.speed;
        } else if (this.direction == 2) {
            this.y += this.speed;
        } else if (this.direction == 3) {
            this.x -= this.speed;
        } else if (this.direction == 4) {
            this.x += this.speed;
        }

        this.getBoundingBox().setBounds(this.x, this.y, this.width, this.height);
    }

    @Override
    public boolean intersect(Rectangle rectangle) {
        return this.getBoundingBox().intersects(rectangle);
    }

    public int getDirection() {
        return this.direction;
    }
}