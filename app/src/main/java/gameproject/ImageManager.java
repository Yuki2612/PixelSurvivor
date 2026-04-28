package gameproject;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import javax.imageio.ImageIO;

public class ImageManager {
    private static HashMap<String, BufferedImage> images = new HashMap<>();

    public static void load(String name, String path) {
        if (images.containsKey(name)) return;
        try {
            BufferedImage img = ImageIO.read(new File(path));
            images.put(name, img);
        } catch (IOException e) {
            System.err.println("LỖI CHÍ MẠNG: Không tìm thấy ảnh tại đường dẫn -> " + path);
        }
    }

    public static BufferedImage get(String name) {
        return images.get(name);
    }
}