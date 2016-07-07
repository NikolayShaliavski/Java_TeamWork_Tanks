import core.GameEngine;

public class Launcher {
    public static void main(String[] args) {
        GameEngine engine = new GameEngine("Tanks");
        engine.run();
    }
}