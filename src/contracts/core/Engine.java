package contracts.core;

import contracts.Printable;
import contracts.Updatable;
import core.GameWindow;

import java.awt.*;

public interface Engine extends Runnable, Updatable, Printable {
    void stop();

    Graphics getGraphics();

    GameWindow getGameWindow();
}