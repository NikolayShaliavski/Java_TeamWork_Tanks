package gameStates;

import contracts.Printable;
import contracts.Updatable;
import contracts.core.Engine;
import contracts.inputHandler.MenuInputHandler;
import core.GameWindow;
import entities.Message;
import images.Images;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.util.List;

public class MenuState extends State implements Updatable, Printable {

    private static final String ONE_PLAYER_MESSAGE = "One Player";
    private static final String TWO_PLAYERS_MESSAGE = "Two Players";
    private static final String HOW_TO_PLAY_MESSAGE = "How To Play";
    private static final String EXIT_MESSAGE = "Exit";

    private static final int DEFAULT_X_MESSAGE = (GameWindow.WINDOW_WIDTH / 2) - (GameWindow.WINDOW_WIDTH / 8);
    private static final int DEFAULT_Y_MESSAGE = (GameWindow.WINDOW_HEIGHT / 2) - (GameWindow.WINDOW_HEIGHT / 7);
    private static final int INDENT = GameWindow.WINDOW_HEIGHT / 6;

    private static final int DEFAULT_TANK_Y = 140;

    private List<Message> messages;
    private int[] positions;

    private MenuInputHandler menuInputHandler;

    private int index;

    private boolean canMove;

    public MenuState(Engine gameEngine,
                     MenuInputHandler menuInputHandler) {
        super(gameEngine);
        this.menuInputHandler = menuInputHandler;
        this.canMove = true;
        this.initPositions();
    }

    @Override
    public void update() {
        if (this.canMove && this.menuInputHandler.isUp() && this.index > 0) {
            this.index--;
            this.canMove = false;
        } else if (this.canMove && this.menuInputHandler.isDown() && this.index < 3) {
            this.index++;
            this.canMove = false;
        }

        if (canMove && this.menuInputHandler.isEnter()) {
            this.executeCommand();
        }
        if (!this.menuInputHandler.isUp() && !this.menuInputHandler.isDown()) {
            this.canMove = true;
        }
    }

    @Override
    public void print(Graphics graphics) {
        graphics.setColor(Color.black);
        //graphics.fillRect(0, 0, GameWindow.WINDOW_WIDTH, GameWindow.WINDOW_HEIGHT);
        graphics.drawImage(Images.menuBack, 0, 0, GameWindow.WINDOW_WIDTH, GameWindow.WINDOW_HEIGHT, null);
        graphics.drawImage(Images.playerTankRight, 170, this.positions[index], null);

        Font font = new Font("Modern No. 20", Font.ITALIC, 25);
        graphics.setColor(Color.WHITE);
        graphics.setFont(font);
        graphics.drawString(ONE_PLAYER_MESSAGE, DEFAULT_X_MESSAGE, DEFAULT_Y_MESSAGE);
        graphics.drawString(TWO_PLAYERS_MESSAGE, DEFAULT_X_MESSAGE, DEFAULT_Y_MESSAGE + INDENT);
        graphics.drawString(HOW_TO_PLAY_MESSAGE, DEFAULT_X_MESSAGE, DEFAULT_Y_MESSAGE + INDENT * 2);
        graphics.drawString(EXIT_MESSAGE, DEFAULT_X_MESSAGE, DEFAULT_Y_MESSAGE + INDENT * 3);
    }

    private void initPositions() {
        this.positions = new int[]{DEFAULT_TANK_Y,
                DEFAULT_TANK_Y + INDENT,
                DEFAULT_TANK_Y + INDENT * 2,
                DEFAULT_TANK_Y + INDENT * 3};
    }

    private void executeCommand() {
        switch (this.index) {
            case 0:
                this.executeOnePlayerCommand();
                break;
            case 1:
                this.executeTwoPlayersCommand();
                break;
            case 2:
                this.executeHowToPlayCommand();
                break;
            case 3:
                this.executeExitCommand();
                break;
        }
    }

    private void executeOnePlayerCommand() {
        StateManager.setCurrentState(new OnePlayerState(this.gameEngine, this.gameEngine.getFirstPlayerInputHandler()));
    }

    private void executeTwoPlayersCommand() {
        StateManager.setCurrentState(new TwoPlayerState(this.gameEngine,
                this.gameEngine.getFirstPlayerInputHandler(),
                this.gameEngine.getSecondPlayerInputHandler()));
    }

    private void executeHowToPlayCommand() {
        // TODO ...
    }

    private void executeExitCommand() {
        JFrame frame = this.gameEngine.getGameWindow().getFrame();
        frame.dispatchEvent(
                new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }
}