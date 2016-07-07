package gameStates;

import core.GameEngine;
import core.GameWindow;
import entities.Message;
import images.Images;
import input.MouseHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

public class MenuState extends State {
    private List<Message> messages;

    private static final String ONE_PLAYER_MESSAGE = "One Player";
    private static final String TWO_PLAYERS_MESSAGE = "Two Players";
    private static final String HOW_TO_PLAY_MESSAGE = "How To Play";
    private static final String EXIT_MESSAGE = "Exit";

    private int indent;

    public MenuState(GameEngine gameEngine) {
        super(gameEngine);
        this.initMessages();
    }

    private void initMessages() {
        this.messages = new ArrayList<>();
        Font font = new Font("Modern No. 20", Font.ITALIC, 25);

        int x = (GameWindow.WINDOW_WIDTH / 2) - (GameWindow.WINDOW_WIDTH / 8);
        int y = (GameWindow.WINDOW_HEIGHT / 2) - (GameWindow.WINDOW_HEIGHT / 5);
        this.indent = GameWindow.WINDOW_HEIGHT / 6;

        this.messages.add(new Message(x, y, font, ONE_PLAYER_MESSAGE, this.gameEngine.getGraphics()));

        y += this.indent;
        this.messages.add(new Message(x, y, font, TWO_PLAYERS_MESSAGE, this.gameEngine.getGraphics()));

        y += this.indent;
        this.messages.add(new Message(x, y, font, HOW_TO_PLAY_MESSAGE, this.gameEngine.getGraphics()));

        y += this.indent;
        this.messages.add(new Message(x, y, font, EXIT_MESSAGE, this.gameEngine.getGraphics()));
    }

    @Override
    public void update() {
        if (MouseHandler.mousePressedX != -1 && MouseHandler.mousePressedY != -1) {
            Rectangle mouseClickedBox = new Rectangle(MouseHandler.mousePressedX, MouseHandler.mousePressedY, 1, 1);
            for (Message message : this.messages) {
                if (message.intersect(mouseClickedBox)) {
                    this.executeCommand(message.getMessage());
                }
            }
        }
    }

    @Override
    public void print(Graphics graphics) {
        //graphics.setColor(Color.black);
        //graphics.fillRect(0, 0, GameWindow.WINDOW_WIDTH, GameWindow.WINDOW_HEIGHT);
        graphics.drawImage(Images.menuBack, 0, 0, GameWindow.WINDOW_WIDTH, GameWindow.WINDOW_HEIGHT, null);
        for (Message message : this.messages) {
            message.print(graphics);
        }
    }

    private void executeCommand(String message) {
        if (message.equals(ONE_PLAYER_MESSAGE)) {
            this.executeOnePlayerCommand();
        } else if (message.equals(HOW_TO_PLAY_MESSAGE)) {
            this.executeHowToPlayCommand();
        } else if (message.equals(EXIT_MESSAGE)) {
            this.executeExitCommand();
        }
    }

    private void executeHowToPlayCommand() {
    }

    private void executeExitCommand() {
        JFrame frame = this.gameEngine.getGameWindow().getFrame();
        frame.dispatchEvent(
                new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }

    private void executeOnePlayerCommand() {
        StateManager.setCurrentState(new GameState(this.gameEngine));
    }
}