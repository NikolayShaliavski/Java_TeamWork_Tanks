package entities.tanks;

import core.GameWindow;
import entities.Entity;
import entities.bullets.Bullet;
import images.Images;

import java.util.ArrayList;
import java.util.List;

public abstract class Tank extends Entity {
    protected int direction;

    private int health;
    private int damage;
    private int speed;

    private List<Bullet> bullets;

    protected Tank(int x, int y, int width, int height, int health, int speed, int damage) {
        super(x, y, width, height);
        this.health = health;
        this.speed = speed;
        this.damage = damage;

        this.bullets = new ArrayList<>();
    }

    public int getHealth() {
        return this.health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getDamage() {
        return this.damage;
    }

    public int getSpeed() {
        return this.speed;
    }

    public List<Bullet> getBullets() {
        return this.bullets;
    }

    public abstract void dealWithCollision();

    protected void removeOutOfRangeBullets() {
        ArrayList<Bullet> toRemove = new ArrayList<>();
        for (int i = 0; i < this.bullets.size(); i++) {
            Bullet currentBullet = bullets.get(i);
            if (currentBullet.getX() < 0 || currentBullet.getX() >= GameWindow.WINDOW_WIDTH ||
                    currentBullet.getY() < 0 || currentBullet.getY() >= GameWindow.WINDOW_HEIGHT) {
                toRemove.add(currentBullet);
            }
        }

        this.bullets.removeAll(toRemove);
    }
}