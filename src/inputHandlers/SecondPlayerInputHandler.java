package inputHandlers;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Handles the input only for the second player.
 * The keys for moving and shooting with the first player are:
 * <ul>
 * <li>Up - <b>Up Arrow</b></li>
 * <li>Down - <b>Down Arrow</b></li>
 * <li>Left - <b>Left Arrow</b></li>
 * <li>Right - <b>Right Arrow</b></li>
 * <li>Shoot - <b>Control</b></li>
 * <li>Dropping bomb - <b>Enter</b></li>
 * </ul>
 */
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
        if (keyCode == KeyEvent.VK_UP) {
            super.setUp(false);
        } else if (keyCode == KeyEvent.VK_DOWN) {
            super.setDown(false);
        } else if (keyCode == KeyEvent.VK_RIGHT) {
            super.setRight(false);
        } else if (keyCode == KeyEvent.VK_LEFT) {
            super.setLeft(false);
        }

        if (keyCode == KeyEvent.VK_CONTROL) {
            super.setShoot(false);
        }

        if (keyCode == KeyEvent.VK_ENTER) {
            super.setBomb(false);
        }
    }

    private void checkKeyPressed(int keyCode) {
        if (keyCode == KeyEvent.VK_UP) {
            super.setUp(true);
            super.setDown(false);
            super.setRight(false);
            super.setLeft(false);

            super.setLastDirection(1);
        } else if (keyCode == KeyEvent.VK_DOWN) {
            super.setDown(true);
            super.setUp(false);
            super.setRight(false);
            super.setLeft(false);

            super.setLastDirection(2);
        } else if (keyCode == KeyEvent.VK_LEFT) {
            super.setLeft(true);
            super.setUp(false);
            super.setDown(false);
            super.setRight(false);

            super.setLastDirection(3);
        } else if (keyCode == KeyEvent.VK_RIGHT) {
            super.setRight(true);
            super.setUp(false);
            super.setDown(false);
            super.setLeft(false);

            super.setLastDirection(4);
        }

        if (keyCode == KeyEvent.VK_CONTROL) {
            super.setShoot(true);
        }
        if (keyCode == KeyEvent.VK_ENTER) {
            super.setBomb(true);
        }
    }
}