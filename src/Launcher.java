import contracts.core.Engine;
import core.GameEngine;

public class Launcher {

    public static void main(String[] args) {
        Engine engine = new GameEngine("Tanks");
        engine.run();
    }
}