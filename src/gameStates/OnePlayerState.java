package gameStates;

import contracts.core.Engine;
import input.InputHandler;

public class OnePlayerState extends GameState {

    public OnePlayerState(Engine gameEngine,
                          InputHandler firstPlayerInputHandler) {
        super(gameEngine, 1, 10, firstPlayerInputHandler);
    }
}
