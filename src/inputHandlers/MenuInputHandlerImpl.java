package inputHandlers;

import contracts.inputHandler.MenuInputHandler;

import javax.swing.*;
import java.awt.event.KeyEvent;

/**
 * Handles the input only at the menu state.
 * The keys for moving and selecting options are:
 * <ul>
 * <li>Up - <b>Up Arrow</b></li>
 * <li>Down - <b>Down Arrow</b></li>
 * <li>Choosing option - <b>Enter</b></li>
 * </ul>
 */
public class MenuInputHandlerImpl implements MenuInputHandler {

    private boolean up;
    private boolean down;
    private boolean enter;

    public MenuInputHandlerImpl(JFrame frame) {
        frame.addKeyListener(this);
    }

    @Override
    public boolean isUp() {
        return this.up;
    }

    @Override
    public boolean isDown() {
        return this.down;
    }

    @Override
    public boolean isEnter() {
        return this.enter;
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
    public void keyTyped(KeyEvent e) {
    }

    private void checkKeyPressed(int keyCode) {
        if (keyCode == KeyEvent.VK_UP) {
            this.up = true;
            this.down = false;
        } else if (keyCode == KeyEvent.VK_DOWN) {
            this.down = true;
            this.up = false;
        } else if (keyCode == KeyEvent.VK_ENTER) {
            this.enter = true;
        }
    }

    private void checkKeyReleased(int keyCode) {
        if (keyCode == KeyEvent.VK_UP) {
            this.up = false;
        } else if (keyCode == KeyEvent.VK_DOWN) {
            this.down = false;
        } else if (keyCode == KeyEvent.VK_ENTER) {
            this.enter = false;
        }
    }
}