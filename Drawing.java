import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Stroke;

public class Drawing {

    private Color[] textures;

    public Drawing(Color[] textures) {
        this.textures = textures;
    }

    // Commented out the original method
    // public void drawTexturedTriangle(Graphics2D g2, Triangle t, Texture texture) {
    //     if (!t.isValid()) {
    //         return;
    //     }
    //     g2.setColor(t.getMaterial());
    //     int[] xCords = {t.getP1().getX(), t.getP2().getX(), t.getP3().getX(), t.getP4().getX()};
    //     int[] yCords = {t.getP1().getY(), t.getP2().getY(), t.getP3().getY(), t.getP4().getY()};
    //     g2.fillPolygon(xCords, yCords, t.getNumberOfVertices());
    // }

    public void drawTexturedTriangle(Graphics2D g2, Triangle t, Texture texture) {
        if (!t.isValid() || t.getNumberOfVertices() == 4) {
            return;
        }

        // Get triangle vertices
        int[] xPoints = {t.getP1().getX(), t.getP2().getX(), t.getP3().getX()};
        int[] yPoints = {t.getP1().getY(), t.getP2().getY(), t.getP3().getY()};
        
        // Calculate average UV coordinates
        double avgU = (t.getUV1().getU() + t.getUV2().getU() + t.getUV3().getU()) / 3.0;
        double avgV = (t.getUV1().getV() + t.getUV2().getV() + t.getUV3().getV()) / 3.0;
    
        // Calculate texture index based on average UV coordinates
        int textureIndex = ((int)(avgU * 32) % 32) + ((int)(avgV * 32) % 32) * 32;
        textureIndex = Math.min(Math.max(textureIndex, 0), textures.length - 1);
        
        // Set color and fill polygon
        g2.setColor(texture.getColor((int)(avgU * 32) % 32, (int)(avgV * 32) % 32));
        g2.fillPolygon(xPoints, yPoints, 3);
    }

    private double[] barycentricWeights(Vertex2D[] vertices, int x, int y) {
        double x1 = vertices[0].getX(), y1 = vertices[0].getY();
        double x2 = vertices[1].getX(), y2 = vertices[1].getY();
        double x3 = vertices[2].getX(), y3 = vertices[2].getY();

        double denom = (y2 - y3) * (x1 - x3) + (x3 - x2) * (y1 - y3);
        double w1 = ((y2 - y3) * (x - x3) + (x3 - x2) * (y - y3)) / denom;
        double w2 = ((y3 - y1) * (x - x3) + (x1 - x3) * (y - y3)) / denom;
        double w3 = 1 - w1 - w2;

        return new double[]{w1, w2, w3};
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