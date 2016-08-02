package inputHandlers;

import contracts.inputHandler.PlayerInputHandler;

import java.awt.*;

public abstract class InputHandler implements PlayerInputHandler {

    private boolean up;
    private boolean down;
    private boolean right;
    private boolean left;
    private boolean shoot;
    private boolean bomb;

    private int lastDirection;

    protected InputHandler(Frame frame) {
        frame.addKeyListener(this);
        this.lastDirection = 1;
    }

    @Override
    public boolean isUp() {
        return this.up;
    }

    protected void setUp(boolean up) {
        this.up = up;
    }

    @Override
    public boolean isDown() {
        return down;
    }

    protected void setDown(boolean down) {
        this.down = down;
    }

    @Override
    public boolean isRight() {
        return right;
    }

    protected void setRight(boolean right) {
        this.right = right;
    }

    @Override
    public boolean isLeft() {
        return left;
    }

    protected void setLeft(boolean left) {
        this.left = left;
    }

    @Override
    public boolean hasShoot() {
        return shoot;
    }

    protected void setShoot(boolean shoot) {
        this.shoot = shoot;
    }

    @Override
    public boolean hasDroppedBomb() {
        return this.bomb;
    }

    protected void setBomb(boolean bomb) {
        this.bomb = bomb;
    }

    @Override
    public int getLastDirection() {
        return lastDirection;
    }

    protected void setLastDirection(int lastDirection) {
        this.lastDirection = lastDirection;
    }
}