package gameStates;

import core.GameEngine;
import core.GameWindow;
import entities.Eagle;
import entities.bonuses.Bonus;
import entities.bullets.Bullet;
import entities.obsticles.BrickField;
import entities.obsticles.bricks.Brick;
import entities.obsticles.bricks.Steel;
import entities.obsticles.walls.BrickWall;
import entities.obsticles.walls.SteelWall;
import entities.tanks.EnemyTank;
import entities.tanks.PlayerTank;
import entities.tanks.Tank;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameState extends State {

    private static final int TOTAL_NUMBER_OF_ENEMIES = 10;

    private Eagle eagle;
    private PlayerTank playerTank;
    private Bonus bonus;

    private List<EnemyTank> enemyTanks;
    private int enemiesCount;

    private BrickField brickField;

    private int ticksCounter;

    private int bonusCounter;
    private boolean bonusExist;

    private int freezeCounter;
    public boolean enemiesFreeze;

    private int timeToGetBonus;

    public GameState(GameEngine gameEngine) {
        super(gameEngine);
        this.brickField = new BrickField();
        this.initEagle();
        this.initWallsAroundEagle();
        this.initBrickOnField();
        this.initPlayerTank();
        this.initEnemyTanks();
    }

    @Override
    public void update() {
        this.movePlayer();
        if (!this.enemiesFreeze) {
            this.moveEnemies();
        }

        this.checkCollisionBetweenPlayerBulletAndEnemies();
        this.checkCollisionBetweenEnemyBulletAndPlayer();
        this.checkCollisionBetweenBulletsAndEagle();
        this.checkCollisionBetweenPlayerBulletAndObstacles();
        this.checkCollisionBetweenEnemyBulletAndObstacles();
        this.checkCollisionBetweenBullets();
        this.removeDeadEnemies();

        this.spawnEnemy();

        this.checkIfGameEnded();

        this.checkBonus();
        this.enemiesFreeze();
    }

    @Override
    public void print(Graphics graphics) {
        this.brickField.print(graphics);

        this.eagle.print(graphics);

        for (EnemyTank enemyTank : this.enemyTanks) {
            enemyTank.print(graphics);
            for (Bullet enemyBullet : enemyTank.getBullets()) {
                enemyBullet.print(graphics);
            }
        }

        this.playerTank.print(graphics);
        for (Bullet playerBullet : this.playerTank.getBullets()) {
            playerBullet.print(graphics);
        }
        if (bonusExist) {
            this.bonus.print(graphics);
        }
    }

    private void initWallsAroundEagle() {
        int sideWallsRows = Eagle.EAGLE_HEIGHT / Brick.BRICK_HEIGHT;
        int sideWallsCols = 2;
        int sideBricksY = GameWindow.WINDOW_HEIGHT - Brick.BRICK_HEIGHT;
        int leftBrickX = (GameWindow.WINDOW_WIDTH / 2) - (Brick.BRICK_WIDTH) - (Brick.BRICK_WIDTH * sideWallsCols);
        BrickWall leftWall = new BrickWall(leftBrickX, sideBricksY, sideWallsRows, sideWallsCols, false);
        this.brickField.addBrickWall(leftWall);

        int rightBrickX = leftBrickX + (Brick.BRICK_WIDTH * sideWallsCols) + Eagle.EAGLE_WIDTH + Brick.BRICK_WIDTH / 2;
        BrickWall rightWall = new BrickWall(rightBrickX, sideBricksY, sideWallsRows, sideWallsCols, false);
        this.brickField.addBrickWall(rightWall);

        int upperWallRows = 2;
        int upperWallCols = 8;//(Eagle.EAGLE_WIDTH / Brick.BRICK_WIDTH) + ((Brick.BRICK_WIDTH * sideWallsCols) );
        int upperBrickY = GameWindow.WINDOW_HEIGHT - Eagle.EAGLE_HEIGHT - (Brick.BRICK_HEIGHT * upperWallRows);
        int upperBrickX = leftBrickX - Brick.BRICK_WIDTH;
        BrickWall upperWall = new BrickWall(upperBrickX, upperBrickY, upperWallRows, upperWallCols, true);
        this.brickField.addBrickWall(upperWall);
    }

    private void initBrickOnField() {
        int space = PlayerTank.PLAYER_TANK_WIDTH / 2;
        int x = PlayerTank.PLAYER_TANK_WIDTH + space;
        int y = PlayerTank.PLAYER_TANK_HEIGHT + PlayerTank.PLAYER_TANK_WIDTH / 2;

        int rows = 11;
        int cols = 3;
        this.buildHorizontalBricks(space, x, y, rows - 1, cols);

        x = 0;
        y += (Brick.BRICK_HEIGHT * rows) + PlayerTank.PLAYER_TANK_HEIGHT;
        rows = 2;
        cols = 10;
        this.buildVerticalBricks(space, x, y, rows, cols);
    }

    private void buildVerticalBricks(int space, int x, int y, int rows, int cols) {
        int rowIndent = Brick.BRICK_HEIGHT * rows + PlayerTank.PLAYER_TANK_HEIGHT + space;
        BrickWall leftBrickWall = new BrickWall(x, y, rows, cols, true);
        BrickWall leftBrickWall2 = new BrickWall(x, y + rowIndent, rows, cols, true);
        this.brickField.addBrickWall(leftBrickWall);
        this.brickField.addBrickWall(leftBrickWall2);

        BrickWall middleBrickWall = null;
        int colIndent = PlayerTank.PLAYER_TANK_WIDTH + space + (Brick.BRICK_WIDTH * cols);
        cols = 5;
        for (int i = 0; i < 2; i++) {
            x = colIndent;
            for (int j = 0; j < 2; j++) {
                middleBrickWall = new BrickWall(x, y, rows, cols, true);
                this.brickField.addBrickWall(middleBrickWall);
                x += PlayerTank.PLAYER_TANK_WIDTH + space + (Brick.BRICK_WIDTH * cols);
            }

            y += rowIndent;
        }

        cols = 10;
        x = leftBrickWall.getWidth() + (middleBrickWall.getWidth() * 2) + (PlayerTank.PLAYER_TANK_WIDTH + space) * 3;
        y = leftBrickWall.getY();
        BrickWall rightBrickWall = new BrickWall(x + 5, y, rows, cols + 2, true);
        BrickWall rightBrickWall2 = new BrickWall(x + 5, y + rightBrickWall.getHeight() + PlayerTank
                .PLAYER_TANK_HEIGHT + space, rows, cols + 2, true);
        this.brickField.addBrickWall(rightBrickWall);
        this.brickField.addBrickWall(rightBrickWall2);
    }

    private void buildSteelWalls(int x, int y) {
        SteelWall steelWall = new SteelWall(x, y, 2, 5, true);
        this.brickField.addSteelWall(steelWall);
    }

    private void buildHorizontalBricks(int space, int x, int y, int rows, int cols) {
        for (int i = 0; i < 6; i++) {
            BrickWall brickWall = new BrickWall(x, y, rows, cols, true);
            this.brickField.addBrickWall(brickWall);
            if (i + 1 == 3) {
                int steelY = y + brickWall.getHeight() / 3 + Brick.BRICK_HEIGHT;
                this.buildSteelWalls(x + Brick.BRICK_WIDTH * cols, steelY);
                x += PlayerTank.PLAYER_TANK_WIDTH + space + (Brick.BRICK_WIDTH * (cols + 2));
            } else {
                x += PlayerTank.PLAYER_TANK_WIDTH + space + (Brick.BRICK_WIDTH * cols);
            }
        }
    }

    private void initEagle() {
        this.eagle = new Eagle(
                (GameWindow.WINDOW_WIDTH / 2) - (Eagle.EAGLE_WIDTH / 2),
                GameWindow.WINDOW_HEIGHT - Eagle.EAGLE_HEIGHT);
    }

    private void initPlayerTank() {
        int x = (GameWindow.WINDOW_WIDTH / 2) - (GameWindow.WINDOW_HEIGHT / 4);
        int y = GameWindow.WINDOW_HEIGHT - PlayerTank.PLAYER_TANK_HEIGHT;
        this.playerTank = new PlayerTank(x, y);
    }

    private void initEnemyTanks() {
        this.enemyTanks = new ArrayList<>();

        this.enemyTanks.add(new EnemyTank(0, 0));
        this.enemyTanks.add(new EnemyTank(
                GameWindow.WINDOW_WIDTH - EnemyTank.ENEMY_TANK_WIDTH, 0));

        this.enemiesCount += 2;
    }

    private void removeDeadEnemies() {
        for (int i = 0; i < this.enemyTanks.size(); i++) {
            if (this.enemyTanks.get(i).getHealth() <= 0) {

                timeToGetBonus++;
                if (timeToGetBonus == 3) {
                    int bonusX = this.enemyTanks.get(i).getX() + this.enemyTanks.get(i).ENEMY_TANK_WIDTH / 4;
                    int bonusY = this.enemyTanks.get(i).getY() + this.enemyTanks.get(i).ENEMY_TANK_HEIGHT / 4;
                    this.bonus = new Bonus(bonusX, bonusY);
                    bonusExist = true;
                    timeToGetBonus = 0;
                }
                this.enemyTanks.remove(i);
            }
        }
    }

    private void movePlayer() {
        boolean canMovePlayer = true;
        for (EnemyTank enemyTank : this.enemyTanks) {
            if (this.playerTank.intersect(enemyTank.getBoundingBox())) {
                canMovePlayer = false;
                break;
            }
        }

        for (BrickWall brickWall : this.brickField.getBrickWalls()) {
            for (Brick brick : brickWall.getBricks()) {
                if (this.playerTank.intersect(brick.getBoundingBox())) {
                    canMovePlayer = false;
                    break;
                }
            }
            if (!canMovePlayer) {
                break;
            }
        }

        for (SteelWall steelWall : this.brickField.getSteelWallsWalls()) {
            for (Steel steel : steelWall.getSteel()) {
                if (this.playerTank.intersect(steel.getBoundingBox())) {
                    canMovePlayer = false;
                    break;
                }
            }
        }

        if (canMovePlayer) {
            this.playerTank.update();
        } else {
            this.playerTank.dealWithCollision();
        }

        if (bonusExist) {
            if (this.playerTank.intersect(this.bonus.getBoundingBox())) {
                enemiesFreeze = true;
                bonusExist = false;
                bonusCounter = 0;
                this.bonus = null;
            }
        }
    }

    private void moveEnemies() {
        List<EnemyTank> toUpdate = new ArrayList<>();
        List<EnemyTank> toDealWithCollision = new ArrayList<>();

        for (EnemyTank enemyTank : this.enemyTanks) {
            if (enemyTank.intersect(this.playerTank.getBoundingBox())) {
                toDealWithCollision.add(enemyTank);
            } else {
                toUpdate.add(enemyTank);
            }
        }

        for (BrickWall brickWall : this.brickField.getBrickWalls()) {
            for (Brick brick : brickWall.getBricks()) {
                for (EnemyTank enemyTank : this.enemyTanks) {
                    if (enemyTank.intersect(brick.getBoundingBox())) {
                        toDealWithCollision.add(enemyTank);
                        toUpdate.remove(enemyTank);
                    }
                }
            }
        }

        for (SteelWall steelWall : this.brickField.getSteelWallsWalls()) {
            for (Steel steel : steelWall.getSteel()) {
                for (EnemyTank enemyTank : this.enemyTanks) {
                    if (enemyTank.intersect(steel.getBoundingBox())) {
                        toDealWithCollision.add(enemyTank);
                        toUpdate.remove(enemyTank);
                    }
                }
            }
        }

        for (int i = 0; i < this.enemyTanks.size(); i++) {
            EnemyTank enemyTank = this.enemyTanks.get(i);
            for (int j = i + 1; j < this.enemyTanks.size(); j++) {
                if (enemyTank.intersect(this.enemyTanks.get(j).getBoundingBox())) {
                    toDealWithCollision.add(enemyTank);
                    toDealWithCollision.add(this.enemyTanks.get(j));
                    toUpdate.remove(enemyTank);
                    toUpdate.remove(this.enemyTanks.get(j));
                }
            }
        }
        toUpdate.forEach(EnemyTank::update);
        toDealWithCollision.forEach(EnemyTank::dealWithCollision);
    }

    private void checkCollisionBetweenEnemyBulletAndObstacles() {
        for (EnemyTank enemyTank : this.enemyTanks) {
            List<Bullet> bulletsToRemove = new ArrayList<>();
            for (Bullet bullet : enemyTank.getBullets()) {
                for (BrickWall brickWall : this.brickField.getBrickWalls()) {
                    List<Brick> bricksToRemove = new ArrayList<>();
                    for (Brick brick : brickWall.getBricks()) {
                        if (bullet.intersect(brick.getBoundingBox())) {
                            bricksToRemove.add(brick);
                            bulletsToRemove.add(bullet);
                        }
                    }

                    brickWall.getBricks().removeAll(bricksToRemove);
                    bricksToRemove.clear();
                }
            }

            enemyTank.getBullets().removeAll(bulletsToRemove);
        }

        for (EnemyTank enemyTank : this.enemyTanks) {
            List<Bullet> bulletsToRemove = new ArrayList<>();
            for (Bullet bullet : enemyTank.getBullets()) {
                for (SteelWall steelWall : this.brickField.getSteelWallsWalls()) {
                    for (Steel steel : steelWall.getSteel()) {
                        if (bullet.intersect(steel.getBoundingBox())) {
                            bulletsToRemove.add(bullet);
                        }
                    }
                }
            }

            enemyTank.getBullets().removeAll(bulletsToRemove);
        }
    }

    private void checkCollisionBetweenEnemyBulletAndPlayer() {
        for (int i = 0; i < this.enemyTanks.size(); i++) {
            EnemyTank enemyTank = this.enemyTanks.get(i);

            List<Bullet> toRemove = new ArrayList<>();
            for (int j = 0; j < enemyTank.getBullets().size(); j++) {
                Bullet enemyBullet = enemyTank.getBullets().get(j);
                if (enemyBullet.intersect(this.playerTank.getBoundingBox())) {
                    this.playerTank.setHealth(this.playerTank.getHealth() - enemyTank.getDamage());
                    toRemove.add(enemyTank.getBullets().get(j));
                }
            }

            enemyTank.getBullets().removeAll(toRemove);
        }
    }

    private void checkCollisionBetweenPlayerBulletAndEnemies() {
        List<Bullet> toRemove = new ArrayList<>();
        for (int i = 0; i < this.playerTank.getBullets().size(); i++) {
            Bullet playerBullet = this.playerTank.getBullets().get(i);
            for (int j = 0; j < this.enemyTanks.size(); j++) {
                Tank enemyTank = this.enemyTanks.get(j);
                if (playerBullet.intersect(enemyTank.getBoundingBox())) {
                    enemyTank.setHealth(enemyTank.getHealth() - this.playerTank.getDamage());
                    toRemove.add(this.playerTank.getBullets().get(i));
                }
            }
        }

        this.playerTank.getBullets().removeAll(toRemove);
    }

    private void checkCollisionBetweenPlayerBulletAndObstacles() {
        List<Brick> bricksToRemove = new ArrayList<>();
        List<Bullet> bulletsToRemove = new ArrayList<>();

        for (BrickWall brickWall : this.brickField.getBrickWalls()) {
            for (Brick brick : brickWall.getBricks()) {
                for (Bullet bullet : this.playerTank.getBullets()) {
                    if (bullet.intersect(brick.getBoundingBox())) {
                        bulletsToRemove.add(bullet);
                        bricksToRemove.add(brick);
                    }
                }
            }

            brickWall.getBricks().removeAll(bricksToRemove);
            bricksToRemove.clear();
        }

        for (SteelWall steelWall : this.brickField.getSteelWallsWalls()) {
            for (Steel steel : steelWall.getSteel()) {
                for (Bullet bullet : this.playerTank.getBullets()) {
                    if (bullet.intersect(steel.getBoundingBox())) {
                        bulletsToRemove.add(bullet);
                    }
                }
            }
        }

        this.playerTank.getBullets().removeAll(bulletsToRemove);
    }

    private void checkCollisionBetweenBulletsAndEagle() {
        for (int i = 0; i < this.enemyTanks.size(); i++) {
            EnemyTank enemyTank = this.enemyTanks.get(i);
            for (int j = 0; j < enemyTank.getBullets().size(); j++) {
                Bullet bullet = enemyTank.getBullets().get(j);
                if (bullet.intersect(this.eagle.getBoundingBox())) {
                    this.eagle.health -= 10;
                }
            }
        }

        for (Bullet bullet : this.playerTank.getBullets()) {
            if (bullet.intersect(this.eagle.getBoundingBox())) {
                this.eagle.health -= 10;
            }
        }
    }

    private void checkCollisionBetweenBullets() {
        List<Bullet> playerBulletsToRemove = new ArrayList<>();
        for (int i = 0; i < this.playerTank.getBullets().size(); i++) {
            for (int j = 0; j < this.enemyTanks.size(); j++) {
                List<Bullet> enemyBulletsToRemove = new ArrayList<>();
                for (int k = 0; k < this.enemyTanks.get(j).getBullets().size(); k++) {
                    Bullet playerBullet = this.playerTank.getBullets().get(i);
                    Bullet enemyBullet = this.enemyTanks.get(j).getBullets().get(k);
                    if (playerBullet.intersect(enemyBullet.getBoundingBox())) {
                        playerBulletsToRemove.add(playerBullet);
                        enemyBulletsToRemove.add(enemyBullet);
                    }
                }

                this.enemyTanks.get(j).getBullets().removeAll(enemyBulletsToRemove);
            }
        }

        this.playerTank.getBullets().removeAll(playerBulletsToRemove);
    }

    private void checkIfGameEnded() {
        if (this.eagle.health <= 0) {
            StateManager.setCurrentState(new EndGameState(this.gameEngine, "You Lost!"));
        }

        if (this.enemyTanks.size() == 0) {
            StateManager.setCurrentState(new EndGameState(this.gameEngine, "You Won!"));
        }

        if (this.playerTank.getHealth() <= 0) {
            StateManager.setCurrentState(new EndGameState(this.gameEngine, "You Lost!"));
        }
    }

    private void spawnEnemy() {
        if (this.enemiesCount == TOTAL_NUMBER_OF_ENEMIES) {
            return;
        }

        this.ticksCounter++;
        if (this.ticksCounter == 500) {
            Random random = new Random();
            boolean cannotGenerate = true;
            int x = 0;
            EnemyTank enemyTank = null;

            while (cannotGenerate) {
                cannotGenerate = false;
                x = random.nextInt(GameWindow.WINDOW_WIDTH - EnemyTank.ENEMY_TANK_WIDTH);
                enemyTank = new EnemyTank(x, 0);
                if (enemyTank.intersect(this.playerTank.getBoundingBox())) {
                    cannotGenerate = true;
                    continue;
                }
                for (EnemyTank enemy : this.enemyTanks) {
//                    int enemyX = enemy.getX();
//                    if ((x >= enemyX && x <= enemyX + enemyTank.getWidth()) && enemyTank.getY() < enemyTank
// .getHeight()) {
//                        cannotGenerate = true;
//                        break;
//                    }
                    if (enemyTank.intersect(enemy.getBoundingBox())) {
                        cannotGenerate = true;
                        break;
                    }
                }
            }


            this.enemyTanks.add(enemyTank);
//            if (this.spawnEnemyLeft) {
//                this.enemyTanks.add(new EnemyTank(0, 0));
//            } else {
//                this.enemyTanks.add(new EnemyTank(
//                        GameWindow.WINDOW_WIDTH - EnemyTank.ENEMY_TANK_WIDTH, 0));
//            }
//
//            this.enemiesCount++;
//            this.spawnEnemyLeft = !this.spawnEnemyLeft;
            this.enemiesCount++;
            this.ticksCounter = 0;
        }
    }

    private void checkBonus() {
        if (bonusExist) {
            bonusCounter++;
            if (bonusCounter == 300) {
                bonusCounter = 0;
                bonusExist = false;
                this.bonus = null;
            }
        }
    }

    private void enemiesFreeze() {
        if (enemiesFreeze) {
            freezeCounter++;
            if (freezeCounter == 500) {
                enemiesFreeze = false;
                freezeCounter = 0;
            }
        }
    }
}