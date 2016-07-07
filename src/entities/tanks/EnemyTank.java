package entities.tanks;

import contracts.Updatable;
import core.GameWindow;
import entities.bullets.Bullet;
import images.Images;

import java.awt.*;
import java.util.Random;

public class EnemyTank extends Tank implements Updatable {

    public static final int ENEMY_TANK_WIDTH = Images.enemyTankUp.getWidth();
    public static final int ENEMY_TANK_HEIGHT = Images.enemyTankUp.getHeight();

    public static final int ENEMY_TANK_BULLET_SPEED = 3;
    public static final int ENEMY_TANK_SPEED = 1;

    private static final int ENEMY_TANK_INITIAL_HEALTH = 20;
    private static final int ENEMY_TANK_INITIAL_DAMAGE = 10;

    private Random random;

    private int ticks;
    private int collisionTicks;

    public EnemyTank(int x, int y) {
        super(x, y,
                ENEMY_TANK_WIDTH,
                ENEMY_TANK_HEIGHT,
                ENEMY_TANK_INITIAL_HEALTH,
                ENEMY_TANK_SPEED,
                ENEMY_TANK_INITIAL_DAMAGE);
        this.random = new Random();
        this.direction = 2;
    }

    @Override
    public void update() {
        this.ticks++;
        if (this.ticks == 170) {
            this.shoot();
            this.changeDirection();
            this.ticks = 0;
        }

        this.move();
        this.updateBullets();
    }

    @Override
    public void dealWithCollision() {
        this.collisionTicks++;
        if (this.collisionTicks == 100) {
            this.collisionTicks = 0;

            this.shoot();
            this.changeOppositeDirection();
            this.move();
            this.changeDirection();
            this.move();
        }

        this.updateBullets();
    }

    @Override
    public void print(Graphics graphics) {
        if (this.direction == 1) {
            graphics.drawImage(Images.enemyTankUp, this.x, this.y, null);
        } else if (this.direction == 2) {
            graphics.drawImage(Images.enemyTankDown, this.x, this.y, null);
        } else if (this.direction == 3) {
            graphics.drawImage(Images.enemyTankLeft, this.x, this.y, null);
        } else if (this.direction == 4) {
            graphics.drawImage(Images.enemyTankRight, this.x, this.y, null);
        }

        graphics.setColor(Color.WHITE);
        for (int i = 0; i < this.getBullets().size(); i++) {
            this.getBullets().get(i).print(graphics);
        }
    }

    @Override
    public boolean intersect(Rectangle rectangle) {
        return this.getBoundingBox().intersects(rectangle);
    }

    private void changeOppositeDirection() {
        if (this.direction == 1) {
            this.direction = 2;
        } else if (this.direction == 2) {
            this.direction = 1;
        } else if (this.direction == 3) {
            this.direction = 4;
        } else if (this.direction == 4) {
            this.direction = 3;
        }
    }

    private void move() {
        if (this.direction == 1 && this.y - this.getSpeed() > 0) {
            this.y -= this.getSpeed();
        } else if (this.direction == 2 &&
                this.y + ENEMY_TANK_HEIGHT + this.getSpeed() < GameWindow.WINDOW_HEIGHT) {
            this.y += this.getSpeed();
        } else if (this.direction == 3 && this.x - this.getSpeed() > 0) {
            this.x -= this.getSpeed();
        } else if (this.direction == 4 &&
                this.x + ENEMY_TANK_WIDTH + this.getSpeed() < GameWindow.WINDOW_WIDTH) {
            this.x += this.getSpeed();
        }

        this.getBoundingBox().setBounds(this.x, this.y, this.width, this.height);
    }

    private void shoot() {
        int bulletX;
        int bulletY;
        if (this.direction == 1) {
            bulletX = (this.x + (ENEMY_TANK_WIDTH / 2 - 6));
            bulletY = this.y;
        } else if (this.direction == 2) {
            bulletX = (this.x + (ENEMY_TANK_WIDTH / 2 - 6));
            bulletY = (this.y + ENEMY_TANK_HEIGHT);
        } else if (this.direction == 3) {
            bulletX = this.x;
            bulletY = (this.y + (ENEMY_TANK_HEIGHT / 2 - 6));
        } else {
            bulletX = (this.x + ENEMY_TANK_WIDTH);
            bulletY = (this.y + (ENEMY_TANK_HEIGHT / 2 - 6));
        }

        Bullet bullet = new Bullet(bulletX, bulletY, this.direction, ENEMY_TANK_BULLET_SPEED);
        this.getBullets().add(bullet);
    }

    private void changeDirection() {
        int nextDirection = this.random.nextInt(4) + 1;
        while (nextDirection == this.direction) {
            nextDirection = this.random.nextInt(4) + 1;
        }

        this.direction = nextDirection;
    }

    private void updateBullets() {
        this.removeOutOfRangeBullets();
        this.getBullets().forEach(Bullet::update);
    }
}