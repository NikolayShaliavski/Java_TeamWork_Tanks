package entities.tanks;

import contracts.Intersectable;
import contracts.Printable;
import contracts.models.Tank;
import contracts.Updatable;
import core.GameWindow;
import entities.bullets.Bullet;
import images.Images;
import input.InputHandler;

import java.awt.*;
import java.awt.image.BufferedImage;

public class PlayerTank extends AbstractTank implements Tank, Updatable, Intersectable, Printable {

    public static final int PLAYER_TANK_WIDTH = Images.firstPlayerTankUP.getWidth();
    public static final int PLAYER_TANK_HEIGHT = Images.firstPlayerTankUP.getHeight();

    private static final int PLAYER_TANK_SPEED = 2;
    private static final int PLAYER_TANK_BULLET_SPEED = 4;
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
        if (!InputHandler.firstPlayerUp && !InputHandler.firstPlayerDown && !InputHandler.firstPlayerRight && !InputHandler.firstPlayerLeft) {
            // Default direction - firstPlayerUp, when no key is pressed
            BufferedImage image = getPlayerDirectionImage(InputHandler.lastDirection);
            graphics.drawImage(image, this.x, this.y, null);
            return;

        } else if (InputHandler.firstPlayerUp) {
            graphics.drawImage(Images.firstPlayerTankUP, this.x, this.y, null);
        } else if (InputHandler.firstPlayerDown) {
            graphics.drawImage(Images.firstPlayerTankDown, this.x, this.y, null);
        } else if (InputHandler.firstPlayerLeft) {
            graphics.drawImage(Images.firstPlayerTankLeft, this.x, this.y, null);
        } else if (InputHandler.firstPlayerRight) {
            graphics.drawImage(Images.firstPlayerTankRight, this.x, this.y, null);
        }

        // Bounding box
        graphics.setColor(Color.WHITE);
        graphics.drawRect(
                (int) this.getBoundingBox().getX(),
                (int) this.getBoundingBox().getY(),
                (int) this.getBoundingBox().getWidth(),
                (int) this.getBoundingBox().getHeight());

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
        if (InputHandler.firstPlayerUp) {
            this.setBoundingBox(this.x, this.y - this.getSpeed(), this.width, this.height);
        } else if (InputHandler.firstPlayerRight) {
            this.setBoundingBox(this.x + this.getSpeed(), this.y, this.width, this.height);
        } else if (InputHandler.firstPlayerDown) {
            this.setBoundingBox(this.x, this.y + this.getSpeed(), this.width, this.height);
        } else if (InputHandler.firstPlayerLeft) {
            this.setBoundingBox(this.x - this.getSpeed(), this.y, this.width, this.height);
        }

        return this.getBoundingBox().intersects(rectangle);
    }

    private void performShooting() {
        if (InputHandler.firstPlayerShoot && !hasShot) {
            if (this.shootTicks > 60) {
                this.shoot();
                hasShot = true;
                this.shootTicks = 0;
            }
        } else if (!InputHandler.firstPlayerShoot) {
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
        if (InputHandler.firstPlayerUp && this.y - this.getSpeed() > 0 && this.collisionDirection != 1) {
            this.y -= this.getSpeed();
        } else if (InputHandler.firstPlayerDown &&
                this.y + PLAYER_TANK_HEIGHT + this.getSpeed() <= GameWindow.WINDOW_HEIGHT && this.collisionDirection
                != 2) {
            this.y += this.getSpeed();
        } else if (InputHandler.firstPlayerLeft && this.x - this.getSpeed() > 0 && this.collisionDirection != 3) {
            this.x -= this.getSpeed();
        } else if (InputHandler.firstPlayerRight &&
                this.x + PLAYER_TANK_WIDTH + this.getSpeed() <= GameWindow.WINDOW_WIDTH &&
                this.collisionDirection != 4) {
            this.x += this.getSpeed();
        }

        this.getBoundingBox().setBounds(this.x, this.y, this.width, this.height);
    }

    private BufferedImage getPlayerDirectionImage(int lastDirection) {
        if (lastDirection == 1) {
            return Images.firstPlayerTankUP;
        } else if (lastDirection == 2) {
            return Images.firstPlayerTankDown;
        } else if (lastDirection == 3) {
            return Images.firstPlayerTankLeft;
        } else if (lastDirection == 4) {
            return Images.firstPlayerTankRight;
        }

        return null;
    }
}