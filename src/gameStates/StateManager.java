package gameStates;

/**
 * Class for setting the current state of the game.
 * It's work is to know about about all the other states.
 */
public class StateManager {

    private static State currentState = null;

    public static State getCurrentState() {
        return currentState;
    }

    public static void setCurrentState(State state) {
        currentState = state;
    }
}