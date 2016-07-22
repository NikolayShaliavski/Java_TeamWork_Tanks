package entities.tanks;

import core.GameWindow;
import input.InputHandler;

public class SecondPlayerTank extends PlayerTank {

    private static final int START_X_POSITION =
            (GameWindow.WINDOW_WIDTH / 2 + 200) - (GameWindow.WINDOW_HEIGHT / 4);
    private static final int START_Y_POSITION =
            GameWindow.WINDOW_HEIGHT - PlayerTank.PLAYER_TANK_HEIGHT;

    public SecondPlayerTank(InputHandler inputHandler) {
        super(START_X_POSITION, START_Y_POSITION, inputHandler);
    }
}