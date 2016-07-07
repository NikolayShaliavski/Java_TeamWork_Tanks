package entities;

import contracts.Intersectable;
import contracts.Printable;

import java.awt.*;

public abstract class Entity implements Printable, Intersectable {
    protected int x, y;
    protected int width, height;

    private Rectangle boundingBox;

    protected Entity(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        this.boundingBox = new Rectangle(this.x, this.y, this.width, this.height);
    }

    public Rectangle getBoundingBox() {
        return this.boundingBox;
    }

    public void setBoundingBox(int x, int y, int width, int height) {
        this.boundingBox.setBounds(x, y, width, height);
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }
}