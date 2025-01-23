import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Stroke;
import java.util.Arrays;

public class Drawing {

    public Drawing() {
    }

    public void draw(Graphics2D g2) {
        // gets all triangles to be drawn
        Triangle[] triangles = map.getSector().getTriangles();
        // sorts the triangles in order of farthest from camera to closest
        Arrays.sort(triangles, (t1, t2) -> Double.compare(t2.getDepthFromCamera(), t1.getDepthFromCamera()));

        // 
        for (Triangle t : triangles) {
            t.update();
            drawing.drawTexturedTriangle(g2, t, texturess.getTexture(0));
        }
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
        if (!t.isValid()) {
            return;
        }

        // Get triangle vertices
        int[] xPoints = {t.getP1().getX(), t.getP2().getX(), t.getP3().getX(), t.getP4().getX()};
        int[] yPoints = {t.getP1().getY(), t.getP2().getY(), t.getP3().getY(), t.getP4().getY()};
        
        g2.setColor(t.getMaterial());
        g2.drawPolygon(xPoints, yPoints, t.getNumberOfVertices());
        // g2.fillPolygon(xPoints, yPoints, t.getNumberOfVertices());
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
        Vertex3D v1 = t.getV1();
        Vertex3D v2 = t.getV2();
        Vertex3D v3 = t.getV3();
        g2.setColor(Color.WHITE);
        g2.drawLine((int)v3.getX(), (int)v3.getY(), (int)v1.getX(), (int)v1.getY());
    }
}