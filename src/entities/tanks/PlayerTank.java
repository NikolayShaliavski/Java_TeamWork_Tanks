package entities.tanks;

import contracts.Intersectable;
import contracts.Printable;
import contracts.Updatable;
import contracts.inputHandler.PlayerInputHandler;
import contracts.models.Tank;
import core.GameWindow;
import entities.bonuses.Bomb;
import entities.bullets.Bullet;
import images.Images;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public abstract class PlayerTank extends AbstractTank
        implements Tank, Updatable, Intersectable, Printable {

    public static final int PLAYER_TANK_WIDTH = Images.playerTankUp.getWidth();
    public static final int PLAYER_TANK_HEIGHT = Images.playerTankUp.getHeight();

    private static final int PLAYER_TANK_SPEED = 2;
    private static final int PLAYER_TANK_BULLET_SPEED = 4;
    private static final int PLAYER_TANK_INITIAL_HEALTH = 10;
    private static final int PLAYER_TANK_INITIAL_DAMAGE = 10;

    private PlayerInputHandler inputHandler;

    private List<Bomb> bombs;
    private int bombsDropped;

    private int collisionDirection = -1;

    private boolean hasShot;
    private int shootTicks;

    private boolean hasDroppedBomb;

    protected PlayerTank(int x, int y, PlayerInputHandler inputHandler) {
        super(x, y,
                PLAYER_TANK_WIDTH,
                PLAYER_TANK_HEIGHT,
                PLAYER_TANK_INITIAL_HEALTH,
                PLAYER_TANK_SPEED,
                PLAYER_TANK_INITIAL_DAMAGE);
        this.shootTicks = 50;
        this.inputHandler = inputHandler;
        this.bombs = new ArrayList<>();
        this.bombsDropped = 0;
    }

    @Override
    public void update() {
        this.collisionDirection = -1;
        this.direction = this.inputHandler.getLastDirection();

        this.move();
        this.performShooting();

        this.dropBomb();

        this.removeOutOfRangeBullets();
        this.getBullets().forEach(Bullet::update);
    }

    @Override
    public void print(Graphics graphics) {
        if (!this.inputHandler.isUp() &&
                !this.inputHandler.isDown() &&
                !this.inputHandler.isRight() &&
                !this.inputHandler.isLeft()) {
            // Default direction - firstPlayerUp, when no key is pressed
            BufferedImage image = getPlayerDirectionImage(this.inputHandler.getLastDirection());
            graphics.drawImage(image, this.getX(), this.getY(), null);
            return;

        } else if (this.inputHandler.isUp()) {
            graphics.drawImage(Images.playerTankUp, this.getX(), this.getY(), null);
        } else if (this.inputHandler.isDown()) {
            graphics.drawImage(Images.playerTankDown, this.getX(), this.getY(), null);
        } else if (this.inputHandler.isLeft()) {
            graphics.drawImage(Images.playerTankLeft, this.getX(), this.getY(), null);
        } else if (this.inputHandler.isRight()) {
            graphics.drawImage(Images.playerTankRight, this.getX(), this.getY(), null);
        }

//        //Bounding box
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

        for (Bomb bomb : this.getBombs()) {
            bomb.print(graphics);
        }
    }

    @Override
    public void dealWithCollision() {

        this.direction = this.inputHandler.getLastDirection();
        this.collisionDirection = this.direction;

        this.move();

        this.performShooting();

        this.removeOutOfRangeBullets();
        this.getBullets().forEach(Bullet::update);
    }

    @Override
    public boolean intersect(Rectangle rectangle) {
        if (this.inputHandler.isUp()) {
            this.setBoundingBox(
                    this.getX(), this.getY() - this.getSpeed(), this.getWidth(), this.getHeight());
        } else if (this.inputHandler.isRight()) {
            this.setBoundingBox(
                    this.getX() + this.getSpeed(), this.getY(), this.getWidth(), this.getHeight());
        } else if (this.inputHandler.isDown()) {
            this.setBoundingBox(
                    this.getX(), this.getY() + this.getSpeed(), this.getWidth(), this.getHeight());
        } else if (this.inputHandler.isLeft()) {
            this.setBoundingBox(
                    this.getX() - this.getSpeed(), this.getY(), this.getWidth(), this.getHeight());
        }

        return this.getBoundingBox().intersects(rectangle);
    }

    public List<Bomb> getBombs() {
        return this.bombs;
    }

    private void performShooting() {
        if (this.inputHandler.hasShoot() && !hasShot) {
            if (this.shootTicks > 60) {
                this.shoot();
                this.hasShot = true;
                this.shootTicks = 0;
            }
        } else if (!this.inputHandler.hasShoot()) {
            this.hasShot = false;
        }

        this.shootTicks++;
    }

    private void shoot() {
        int bulletX = 0;
        int bulletY = 0;
        if (this.direction == 1) {
            bulletX = (this.getX() + (PLAYER_TANK_WIDTH / 2 - 6));
            bulletY = this.getY();
        } else if (this.direction == 2) {
            bulletX = (this.getX() + (PLAYER_TANK_WIDTH / 2 - 6));
            bulletY = (this.getY() + PLAYER_TANK_HEIGHT);
        } else if (this.direction == 3) {
            bulletX = this.getX();
            bulletY = (this.getY() + (PLAYER_TANK_HEIGHT / 2 - 6));
        } else if (this.direction == 4) {
            bulletX = (this.getX() + PLAYER_TANK_WIDTH);
            bulletY = (this.getY() + (PLAYER_TANK_HEIGHT / 2 - 6));
        }

        Bullet bullet = new Bullet(bulletX, bulletY, direction, PLAYER_TANK_BULLET_SPEED);
        this.getBullets().add(bullet);
    }

    private void dropBomb() {
        if (this.inputHandler.hasDroppedBomb() && !this.hasDroppedBomb) {
            if (this.bombsDropped < 2) {
                Bomb bomb = new Bomb(this.getX() + 5, this.getY() + 5);
                this.getBombs().add(bomb);
                this.bombsDropped++;
                this.hasDroppedBomb = true;
            }
        } else if (!this.inputHandler.hasDroppedBomb()) {
            this.hasDroppedBomb = false;
        }
    }

    private void move() {
        if (this.inputHandler.isUp() && this.getY() - this.getSpeed() > 0 && this.collisionDirection != 1) {
            this.setY(this.getY() - this.getSpeed());
        } else if (this.inputHandler.isDown() &&
                this.getY() + PLAYER_TANK_HEIGHT + this.getSpeed() <= GameWindow.WINDOW_HEIGHT && this.collisionDirection
                != 2) {
            this.setY(this.getY() + this.getSpeed());
        } else if (this.inputHandler.isLeft() && this.getX() - this.getSpeed() > 0 && this.collisionDirection != 3) {
            this.setX(this.getX() - this.getSpeed());
        } else if (this.inputHandler.isRight() &&
                this.getX() + PLAYER_TANK_WIDTH + this.getSpeed() <= GameWindow.WINDOW_WIDTH &&
                this.collisionDirection != 4) {
            this.setX(this.getX() + this.getSpeed());
        }

        this.getBoundingBox().setBounds(this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }

    private BufferedImage getPlayerDirectionImage(int lastDirection) {
        if (lastDirection == 1) {
            return Images.playerTankUp;
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