package entities.obsticles.walls;

import contracts.Printable;
import entities.AbstractEntity;
import entities.obsticles.bricks.Brick;
import entities.obsticles.bricks.Steel;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class SteelWall extends AbstractEntity implements Printable {
    private Set<Steel> steel;

    public SteelWall(int x, int y, int rows, int cols, boolean fromUpToDown) {
        super(x, y, 0, 0);
        this.steel = new HashSet<>();
        this.buildBrickWall(x, y, rows, cols, fromUpToDown);
    }

    @Override
    public boolean intersect(Rectangle rectangle) {
        for (Steel steel : this.steel) {
            if (steel.intersect(rectangle)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void print(Graphics graphics) {
        for (Steel steel : this.steel) {
            steel.print(graphics);
        }
    }

    private void buildBrickWall(int x, int y, int rows, int cols, boolean fromUpToDown) {
        if (fromUpToDown) {
            this.buildWallFromUpToDown(x, y, rows, cols);
        } else {
            this.buildWallFromDownToUp(x, y, rows, cols);
        }
    }

    private void buildWallFromDownToUp(int endX, int endY, int rows, int cols) {
        int y = endY;
        for (int row = 0; row < rows; row++) {
            int x = endX;
            for (int col = 0; col < cols; col++) {
                Steel steel = new Steel(x, y);
                this.steel.add(steel);

                x -= Brick.BRICK_WIDTH;
                this.setWidth(this.getWidth() + Brick.BRICK_WIDTH);
            }

            y -= Brick.BRICK_HEIGHT;
        }

        this.setWidth(Brick.BRICK_WIDTH * cols);
        this.setHeight(Brick.BRICK_HEIGHT * rows);
    }

    private void buildWallFromUpToDown(int startX, int startY, int rows, int cols) {
        int y = startY;
        for (int row = 0; row < rows; row++) {
            int x = startX;
            for (int col = 0; col < cols; col++) {
                Steel steel = new Steel(x, y);
                this.steel.add(steel);

                x += Brick.BRICK_WIDTH;
            }

            y += Brick.BRICK_HEIGHT;
        }

        this.setWidth(Brick.BRICK_WIDTH * cols);
        this.setHeight(Brick.BRICK_HEIGHT * rows);
    }

    public Set<Steel> getSteel() {
        return this.steel;
    }
}