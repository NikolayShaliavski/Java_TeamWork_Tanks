package contracts;

import java.awt.*;

public interface Intersectable {

    /**
     * @param rectangle
     * @return True of false, weather
     * two rectangles intersect each other.
     */
    boolean intersect(Rectangle rectangle);
}