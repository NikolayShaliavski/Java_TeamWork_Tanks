package input;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputHandler implements KeyListener {

    public static boolean up = false;
    public static boolean down = false;
    public static boolean right = false;
    public static boolean left = false;
    public static boolean space = false;

    public static int lastDirection = 1;

    public InputHandler(JFrame frame) {
        frame.addKeyListener(this);
    }

    @Override
    public void keyPressed(KeyEvent key) {
        int keyCode = key.getKeyCode();
        if (keyCode == KeyEvent.VK_UP) {
            up = true;
            down = false;
            right = false;
            left = false;

            lastDirection = 1;
        } else if (keyCode == KeyEvent.VK_DOWN) {
            down = true;
            up = false;
            right = false;
            left = false;

            lastDirection = 2;
        } else if (keyCode == KeyEvent.VK_LEFT) {
            left = true;
            down = false;
            up = false;
            right = false;

            lastDirection = 3;
        } else if (keyCode == KeyEvent.VK_RIGHT) {
            right = true;
            down = false;
            left = false;
            up = false;

            lastDirection = 4;
        }

        if (keyCode == KeyEvent.VK_SPACE) {
            space = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent key) {
        int keyCode = key.getKeyCode();
        if (keyCode == KeyEvent.VK_UP) {
            up = false;
        } else if (keyCode == KeyEvent.VK_DOWN) {
            down = false;
        } else if (keyCode == KeyEvent.VK_RIGHT) {
            right = false;
        } else if (keyCode == KeyEvent.VK_LEFT) {
            left = false;
        }

        if (keyCode == KeyEvent.VK_SPACE) {
            space = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent key) {
    }
}