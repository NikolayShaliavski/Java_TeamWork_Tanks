package core;

import contracts.Printable;
import contracts.Updatable;
import gameStates.GameState;
import gameStates.MenuState;
import gameStates.State;
import gameStates.StateManager;
import images.Images;
import input.InputHandler;
import input.MouseHandler;

import javax.sound.sampled.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.io.IOException;

public class GameEngine implements Updatable, Printable {
    private GameWindow gameWindow;

    public Graphics graphics;
    private BufferStrategy bufferStrategy;

    private State currentGameState;

    public static InputHandler inputHandler;
    public static MouseHandler mouseHandler;

    private boolean isRunning;

    public GameEngine(String gameTitle) {
        this.gameWindow = new GameWindow(gameTitle);
        this.init();
        this.isRunning = true;
    }

    public void run() {
        // TODO: Add a thread to make the game move smooth
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

    public void stop() {
        this.isRunning = false;
    }

    public Graphics getGraphics() {
        return this.graphics;
    }

    public GameWindow getGameWindow() {
        return this.gameWindow;
    }

    private void init() {
        this.gameWindow.getCanvas().createBufferStrategy(2);
        this.bufferStrategy = this.gameWindow.getCanvas().getBufferStrategy();
        this.graphics = this.bufferStrategy.getDrawGraphics();

        Images.loadImages();

        inputHandler = new InputHandler(this.gameWindow.getFrame());
        mouseHandler = new MouseHandler(this.gameWindow.getFrame(), this.gameWindow.getCanvas());

        this.currentGameState = new MenuState(this);
        StateManager.setCurrentState(this.currentGameState);
    }
}