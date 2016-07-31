package gameStates;

import contracts.core.Engine;
import contracts.inputHandler.PlayerInputHandler;

public class OnePlayerState extends GameState {

    public OnePlayerState(Engine gameEngine,
                          PlayerInputHandler firstPlayerInputHandler) {
        super(gameEngine, 1, 10, firstPlayerInputHandler);
    }
}
