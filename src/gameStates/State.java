package gameStates;

import contracts.core.Engine;
import contracts.Printable;
import contracts.Updatable;

public abstract class State implements Updatable, Printable {

    protected Engine gameEngine;

    protected State(Engine gameEngine) {
        this.gameEngine = gameEngine;
    }
}