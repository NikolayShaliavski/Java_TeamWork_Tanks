package images;

import java.awt.image.BufferedImage;

/**
 * Repository for all the images in the game.
 */
public class Images {

    public static BufferedImage menuBackground;

    public static BufferedImage eagle;

    public static BufferedImage playerTankUp;
    public static BufferedImage playerTankDown;
    public static BufferedImage playerTankLeft;
    public static BufferedImage playerTankRight;

    public static BufferedImage enemyTankUp;
    public static BufferedImage enemyTankDown;
    public static BufferedImage enemyTankLeft;
    public static BufferedImage enemyTankRight;

    public static BufferedImage bullet;

    public static BufferedImage steel;
    public static BufferedImage brick;

    public static BufferedImage freeze;
    public static BufferedImage bomb;

    /**
     * Initializes all the images, witch are used in the game.
     * If this method is not called, NullPointerException will be thrown.
     */
    public static void loadImages() {
        menuBackground = ImageLoader.loadImage("resources\\menu_back.jpg");

        playerTankUp = ImageLoader.loadImage("resources\\player_up.png");
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

        bomb = ImageLoader.loadImage("resources\\bomb.png");
    }
}