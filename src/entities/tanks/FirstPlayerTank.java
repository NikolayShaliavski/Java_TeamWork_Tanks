package entities.tanks;

import images.Images;
import input.InputHandler;

import java.awt.*;

public class FirstPlayerTank extends PlayerTank {
    public FirstPlayerTank(int x, int y) {
        super(x, y);
    }

    @Override
    public void print(Graphics graphics) {

        if (InputHandler.firstPlayerUp) {
            graphics.drawImage(Images.firstPlayerTankUP, this.x, this.y, null);
        } else if (InputHandler.firstPlayerDown) {
            graphics.drawImage(Images.firstPlayerTankDown, this.x, this.y, null);
        } else if (InputHandler.firstPlayerLeft) {
            graphics.drawImage(Images.firstPlayerTankLeft, this.x, this.y, null);
        } else if (InputHandler.firstPlayerRight) {
            graphics.drawImage(Images.firstPlayerTankRight, this.x, this.y, null);
        }

        graphics.setColor(Color.WHITE);
        for (int i = 0; i < this.getBullets().size(); i++) {
            this.getBullets().get(i).print(graphics);
        }

        // Prints only bounding box
        super.print(graphics);
    }
}