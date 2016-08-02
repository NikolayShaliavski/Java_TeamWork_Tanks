package entities.tanks;

import contracts.inputHandler.PlayerInputHandler;
import core.GameWindow;

public class FirstPlayerTank extends PlayerTank {

    private static final int START_X_POSITION =
            (GameWindow.WINDOW_WIDTH / 2) - (GameWindow.WINDOW_HEIGHT / 4);
    private static final int START_Y_POSITION =
            GameWindow.WINDOW_HEIGHT - PlayerTank.PLAYER_TANK_HEIGHT;

    public FirstPlayerTank(PlayerInputHandler inputHandler) {
        super(START_X_POSITION, START_Y_POSITION, inputHandler);
    }
}