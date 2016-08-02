package gameStates;

import contracts.core.Engine;
import inputHandlers.InputHandler;

public class TwoPlayerState extends GameState {

    private static final int PLAYER_TANKS_COUNT = 2;
    private static final int ENEMIES_COUNT = 20;

    public TwoPlayerState(Engine gameEngine,
                          InputHandler firstPlayerInputHandler,
                          InputHandler secondPlayerInputHandler) {
        super(gameEngine,
                PLAYER_TANKS_COUNT,
                ENEMIES_COUNT,
                firstPlayerInputHandler,
                secondPlayerInputHandler);
    }
}