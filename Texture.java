import java.awt.Color;

public class Texture {
    private Color[][] texture;

    public Texture(Color[][] texture) {
        this.texture = texture;
    }

    public Color getColor(int x, int y) {
        return this.texture[x][y];
    }

    public void setColor(int x, int y, Color color) {
        this.texture[x][y] = color;
    }
}