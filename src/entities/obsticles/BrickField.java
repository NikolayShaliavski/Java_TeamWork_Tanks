package entities.obsticles;

import entities.obsticles.walls.*;
import contracts.Printable;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Class that contains all the obstacles on the game field.
 */
public class BrickField implements Printable {

    private Set<BrickWall> brickWalls;
    private Set<SteelWall> steelWallsWalls;

    public BrickField() {
        this.brickWalls = new HashSet<>();
        this.steelWallsWalls = new HashSet<>();
    }

    @Override
    public void print(Graphics graphics) {
        for (BrickWall brickWall : this.brickWalls) {
            brickWall.print(graphics);
        }

        for (SteelWall steelWallsWall : this.steelWallsWalls) {
            steelWallsWall.print(graphics);
        }
    }

    public void addBrickWall(BrickWall brickWall){
        this.brickWalls.add(brickWall);
    }

    public void addSteelWall(SteelWall steelWall){
        this.steelWallsWalls.add(steelWall);
    }

    public Set<BrickWall> getBrickWalls() {
        return this.brickWalls;
    }

    public Set<SteelWall> getSteelWallsWalls() {
        return this.steelWallsWalls;
    }
}