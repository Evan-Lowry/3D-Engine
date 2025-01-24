import java.awt.Graphics2D;
import java.util.Arrays;

public class Drawing {

    public Drawing() {
    }

    // used to draw all items to screen
    public void draw(Graphics2D g2) {
        // gets all triangles to be drawn
        Triangle[] triangles = GamePanel.map.getSector().getTriangles();
        // sorts the triangles in order of farthest from camera to closest
        Arrays.sort(triangles, (t1, t2) -> Double.compare(t2.getDepthFromCamera(), t1.getDepthFromCamera()));

        // loops through each triangle, updates variables and draws to screen
        for (Triangle t : triangles) {
            t.update();
            drawTexturedTriangle(g2, t);
        }
    }

    public void drawTexturedTriangle(Graphics2D g2, Triangle t) {
        // makes sure the triangle is valid to render
        if (!t.isValid()) {
            return;
        }

        // gets triangle points in screen space
        int[] xPoints = {(int)t.getP1().getX(), (int)t.getP2().getX(),(int) t.getP3().getX(), (int)t.getP4().getX()};
        int[] yPoints = {(int)t.getP1().getY(), (int)t.getP2().getY(), (int)t.getP3().getY(), (int)t.getP4().getY()};

        // sets the drawing color to triangle color
        g2.setColor(t.getMaterial());
        // draws the triangle to screen as a polygon
        // g2.drawPolygon(xPoints, yPoints, t.getNumberOfVertices());
        g2.fillPolygon(xPoints, yPoints, t.getNumberOfVertices());
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
}