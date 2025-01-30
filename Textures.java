import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Textures {

    private List<Texture> textures;

    public Textures(String folderName) {
        textures = new ArrayList<>();
        File folder = new File(folderName);
        File[] listOfFiles = folder.listFiles();
        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                if (file.isFile() && file.getName().endsWith(".png")) {
                    textures.add(new Texture(loadTexture(file.getPath())));
                }
            }
        }
    }

    public Color[][] loadTexture(String filePath) {
        Color[][] pixels = new Color[32][32];
        try {
            BufferedImage image = ImageIO.read(new File(filePath));
            for (int y = 0; y < 32; y++) {
                for (int x = 0; x < 32; x++) {
                    pixels[y][x] = new Color(image.getRGB(x, y));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pixels;
    }

    public List<Texture> getTextures() {
        return textures;
    }

    public Texture getTexture(int index) {
        if (index >= 0 && index < textures.size()) {
            return textures.get(index);
        } else {
            return null;
        }
    }
}
