package contracts.inputHandler;

import java.awt.event.KeyListener;

public interface MenuInputHandler extends KeyListener {

    boolean isUp();

    boolean isDown();

    boolean isEnter();
}
