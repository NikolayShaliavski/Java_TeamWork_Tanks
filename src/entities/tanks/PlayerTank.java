package entities.tanks;

import contracts.Updatable;
import core.GameWindow;
import entities.bullets.Bullet;
import images.Images;
import input.InputHandler;

import java.awt.*;
import java.awt.image.BufferedImage;

public class PlayerTank extends Tank implements Updatable {

    public static final int PLAYER_TANK_WIDTH = Images.playerTankUP.getWidth();
    public static final int PLAYER_TANK_HEIGHT = Images.playerTankUP.getHeight();

    public static final int PLAYER_TANK_SPEED = 2;
    public static final int PLAYER_TANK_BULLET_SPEED = 4;

    private static final int PLAYER_TANK_INITIAL_HEALTH = 10;
    private static final int PLAYER_TANK_INITIAL_DAMAGE = 10;

    private int collisionDirection = -1;

    private boolean hasShot;
    private int shootTicks;

    public PlayerTank(int x, int y) {
        super(x, y,
                PLAYER_TANK_WIDTH,
                PLAYER_TANK_HEIGHT,
                PLAYER_TANK_INITIAL_HEALTH,
                PLAYER_TANK_SPEED,
                PLAYER_TANK_INITIAL_DAMAGE);
        this.shootTicks = 50;
    }

    @Override
    public void update() {
        this.collisionDirection = -1;
        this.direction = InputHandler.lastDirection;

        this.move();
        this.performShooting();

        this.removeOutOfRangeBullets();
        this.getBullets().forEach(Bullet::update);
    }


    @Override
    public void print(Graphics graphics) {
        if (!InputHandler.up && !InputHandler.down && !InputHandler.right && !InputHandler.left) {
            // Default direction - up, when no key is pressed
            BufferedImage image = getPlayerDirectionImage(InputHandler.lastDirection);
            graphics.drawImage(image, this.x, this.y, null);
        } else if (InputHandler.up) {
            graphics.drawImage(Images.playerTankUP, this.x, this.y, null);
        } else if (InputHandler.down) {
            graphics.drawImage(Images.playerTankDown, this.x, this.y, null);
        } else if (InputHandler.left) {
            graphics.drawImage(Images.playerTankLeft, this.x, this.y, null);
        } else if (InputHandler.right) {
            graphics.drawImage(Images.playerTankRight, this.x, this.y, null);
        }

        //// Bounding box
//        graphics.setColor(Color.WHITE);
//        graphics.drawRect(
//                (int) this.getBoundingBox().getX(),
//                (int) this.getBoundingBox().getY(),
//                (int) this.getBoundingBox().getWidth(),
//                (int) this.getBoundingBox().getHeight());

        graphics.setColor(Color.WHITE);
        for (int i = 0; i < this.getBullets().size(); i++) {
            this.getBullets().get(i).print(graphics);
        }
    }

    @Override
    public void dealWithCollision() {

        this.direction = InputHandler.lastDirection;
        this.collisionDirection = this.direction;

        this.move();

        this.performShooting();

        this.removeOutOfRangeBullets();
        this.getBullets().forEach(Bullet::update);
    }

    @Override
    public boolean intersect(Rectangle rectangle) {
        //return (this.getBoundingBox().intersects(rectangle));
//        if (this.getBoundingBox().intersects(rectangle)) {
//            return true;
//        }
        if (InputHandler.up) {
            this.setBoundingBox(this.x, this.y - this.getSpeed(), this.width, this.height);
        } else if (InputHandler.right) {
            this.setBoundingBox(this.x + this.getSpeed(), this.y, this.width, this.height);
        } else if (InputHandler.down) {
            this.setBoundingBox(this.x, this.y + this.getSpeed(), this.width, this.height);
        } else if (InputHandler.left) {
            this.setBoundingBox(this.x - this.getSpeed(), this.y, this.width, this.height);
        }

        if (this.getBoundingBox().intersects(rectangle)) {
            return true;
        }
        return false;
    }

    private void performShooting() {
        if (InputHandler.space && !hasShot) {
            if (this.shootTicks > 60) {
                this.shoot();
                hasShot = true;
                this.shootTicks = 0;
            }
        } else if (!InputHandler.space) {
            hasShot = false;
        }

        this.shootTicks++;
    }

    private void shoot() {
        int bulletX = 0;
        int bulletY = 0;
        if (this.direction == 1) {
            bulletX = (this.x + (PLAYER_TANK_WIDTH / 2 - 6));
            bulletY = this.y;
        } else if (this.direction == 2) {
            bulletX = (this.x + (PLAYER_TANK_WIDTH / 2 - 6));
            bulletY = (this.y + PLAYER_TANK_HEIGHT);
        } else if (this.direction == 3) {
            bulletX = this.x;
            bulletY = (this.y + (PLAYER_TANK_HEIGHT / 2 - 6));
        } else if (this.direction == 4) {
            bulletX = (this.x + PLAYER_TANK_WIDTH);
            bulletY = (this.y + (PLAYER_TANK_HEIGHT / 2 - 6));
        }

        Bullet bullet = new Bullet(bulletX, bulletY, direction, PLAYER_TANK_BULLET_SPEED);
        this.getBullets().add(bullet);
    }

    private void move() {
        if (InputHandler.up && this.y - this.getSpeed() > 0 && this.collisionDirection != 1) {
            this.y -= this.getSpeed();
            //this.direction = 1;
        } else if (InputHandler.down &&
                this.y + PLAYER_TANK_HEIGHT + this.getSpeed() <= GameWindow.WINDOW_HEIGHT && this.collisionDirection
                != 2) {
            this.y += this.getSpeed();
            //this.direction = 2;
        } else if (InputHandler.left && this.x - this.getSpeed() > 0 && this.collisionDirection != 3) {
            this.x -= this.getSpeed();
            //this.direction = 3;
        } else if (InputHandler.right &&
                this.x + PLAYER_TANK_WIDTH + this.getSpeed() <= GameWindow.WINDOW_WIDTH &&
                this.collisionDirection != 4) {
            this.x += this.getSpeed();
            //this.direction = 4;
        }

        this.getBoundingBox().setBounds(this.x, this.y, this.width, this.height);
    }

    private BufferedImage getPlayerDirectionImage(int lastDirection) {
        if (lastDirection == 1) {
            return Images.playerTankUP;
        } else if (lastDirection == 2) {
            return Images.playerTankDown;
        } else if (lastDirection == 3) {
            return Images.playerTankLeft;
        } else if (lastDirection == 4) {
            return Images.playerTankRight;
        }

        return null;
    }
}