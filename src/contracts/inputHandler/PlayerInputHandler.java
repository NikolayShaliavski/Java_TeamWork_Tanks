package contracts.inputHandler;

import java.awt.event.KeyListener;

public interface PlayerInputHandler extends KeyListener {

    boolean isUp();

    boolean isDown();

    boolean isRight();

    boolean isLeft();

    boolean hasShoot();

    int getLastDirection();
}
