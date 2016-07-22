package gameStates;

import contracts.Printable;
import contracts.Updatable;
import contracts.core.Engine;
import core.GameWindow;
import entities.Eagle;
import entities.bonuses.Bonus;
import entities.bullets.Bullet;
import entities.obsticles.BrickField;
import entities.obsticles.bricks.Brick;
import entities.obsticles.bricks.Steel;
import entities.obsticles.walls.BrickWall;
import entities.obsticles.walls.SteelWall;
import entities.tanks.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;

public class GameState extends State implements Updatable, Printable {

    //private static final int TOTAL_NUMBER_OF_ENEMIES = 10;

    private Eagle eagle;
    private PlayerTank[] playerTanks;
    private Bonus bonus;

    private List<EnemyTank> enemyTanks;
    private int enemiesCount;

    private BrickField brickField;

    private int ticksCounter;

    private int bonusCounter;
    private boolean bonusExist;
    private int timeToGetBonus;

    private int freezeCounter;
    public boolean enemiesFreeze;

    private int totalNumberOfEnemies;

    public GameState(Engine gameEngine,
                     int numberOfPlayers,
                     int totalNumberOfEnemies) {
        super(gameEngine);
        this.brickField = new BrickField();
        this.initEagle();
        this.initWallsAroundEagle();
        this.initBrickOnField();
        this.initPlayerTanks(numberOfPlayers);
        this.initEnemyTanks();

        this.totalNumberOfEnemies = totalNumberOfEnemies;
    }

    @Override
    public void update() {
        this.updatePlayers();
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

        for (PlayerTank playerTank : this.playerTanks) {
            playerTank.print(graphics);
            for (Bullet playerBullet : playerTank.getBullets()) {
                playerBullet.print(graphics);
            }
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

    private void initPlayerTanks(int numberOfPlayers) {

        this.playerTanks = new PlayerTank[numberOfPlayers];
        PlayerTank firstPlayer = new FirstPlayerTank();
        this.playerTanks[0] = firstPlayer;
        if (numberOfPlayers == 2) {
            PlayerTank secondPlayer = new SecondPlayerTank();
            this.playerTanks[1] = secondPlayer;
        }
//        int x = (GameWindow.WINDOW_WIDTH / 2) - (GameWindow.WINDOW_HEIGHT / 4);
//        int y = GameWindow.WINDOW_HEIGHT - PlayerTank.PLAYER_TANK_HEIGHT;
//        this.playerTanks = new PlayerTank(x, y);
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
                this.timeToGetBonus++;
                if (timeToGetBonus == 3) {
                    int bonusX = this.enemyTanks.get(i).getX() + EnemyTank.ENEMY_TANK_WIDTH / 4;
                    int bonusY = this.enemyTanks.get(i).getY() + EnemyTank.ENEMY_TANK_HEIGHT / 4;
                    this.bonus = new Bonus(bonusX, bonusY);
                    bonusExist = true;
                    timeToGetBonus = 0;
                }

                this.enemyTanks.remove(i);
            }
        }
    }

    private void updatePlayers() {

        boolean[] canMovePlayers = new boolean[this.playerTanks.length];
        for (int i = 0; i < canMovePlayers.length; i++) {
            canMovePlayers[i] = true;
        }

        for (EnemyTank enemyTank : this.enemyTanks) {
            for (int i = 0; i < this.playerTanks.length; i++) {
                if (this.playerTanks[i].intersect(enemyTank.getBoundingBox())) {
                    canMovePlayers[i] = false;
                }
            }
        }

        for (BrickWall brickWall : this.brickField.getBrickWalls()) {
            for (Brick brick : brickWall.getBricks()) {
                for (int i = 0; i < this.playerTanks.length; i++) {
                    if (this.playerTanks[i].intersect(brick.getBoundingBox())) {
                        canMovePlayers[i] = false;
                    }
                }
            }
        }

        for (SteelWall steelWall : this.brickField.getSteelWallsWalls()) {
            for (Steel steel : steelWall.getSteel()) {
                for (int i = 0; i < this.playerTanks.length; i++) {
                    if (this.playerTanks[i].intersect(steel.getBoundingBox())) {
                        canMovePlayers[i] = false;
                    }
                }
            }
        }
        for (int i = 0; i < canMovePlayers.length; i++) {
            if (canMovePlayers[i]) {
                this.playerTanks[i].update();
            } else {
                this.playerTanks[i].dealWithCollision();
            }
        }

        if (bonusExist) {
            for (int i = 0; i < this.playerTanks.length; i++) {
                if (this.playerTanks[i].intersect(this.bonus.getBoundingBox())) {
                    enemiesFreeze = true;
                    bonusExist = false;
                    bonusCounter = 0;
                    this.bonus = null;
                    break;
                }
            }
        }
    }

    private void moveEnemies() {
        ArrayList<EnemyTank> toUpdate = new ArrayList<>();
        ArrayList<EnemyTank> toDealWithCollision = new ArrayList<>();

        for (int i = 0; i < this.playerTanks.length; i++) {
            for (EnemyTank enemyTank : this.enemyTanks) {
                if (enemyTank.intersect(this.playerTanks[i].getBoundingBox())) {
                    if (!toDealWithCollision.contains(enemyTank) && !toUpdate.contains(enemyTank)) {
                        toDealWithCollision.add(enemyTank);
                        //toUpdate.remove(enemyTank);
                    }
                } else {
                    if (!toUpdate.contains(enemyTank) && !toDealWithCollision.contains(enemyTank)) {
                        toUpdate.add(enemyTank);
                        //toDealWithCollision.remove(enemyTank);
                    }
                }
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
                for (int k = 0; k < this.playerTanks.length; k++) {
                    if (enemyBullet.intersect(this.playerTanks[k].getBoundingBox())) {
                        //this.playerTanks[k].decreaseHealth(this.playerTanks[k].getHealth() - enemyTank.getDamage());
                        this.playerTanks[k].decreaseHealth(enemyTank.getDamage());
                        toRemove.add(enemyTank.getBullets().get(j));
                    }
                }
            }

            enemyTank.getBullets().removeAll(toRemove);
        }
    }

    private void checkCollisionBetweenPlayerBulletAndEnemies() {
        LinkedHashMap<Integer, List<Bullet>> toRemove = new LinkedHashMap<>();
        for (int k = 0; k < this.playerTanks.length; k++) {
            PlayerTank currentPlayerTank = this.playerTanks[k];
            toRemove.put(k, new ArrayList<>());
            for (int i = 0; i < currentPlayerTank.getBullets().size(); i++) {
                Bullet playerBullet = currentPlayerTank.getBullets().get(i);
                for (int j = 0; j < this.enemyTanks.size(); j++) {
                    AbstractTank enemyTank = this.enemyTanks.get(j);
                    if (playerBullet.intersect(enemyTank.getBoundingBox())) {
                        //enemyTank.decreaseHealth(enemyTank.getHealth() - this.playerTanks[k].getDamage());
                        enemyTank.decreaseHealth(currentPlayerTank.getDamage());
                        toRemove.get(k).add(playerBullet);
                    }
                }
            }
        }

        for (int i = 0; i < this.playerTanks.length; i++) {
            List<Bullet> bulletsToRemove = toRemove.get(i);
            this.playerTanks[i].getBullets().removeAll(bulletsToRemove);
        }
    }

    private void checkCollisionBetweenPlayerBulletAndObstacles() {
        List<Brick> bricksToRemove = new ArrayList<>();
        LinkedHashMap<Integer, List<Bullet>> bulletsToRemove = new LinkedHashMap<>();
        for (int i = 0; i < this.playerTanks.length; i++) {
            bulletsToRemove.put(i, new ArrayList<>());
            for (BrickWall brickWall : this.brickField.getBrickWalls()) {
                for (Brick brick : brickWall.getBricks()) {

                    for (Bullet bullet : this.playerTanks[i].getBullets()) {
                        if (bullet.intersect(brick.getBoundingBox())) {
                            bulletsToRemove.get(i).add(bullet);
                            bricksToRemove.add(brick);
                        }
                    }
                }
                brickWall.getBricks().removeAll(bricksToRemove);
                bricksToRemove.clear();
            }

        }

        for (SteelWall steelWall : this.brickField.getSteelWallsWalls()) {
            for (Steel steel : steelWall.getSteel()) {
                for (int i = 0; i < this.playerTanks.length; i++) {
                    for (Bullet bullet : this.playerTanks[i].getBullets()) {
                        if (bullet.intersect(steel.getBoundingBox())) {
                            bulletsToRemove.get(i).add(bullet);
                        }
                    }
                }
            }
        }

        for (int i = 0; i < this.playerTanks.length; i++) {
            this.playerTanks[i].getBullets().removeAll(bulletsToRemove.get(i));
        }
    }

    private void checkCollisionBetweenBulletsAndEagle() {
        for (int i = 0; i < this.enemyTanks.size(); i++) {
            EnemyTank enemyTank = this.enemyTanks.get(i);
            for (int j = 0; j < enemyTank.getBullets().size(); j++) {
                Bullet bullet = enemyTank.getBullets().get(j);
                if (bullet.intersect(this.eagle.getBoundingBox())) {
                    this.eagle.decreaseHealth(this.eagle.getHealth() - 10);
                }
            }
        }

        for (int i = 0; i < this.playerTanks.length; i++) {
            for (Bullet bullet : this.playerTanks[i].getBullets()) {
                if (bullet.intersect(this.eagle.getBoundingBox())) {
                    this.eagle.decreaseHealth(this.eagle.getHealth() - 10);
                }
            }
        }
    }

    private void checkCollisionBetweenBullets() {
        LinkedHashMap<Integer, List<Bullet>> playerBulletsToRemove = new LinkedHashMap<>();
        for (int p = 0; p < this.playerTanks.length; p++) {
            playerBulletsToRemove.put(p, new ArrayList<>());
            for (int i = 0; i < this.playerTanks[p].getBullets().size(); i++) {
                for (int j = 0; j < this.enemyTanks.size(); j++) {
                    List<Bullet> enemyBulletsToRemove = new ArrayList<>();
                    for (int k = 0; k < this.enemyTanks.get(j).getBullets().size(); k++) {
                        Bullet playerBullet = this.playerTanks[p].getBullets().get(i);
                        Bullet enemyBullet = this.enemyTanks.get(j).getBullets().get(k);
                        if (playerBullet.intersect(enemyBullet.getBoundingBox())) {
                            playerBulletsToRemove.get(p).add(playerBullet);
                            enemyBulletsToRemove.add(enemyBullet);
                        }
                    }

                    this.enemyTanks.get(j).getBullets().removeAll(enemyBulletsToRemove);
                }
            }
        }
        for (int i = 0; i < this.playerTanks.length; i++) {
            this.playerTanks[i].getBullets().removeAll(playerBulletsToRemove.get(i));
        }
    }

    private void checkIfGameEnded() {
        if (this.eagle.health <= 0) {
            StateManager.setCurrentState(new EndGameState(this.gameEngine, "You Lost!"));
        }

        if (this.enemyTanks.size() == 0) {
            StateManager.setCurrentState(new EndGameState(this.gameEngine, "You Won!"));
        }


        List<PlayerTank> playerTanksToRemove = new ArrayList<>();
        for (int i = 0; i < this.playerTanks.length; i++) {
            if (this.playerTanks[i].getHealth() <= 0) {
                playerTanksToRemove.add(this.playerTanks[i]);
                //StateManager.setCurrentState(new EndGameState(this.gameEngine, "You Lost!"));
            }
        }

        // TODO: this may not work
        //Arrays.asList(this.playerTanks).removeAll(playerTanksToRemove);
    }

    private void spawnEnemy() {
        if (this.enemiesCount == this.totalNumberOfEnemies) {
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
                for (int i = 0; i < this.playerTanks.length; i++) {
                    if (enemyTank.intersect(this.playerTanks[i].getBoundingBox())) {
                        cannotGenerate = true;
                        continue;
                    }
                    for (EnemyTank enemy : this.enemyTanks) {
                        if (enemyTank.intersect(enemy.getBoundingBox())) {
                            cannotGenerate = true;
                            break;
                        }
                    }
                }
            }

            this.enemyTanks.add(enemyTank);
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