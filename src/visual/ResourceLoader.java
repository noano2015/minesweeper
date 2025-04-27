package visual;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.io.IOException;


public class ResourceLoader {
    
    // Utility method to load an image (from file or JAR resource)
    public static BufferedImage loadImage(String path) throws IOException {
        // Try to load the resource from the file system
        File file = new File(path);
        if (file.exists()) {
            return ImageIO.read(file); // Load the image from the file system
        }
        
        // Otherwise, try to load the image from the classpath (JAR)
        InputStream inputStream = ResourceLoader.class.getResourceAsStream(path);
        if (inputStream != null) {
            return ImageIO.read(inputStream); // Load from JAR resource
        }
        
        throw new IOException("Image not found at path: " + path);
    }
}

