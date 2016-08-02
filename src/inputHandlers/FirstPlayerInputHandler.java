package inputHandlers;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Handles the input only for the first player.
 * The keys for moving and shooting with the first player are:
 * <ul>
 * <li>Up - <b>A</b></li>
 * <li>Down - <b>S</b></li>
 * <li>Left - <b>A</b></li>
 * <li>Right - <b>D</b></li>
 * <li>Shoot - <b>Space</b></li>
 * <li>Dropping bomb - <b>Shift</b></li>
 * </ul>
 */
public class FirstPlayerInputHandler extends InputHandler implements KeyListener {

    public FirstPlayerInputHandler(Frame frame) {
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

        if (keyCode == KeyEvent.VK_SPACE) {
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

        if (keyCode == KeyEvent.VK_SPACE) {
            super.setShoot(true);
        }

        if (keyCode == KeyEvent.VK_SHIFT) {
            super.setBomb(true);
        }
    }
}