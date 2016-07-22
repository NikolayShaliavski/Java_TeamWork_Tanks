package images;

import java.awt.image.BufferedImage;

public class Images {
    public static BufferedImage menuBack;

    public static BufferedImage firstPlayerTankUP;
    public static BufferedImage firstPlayerTankDown;
    public static BufferedImage firstPlayerTankLeft;
    public static BufferedImage firstPlayerTankRight;

    public static BufferedImage secondPlayerTankUP;
    public static BufferedImage secondPlayerTankDown;
    public static BufferedImage secondPlayerTankLeft;
    public static BufferedImage secondPlayerTankRight;

    public static BufferedImage enemyTankUp;
    public static BufferedImage enemyTankDown;
    public static BufferedImage enemyTankLeft;
    public static BufferedImage enemyTankRight;

    public static BufferedImage steel;
    public static BufferedImage brick;

    public static BufferedImage eagle;

    public static BufferedImage bullet;

    public static BufferedImage freeze;

    //public static BufferedImage explosion;

    public static void loadImages() {
        menuBack = ImageLoader.loadImage("resources\\menu_back.jpg");

        firstPlayerTankUP = ImageLoader.loadImage("resources\\player_up.png");
        firstPlayerTankDown = ImageLoader.loadImage("resources\\player_down.png");
        firstPlayerTankRight = ImageLoader.loadImage("resources\\player_right.png");
        firstPlayerTankLeft = ImageLoader.loadImage("resources\\player_left.png");

        enemyTankUp = ImageLoader.loadImage("resources\\normal_enemy_up.png");
        enemyTankDown = ImageLoader.loadImage("resources\\normal_enemy_down.png");
        enemyTankLeft = ImageLoader.loadImage("resources\\normal_enemy_left.png");
        enemyTankRight = ImageLoader.loadImage("resources\\normal_enemy_right.png");

        eagle = ImageLoader.loadImage("resources\\eagle.png");

        brick = ImageLoader.loadImage("resources\\brick.png");
        steel = ImageLoader.loadImage("resources\\steel.png");

        bullet = ImageLoader.loadImage("resources\\bullet.png");

        freeze = ImageLoader.loadImage("resources\\freeze.png");

        //explosion = ImageLoader.loadImage("resources\\explosion.png");
    }

//    public static BufferedImage crop(int x, int y, int width, int height) {
//        return explosion.getSubimage(x, y, width, height);
//    }
}