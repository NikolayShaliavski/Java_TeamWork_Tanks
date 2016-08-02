package entities;

import contracts.models.Entity;
import contracts.Intersectable;
import contracts.Printable;

import java.awt.*;

public abstract class AbstractEntity implements Entity, Printable, Intersectable {

    private int x, y;
    private int width, height;

    private Rectangle boundingBox;

    protected AbstractEntity(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        this.boundingBox = new Rectangle(this.x, this.y, this.width, this.height);
    }

    @Override
    public Rectangle getBoundingBox() {
        return this.boundingBox;
    }

    @Override
    public int getX() {
        return this.x;
    }

    @Override
    public int getY() {
        return this.y;
    }

    @Override
    public int getWidth() {
        return this.width;
    }

    @Override
    public int getHeight() {
        return this.height;
    }

    protected void setX(int x) {
        this.x = x;
    }

    protected void setY(int y) {
        this.y = y;
    }

    protected void setWidth(int width) {
        this.width = width;
    }

    protected void setHeight(int height) {
        this.height = height;
    }

    protected void setBoundingBox(int x, int y, int width, int height) {
        this.boundingBox.setBounds(x, y, width, height);
    }
}