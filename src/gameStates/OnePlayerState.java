package gameStates;

import contracts.core.Engine;

public class OnePlayerState extends GameState {

    public OnePlayerState(Engine gameEngine) {
        super(gameEngine, 1, 10);
    }
}
