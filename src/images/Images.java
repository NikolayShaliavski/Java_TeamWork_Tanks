package images;

import java.awt.image.BufferedImage;

public class Images {
    public static BufferedImage menuBack;

    public static BufferedImage playerTankUP;
    public static BufferedImage playerTankDown;
    public static BufferedImage playerTankLeft;
    public static BufferedImage playerTankRight;

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

        playerTankUP = ImageLoader.loadImage("resources\\player_up.png");
        playerTankDown = ImageLoader.loadImage("resources\\player_down.png");
        playerTankRight = ImageLoader.loadImage("resources\\player_right.png");
        playerTankLeft = ImageLoader.loadImage("resources\\player_left.png");

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