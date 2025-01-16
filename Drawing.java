import java.awt.Color;
import java.awt.Graphics2D;

public class Drawing {

    private Color[] textures;

    public Drawing(Color[] textures) {
        this.textures = textures;
    }

    public void drawTriangle(Graphics2D g2, Triangle t) {
        if (!t.isValid()) {
            return;
        }
        g2.setColor(textures[t.getColorIndex()]);
        int[] cordinatesX = {t.getP1().getX(), t.getP2().getX(), t.getP3().getX(), t.getP4().getX()};
        int[] cordinatesY = {t.getP1().getY(), t.getP2().getY(), t.getP3().getY(), t.getP4().getY()};
        g2.fillPolygon(cordinatesX, cordinatesY, t.getNumberOfVertices());
    }

    public void drawPointer(Graphics2D g2, int x, int y, int magnitude, double angle) {
        double deltaX = Math.cos(angle) * magnitude;
        double deltaY = Math.sin(angle) * magnitude;
        g2.drawLine(x, y, x + (int)deltaX, y + (int)deltaY);
    }

    public void drawRadius(Graphics2D g2, int x, int y, int magnitude) {
        g2.setColor(Color.RED);
        g2.drawOval(x - magnitude / 2, y - magnitude / 2, magnitude, magnitude);
    }

    public void camDebug(Graphics2D g2, Camera c) {
        g2.setColor(Color.WHITE);
        g2.drawOval((int)c.getX() - 10, (int)c.getY() - 10, 20, 20);
        drawPointer(g2, (int)c.getX(), (int)c.getY(), 25, c.getRot());
        drawPointer(g2, (int)c.getX(), (int)c.getY(), 500, c.getRot() + (c.getFOV() / 2));
        drawPointer(g2, (int)c.getX(), (int)c.getY(), 500, c.getRot() - (c.getFOV() / 2));
        drawPointer(g2, (int)c.getX(), (int)c.getY(), 1000, c.getRot() + (Math.PI / 2));
        drawPointer(g2, (int)c.getX(), (int)c.getY(), 1000, c.getRot() - (Math.PI / 2));
    }

    public void triangleDebug(Graphics2D g2, Triangle t) {
        Vertex v1 = t.getV1();
        Vertex v2 = t.getV2();
        Vertex v3 = t.getV3();
        g2.setColor(Color.WHITE);
        g2.drawLine(v3.getIX(), v3.getIY(), v1.getIX(), v1.getIY());
    }
}