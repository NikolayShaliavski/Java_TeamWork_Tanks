package gameStates;

import contracts.core.Engine;
import input.InputHandler;

public class TwoPlayerState extends GameState {

    public TwoPlayerState(Engine gameEngine,
                          InputHandler firstPlayerInputHandler,
                          InputHandler secondPlayerInputHandler) {
        super(gameEngine, 2, 20, firstPlayerInputHandler, secondPlayerInputHandler);
    }
}