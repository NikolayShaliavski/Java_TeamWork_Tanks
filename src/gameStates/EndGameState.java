package gameStates;

import contracts.Printable;
import contracts.Updatable;
import contracts.core.Engine;
import core.GameWindow;
import images.Images;

import java.awt.*;

public class EndGameState extends State implements Updatable, Printable {

    private String message;

    public EndGameState(Engine gameEngine, String message) {
        super(gameEngine);
        this.message = message;
    }

    @Override
    public void update() {
        this.gameEngine.stop();
    }

    @Override
    public void print(Graphics graphics) {
        graphics.drawImage(Images.menuBackground, 0, 0, GameWindow.WINDOW_WIDTH, GameWindow.WINDOW_HEIGHT, null);
        int x = (GameWindow.WINDOW_WIDTH / 2) - (GameWindow.WINDOW_WIDTH / 4);
        int y = GameWindow.WINDOW_HEIGHT / 2;

        graphics.setFont(new Font("Modern No. 20", Font.ITALIC, 60));
        if (this.message.equals("You Won!")) {
            graphics.setColor(Color.GREEN);
        } else if (this.message.equals("You Lost!")) {
            graphics.setColor(Color.RED);
        }

        graphics.drawString(this.message, x, y);
    }
}