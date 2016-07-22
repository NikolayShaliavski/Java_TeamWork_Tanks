package input;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class FirstPayerInputHandler implements KeyListener {

    public static Boolean firstPlayerUp = false;
    public static Boolean firstPlayerDown = false;
    public static Boolean firstPlayerLeft = false;
    public static Boolean firstPlayerRight = false;
    public static Boolean firstPlayerShoot = false;

    public static int lastDirection = 1;

    public FirstPayerInputHandler(Frame frame) {
        frame.addKeyListener(this);
    }

    @Override
    public void keyPressed(KeyEvent key) {
        int keyCode = key.getKeyCode();
        this.checkForPressedDirectionForFirstPlayer(keyCode);
    }

    @Override
    public void keyReleased(KeyEvent key) {
        int keyCode = key.getKeyCode();
        this.checkForReleasedDirectionForFirstPlayer(keyCode);
    }

    @Override
    public void keyTyped(KeyEvent key) {
    }

    private void checkForReleasedDirectionForFirstPlayer(int keyCode) {
        if (keyCode == KeyEvent.VK_UP) {
            firstPlayerUp = false;
        } else if (keyCode == KeyEvent.VK_DOWN) {
            firstPlayerDown = false;
        } else if (keyCode == KeyEvent.VK_RIGHT) {
            firstPlayerRight = false;
        } else if (keyCode == KeyEvent.VK_LEFT) {
            firstPlayerLeft = false;
        }

        if (keyCode == KeyEvent.VK_SPACE) {
            firstPlayerShoot = false;
        }
    }

    private void checkForPressedDirectionForFirstPlayer(int keyCode) {
        if (keyCode == KeyEvent.VK_UP) {
            firstPlayerUp = true;
            firstPlayerDown = false;
            firstPlayerRight = false;
            firstPlayerLeft = false;
            
            lastDirection = 1;
        } else if (keyCode == KeyEvent.VK_DOWN) {
            firstPlayerDown = true;
            firstPlayerUp = false;
            firstPlayerRight = false;
            firstPlayerLeft = false;

            lastDirection = 2;
        } else if (keyCode == KeyEvent.VK_LEFT) {
            firstPlayerLeft = true;
            firstPlayerDown = false;
            firstPlayerUp = false;
            firstPlayerRight = false;

            lastDirection = 3;
        } else if (keyCode == KeyEvent.VK_RIGHT) {
            firstPlayerRight = true;
            firstPlayerDown = false;
            firstPlayerLeft = false;
            firstPlayerUp = false;

            lastDirection = 4;
        }

        if (keyCode == KeyEvent.VK_SPACE) {
            firstPlayerShoot = true;
        }
    }
}