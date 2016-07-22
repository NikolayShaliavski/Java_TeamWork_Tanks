package entities.tanks;

import contracts.models.Destroyable;
import contracts.models.Tank;
import core.GameWindow;
import entities.AbstractEntity;
import entities.bullets.Bullet;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractTank extends AbstractEntity implements Tank, Destroyable {

    protected int direction;

    private int health;
    private int damage;
    private int speed;

    private List<Bullet> bullets;

    protected AbstractTank(int x, int y, int width, int height, int health, int speed, int damage) {
        super(x, y, width, height);
        this.setHealth(health);
        this.speed = speed;
        this.damage = damage;

        this.bullets = new ArrayList<>();
    }

    @Override
    public int getHealth() {
        return this.health;
    }

    @Override
    public void decreaseHealth(int damage) {
        this.setHealth(this.getHealth() - damage);
    }

    @Override
    public int getDamage() {
        return this.damage;
    }

    @Override
    public int getSpeed() {
        return this.speed;
    }

    @Override
    public List<Bullet> getBullets() {
        return this.bullets;
    }

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

    private void setHealth(int health) {
        this.health = health;
    }
}