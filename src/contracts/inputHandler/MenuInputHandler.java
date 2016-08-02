package contracts.inputHandler;

import java.awt.event.KeyListener;

public interface MenuInputHandler extends KeyListener {

    /**
     * @return <tt>true</tt> or <tt>false</tt> whether the user has
     * pressed the key for going up it the menu.
     */
    boolean isUp();

    /**
     * @return <tt>true</tt> or <tt>false</tt> whether the user has
     * pressed the key for going down it the menu.
     */
    boolean isDown();

    /**
     * @return <tt>true</tt> or <tt>false</tt> whether the user has
     * pressed the enter key.
     */
    boolean isEnter();
}