package contracts.models;

import contracts.Intersectable;
import contracts.Printable;

import java.awt.*;

public interface Entity extends Printable, Intersectable {
    Rectangle getBoundingBox();

    int getX();

    int getY();

    int getWidth();

    int getHeight();
}