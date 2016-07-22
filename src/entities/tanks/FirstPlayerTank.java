package entities.tanks;

import core.GameWindow;

public class FirstPlayerTank extends PlayerTank {

    private static final int START_X_POSITION =
            (GameWindow.WINDOW_WIDTH / 2) - (GameWindow.WINDOW_HEIGHT / 4);
    private static final int START_Y_POSITION =
            GameWindow.WINDOW_HEIGHT - PlayerTank.PLAYER_TANK_HEIGHT;

    public FirstPlayerTank() {
        super(START_X_POSITION, START_Y_POSITION);
    }

//    @Override
//    public void print(Graphics graphics) {
//
//        if (FirstPayerInputHandler.firstPlayerUp) {
//            graphics.drawImage(Images.firstPlayerTankUP, this.x, this.y, null);
//        } else if (FirstPayerInputHandler.firstPlayerDown) {
//            graphics.drawImage(Images.firstPlayerTankDown, this.x, this.y, null);
//        } else if (FirstPayerInputHandler.firstPlayerLeft) {
//            graphics.drawImage(Images.firstPlayerTankLeft, this.x, this.y, null);
//        } else if (FirstPayerInputHandler.firstPlayerRight) {
//            graphics.drawImage(Images.firstPlayerTankRight, this.x, this.y, null);
//        }
//
//        graphics.setColor(Color.WHITE);
//        for (int i = 0; i < this.getBullets().size(); i++) {
//            this.getBullets().get(i).print(graphics);
//        }
//
//        // Prints only bounding box
//        super.print(graphics);
//    }
}