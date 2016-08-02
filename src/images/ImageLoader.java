package images;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Helper class for loading images.
 */
public final class ImageLoader {

    /*
        The class should not be instantiated.
     */
    private ImageLoader() {
    }

    /**
     * @param path The path to the specified image.
     * @return The image or null if the method cannot load it.
     */
    public static BufferedImage loadImage(String path) {
        File file = new File(path);

        BufferedImage image = null;
        try {
            image = ImageIO.read(file);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return image;
    }
}