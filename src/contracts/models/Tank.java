package contracts.models;

import contracts.Intersectable;
import contracts.Printable;
import contracts.Updatable;
import entities.bullets.Bullet;

import java.util.List;

public interface Tank extends Updatable, Intersectable, Printable {
    int getHealth();

    void decreaseHealth(int damage);

    int getDamage();

    int getSpeed();

    List<Bullet> getBullets();

    void dealWithCollision();
}