package input;

import java.awt.*;
import java.awt.event.KeyEvent;

public class SecondPlayerInputHandler extends InputHandler {

    public SecondPlayerInputHandler(Frame frame) {
        super(frame);
    }

    @Override
    public void keyPressed(KeyEvent key) {
        int keyCode = key.getKeyCode();
        this.checkKeyPressed(keyCode);
    }

    @Override
    public void keyReleased(KeyEvent key) {
        int keyCode = key.getKeyCode();
        this.checkKeyReleased(keyCode);
    }

    @Override
    public void keyTyped(KeyEvent key) {
    }

    private void checkKeyReleased(int keyCode) {
        if (keyCode == KeyEvent.VK_W) {
            super.setUp(false);
        } else if (keyCode == KeyEvent.VK_S) {
            super.setDown(false);
        } else if (keyCode == KeyEvent.VK_D) {
            super.setRight(false);
        } else if (keyCode == KeyEvent.VK_A) {
            super.setLeft(false);
        }

        if (keyCode == KeyEvent.VK_CONTROL) {
            super.setShoot(false);
        }
        if (keyCode == KeyEvent.VK_SHIFT) {
            super.setBomb(false);
        }
    }

    private void checkKeyPressed(int keyCode) {
        if (keyCode == KeyEvent.VK_W) {
            super.setUp(true);
            super.setDown(false);
            super.setRight(false);
            super.setLeft(false);

            super.setLastDirection(1);
        } else if (keyCode == KeyEvent.VK_S) {
            super.setDown(true);
            super.setUp(false);
            super.setRight(false);
            super.setLeft(false);

            super.setLastDirection(2);
        } else if (keyCode == KeyEvent.VK_A) {
            super.setLeft(true);
            super.setUp(false);
            super.setDown(false);
            super.setRight(false);

            super.setLastDirection(3);
        } else if (keyCode == KeyEvent.VK_D) {
            super.setRight(true);
            super.setUp(false);
            super.setDown(false);
            super.setLeft(false);

            super.setLastDirection(4);
        }

        if (keyCode == KeyEvent.VK_CONTROL) {
            super.setShoot(true);
        }
        if (keyCode == KeyEvent.VK_SHIFT) {
            super.setBomb(true);
        }
    }
}
