package gameStates;

import contracts.Printable;
import contracts.Updatable;
import core.GameEngine;

public abstract class State implements Updatable, Printable {
    protected GameEngine gameEngine;

    protected State(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
    }
}