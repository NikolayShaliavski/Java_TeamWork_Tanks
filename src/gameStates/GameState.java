package gameStates;

import contracts.Printable;
import contracts.Updatable;
import contracts.core.Engine;
import contracts.inputHandler.PlayerInputHandler;
import core.GameWindow;
import entities.Eagle;
import entities.bonuses.Bomb;
import entities.bonuses.Freeze;
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

/**
 * Defines solid core logic for all game states.
 */
public abstract class GameState extends State implements Updatable, Printable {

    private Eagle eagle;
    private PlayerTank[] playerTanks;
    private Freeze freeze;

    private PlayerInputHandler[] inputHandlers;

    private List<EnemyTank> enemyTanks;
    private int enemiesCount;

    private BrickField brickField;

    private int ticksCounter;

    private int bonusCounter;
    private boolean bonusExist;
    private int timeToGetBonus;

    private int freezeCounter;
    private boolean enemiesFreeze;

    private int totalNumberOfEnemies;

    protected GameState(Engine gameEngine,
                        int numberOfPlayers,
                        int totalNumberOfEnemies,
                        PlayerInputHandler... inputHandlers) {
        super(gameEngine);

        this.inputHandlers = inputHandlers;
        this.totalNumberOfEnemies = totalNumberOfEnemies;

        this.brickField = new BrickField();
        this.initEagle();
        this.initWallsAroundEagle();
        this.initBrickOnField();
        this.initPlayerTanks(numberOfPlayers, inputHandlers);
        this.initEnemyTanks();
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
        this.removeDeadPlayers();
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
            this.freeze.print(graphics);
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
        int upperWallCols = 8;
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

    private void initPlayerTanks(int numberOfPlayers, PlayerInputHandler[] inputHandlers) {

        this.playerTanks = new PlayerTank[numberOfPlayers];
        PlayerTank firstPlayer = new FirstPlayerTank(inputHandlers[0]);
        this.playerTanks[0] = firstPlayer;
        if (numberOfPlayers == 2) {
            PlayerTank secondPlayer = new SecondPlayerTank(inputHandlers[1]);
            this.playerTanks[1] = secondPlayer;
        }
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
                    this.freeze = new Freeze(bonusX, bonusY);
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

        if (this.playerTanks.length == 2) {
            if (this.playerTanks[0].intersect(this.playerTanks[1].getBoundingBox())) {
                canMovePlayers[0] = false;
            }
            if (this.playerTanks[1].intersect(this.playerTanks[0].getBoundingBox())) {
                canMovePlayers[1] = false;
            }
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

        if (this.bonusExist) {
            for (int i = 0; i < this.playerTanks.length; i++) {
                if (this.playerTanks[i].intersect(this.freeze.getBoundingBox())) {
                    this.enemiesFreeze = true;
                    this.bonusExist = false;
                    this.bonusCounter = 0;
                    this.freeze = null;
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
                    if (!toDealWithCollision.contains(enemyTank)) {
                        toDealWithCollision.add(enemyTank);
                        toUpdate.remove(enemyTank);
                    }
                } else {
                    if (!toUpdate.contains(enemyTank) && !toDealWithCollision.contains(enemyTank)) {
                        toUpdate.add(enemyTank);
                    }
                }
            }
        }

        for (BrickWall brickWall : this.brickField.getBrickWalls()) {
            for (Brick brick : brickWall.getBricks()) {
                for (EnemyTank enemyTank : this.enemyTanks) {
                    if (enemyTank.intersect(brick.getBoundingBox())) {
                        if (!toDealWithCollision.contains(enemyTank)) {
                            toDealWithCollision.add(enemyTank);
                        }

                        toUpdate.remove(enemyTank);
                    }
                }
            }
        }

        for (SteelWall steelWall : this.brickField.getSteelWallsWalls()) {
            for (Steel steel : steelWall.getSteel()) {
                for (EnemyTank enemyTank : this.enemyTanks) {
                    if (enemyTank.intersect(steel.getBoundingBox())) {
                        if (!toDealWithCollision.contains(enemyTank)) {
                            toDealWithCollision.add(enemyTank);
                        }

                        toUpdate.remove(enemyTank);
                    }
                }
            }
        }

        for (int i = 0; i < this.enemyTanks.size(); i++) {
            EnemyTank enemyTank = this.enemyTanks.get(i);
            for (int j = i + 1; j < this.enemyTanks.size(); j++) {
                if (enemyTank.intersect(this.enemyTanks.get(j).getBoundingBox())) {
                    if (!toDealWithCollision.contains(enemyTank)) {
                        toDealWithCollision.add(enemyTank);
                    }

                    if (!toDealWithCollision.contains(this.enemyTanks.get(j))) {
                        toDealWithCollision.add(this.enemyTanks.get(j));
                    }

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
                        enemyTank.decreaseHealth(currentPlayerTank.getDamage());
                        toRemove.get(k).add(playerBullet);
                    }
                }
            }
        }

        for (PlayerTank playerTank : this.playerTanks) {
            Bomb bombToRemove = null;
            for (Bomb bomb : playerTank.getBombs()) {
                for (EnemyTank enemyTank : this.enemyTanks) {
                    if (bomb.intersect(enemyTank.getBoundingBox())) {
                        enemyTank.decreaseHealth(bomb.getDamage());
                        bombToRemove = bomb;
                        break;
                    }
                }
            }

            if (bombToRemove != null) {
                playerTank.getBombs().remove(bombToRemove);
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

        if (this.playerTanks.length == 2) {
            for (Bullet bullet : this.playerTanks[0].getBullets()) {
                if (bullet.intersect(this.playerTanks[1].getBoundingBox())) {
                    bulletsToRemove.get(0).add(bullet);
                }
            }

            for (Bullet bullet : this.playerTanks[1].getBullets()) {
                if (bullet.intersect(this.playerTanks[0].getBoundingBox())) {
                    bulletsToRemove.get(1).add(bullet);
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

    private void removeDeadPlayers() {
        List<PlayerTank> playerTanksAlive = new ArrayList<>();

        for (int i = 0; i < this.playerTanks.length; i++) {
            if (this.playerTanks[i].getHealth() > 0) {
                playerTanksAlive.add(playerTanks[i]);
            }
        }

        PlayerTank[] newPlayerTankArr = new PlayerTank[playerTanksAlive.size()];
        for (int i = 0; i < playerTanksAlive.size(); i++) {
            newPlayerTankArr[i] = playerTanksAlive.get(i);
        }

        this.playerTanks = newPlayerTankArr;
    }

    private void checkIfGameEnded() {
        if (this.eagle.health <= 0) {
            StateManager.setCurrentState(new EndGameState(this.gameEngine, "You Lost!"));
        }

        if (this.enemyTanks.size() == 0) {
            StateManager.setCurrentState(new EndGameState(this.gameEngine, "You Won!"));
        }

        if (this.playerTanks.length == 0) {
            StateManager.setCurrentState(new EndGameState(this.gameEngine, "You Lost!"));
        }
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
        if (this.bonusExist) {
            this.bonusCounter++;
            if (this.bonusCounter == 300) {
                this.bonusCounter = 0;
                this.bonusExist = false;
                this.freeze = null;
            }
        }
    }

    private void enemiesFreeze() {
        if (this.enemiesFreeze) {
            this.freezeCounter++;
            if (this.freezeCounter == 500) {
                this.enemiesFreeze = false;
                this.freezeCounter = 0;
            }
        }
    }
}