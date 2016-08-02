package core;

import contracts.Printable;
import contracts.Updatable;
import contracts.core.Engine;
import contracts.inputHandler.MenuInputHandler;
import gameStates.MenuState;
import gameStates.State;
import gameStates.StateManager;
import images.Images;
import inputHandlers.*;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class GameEngine implements Engine, Runnable, Updatable, Printable {

    private GameWindow gameWindow;

    public Graphics graphics;
    private BufferStrategy bufferStrategy;

    private State currentGameState;

    private MenuInputHandler menuInputHandler;

    private boolean isRunning;

    public GameEngine(String gameTitle) {
        this.gameWindow = new GameWindow(gameTitle);
        this.init();
        this.isRunning = true;
    }

    @Override
    public void run() {
        int fps = 70;
        double ticksPerFrame = 1_000_000_000 / fps;
        double delta = 0;
        long now;
        long lastTimeTicked = System.nanoTime();

        while (this.isRunning) {
            now = System.nanoTime();
            delta += (now - lastTimeTicked) / ticksPerFrame;
            lastTimeTicked = now;
            if (delta >= 1) {
                this.update();
                this.print(this.graphics);
                delta--;
            }
        }
    }

    @Override
    public void update() {
        if (StateManager.getCurrentState() != null) {
            StateManager.getCurrentState().update();
        }
    }

    @Override
    public void print(Graphics graphics) {
        graphics = this.bufferStrategy.getDrawGraphics();

        this.graphics.clearRect(0, 0, 650, 450);

        // Background image
        this.graphics.setColor(Color.BLACK);
        this.graphics.fillRect(0, 0, GameWindow.WINDOW_WIDTH, GameWindow.WINDOW_HEIGHT);

        if (StateManager.getCurrentState() != null) {
            StateManager.getCurrentState().print(graphics);
        }

        this.bufferStrategy.show();
        graphics.dispose();
    }

    @Override
    public void stop() {
        this.isRunning = false;
    }

    @Override
    public Graphics getGraphics() {
        return this.graphics;
    }

    @Override
    public GameWindow getGameWindow() {
        return this.gameWindow;
    }

    private void init() {
        this.gameWindow.getCanvas().createBufferStrategy(2);
        this.bufferStrategy = this.gameWindow.getCanvas().getBufferStrategy();
        this.graphics = this.bufferStrategy.getDrawGraphics();

        Images.loadImages();

        this.menuInputHandler = new MenuInputHandlerImpl(this.gameWindow.getFrame());

        this.currentGameState = new MenuState(this, this.menuInputHandler);
        StateManager.setCurrentState(this.currentGameState);
    }
}