package gameStates;

import contracts.core.Engine;
import contracts.inputHandler.PlayerInputHandler;

public class OnePlayerState extends GameState {

    private static final int PLAYER_TANKS_COUNT = 1;
    private static final int ENEMIES_COUNT = 10;

    public OnePlayerState(Engine gameEngine,
                          PlayerInputHandler firstPlayerInputHandler) {
        super(gameEngine,
                PLAYER_TANKS_COUNT,
                ENEMIES_COUNT,
                firstPlayerInputHandler);
    }
}