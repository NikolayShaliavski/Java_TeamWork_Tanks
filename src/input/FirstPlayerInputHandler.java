package input;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

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
        if (keyCode == KeyEvent.VK_UP) {
            super.setUp(false);
        } else if (keyCode == KeyEvent.VK_DOWN) {
            super.setDown(false);
        } else if (keyCode == KeyEvent.VK_RIGHT) {
            super.setRight(false);
        } else if (keyCode == KeyEvent.VK_LEFT) {
            super.setLeft(false);
        }

        if (keyCode == KeyEvent.VK_SPACE) {
            super.setShoot(false);
        }

        if (keyCode == KeyEvent.VK_M) {
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

        if (keyCode == KeyEvent.VK_SPACE){
            super.setShoot(true);
        }
        if (keyCode == KeyEvent.VK_M) {
            super.setBomb(true);
        }
    }


}