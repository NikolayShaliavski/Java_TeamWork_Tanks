package contracts.inputHandler;

import java.awt.event.KeyListener;

public interface PlayerInputHandler extends KeyListener {

    /**
     * @return <tt>true</tt> or <tt>false</tt> whether the user has
     * pressed the key for moving up.
     */
    boolean isUp();

    /**
     * @return <tt>true</tt> or <tt>false</tt> whether the user has
     * pressed the key for moving down.
     */
    boolean isDown();

    /**
     * @return <tt>true</tt> or <tt>false</tt> whether the user has
     * pressed the key for moving left.
     */
    boolean isLeft();

    /**
     * @return <tt>true</tt> or <tt>false</tt> whether the user has
     * pressed the key for moving right.
     */
    boolean isRight();

    /**
     * @return <tt>true</tt> or <tt>false</tt> whether the user has
     * pressed the key for shooting.
     */
    boolean hasShoot();

    /**
     * @return <tt>true</tt> or <tt>false</tt> whether the user has
     * pressed the key for dropping bomb.
     */
    boolean hasDroppedBomb();

    /**
     * @return The last direction of the player.
     * This is used by other classes and method to handle the collision
     * between the tanks and obstacles.
     */
    int getLastDirection();
}