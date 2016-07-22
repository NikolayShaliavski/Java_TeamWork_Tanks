package gameStates;

import contracts.core.Engine;

public class TwoPlayerState extends GameState {

    public TwoPlayerState(Engine gameEngine) {
        super(gameEngine, 2, 20);
    }
}