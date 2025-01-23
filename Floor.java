import java.util.ArrayList;

public class Floor {

    private Vertex3D[] vertexs = new Vertex3D[3];
    private double floorHeight;

    public Floor(Vertex3D v1, Vertex3D v2, Vertex3D v3) {
        this.vertexs[0] = v1;
        this.vertexs[1] = v2;
        this.vertexs[2] = v3;
        this.floorHeight = this.vertexs[0].getZ();
    }

    public double getFloorHeight() {
        return floorHeight;
    }

    public boolean isPlayerInside(Camera c) {
        return insideAABB(c) && passesSAT(c);
    }

    // checks if player is inside axis aligned bounding boxes (AABB)
    private boolean insideAABB(Camera c) {

        boolean insideX = false;
        boolean insideY = false;

        // calculates the min and max values on each axis
        double maxX = Math.max(this.vertexs[0].getX(), Math.max(this.vertexs[1].getX(), this.vertexs[2].getX()));
        double minX = Math.min(this.vertexs[0].getX(), Math.min(this.vertexs[1].getX(), this.vertexs[2].getX()));

        double maxY = Math.max(this.vertexs[0].getY(), Math.max(this.vertexs[1].getY(), this.vertexs[2].getY()));
        double minY = Math.min(this.vertexs[0].getY(), Math.min(this.vertexs[1].getY(), this.vertexs[2].getY()));

        // checks if camera is inside bounding box on the x axis
        if (c.getNewX() <= maxX && c.getNewX() >= minX) {
            insideX = true;
        }

        // checks if camera is inside bounding box on the y axis
        if (c.getNewY() <= maxY && c.getNewY() >= minY) {
            insideY = true;
        }

        return insideX && insideY;
    }

    // checks if player passes the seperating axis therom (SAT)
    private boolean passesSAT(Camera c) {
        boolean passSAT = true;

        for (int i = 0; i < this.vertexs.length; i++) {
            if (shortestDistanceToLine(c, this.vertexs[i], this.vertexs[(i+1)%3]) > 0) {
                passSAT = false;
            }
        }

        return passSAT;
    }

    public double shortestDistanceToLine(Camera c, Vertex3D v1, Vertex3D v2) {
        double x0 = c.getNewX();
        double y0 = c.getNewY();
        double x1 = v1.getX();
        double y1 = v1.getY();
        double x2 = v2.getX();
        double y2 = v2.getY();

        // Numerator: (y2 - y1)x0 - (x2 - x1)y0 + x2y1 - y2x1
        double numerator = (y2 - y1) * x0 - (x2 - x1) * y0 + (x2 * y1) - (y2 * x1);

        // Denominator: √((y2 - y1)^2 + (x2 - x1)^2)
        double denominator = Math.sqrt(Math.pow(y2 - y1, 2) + Math.pow(x2 - x1, 2));

        // Final distance
        return numerator / denominator;
    }

    public Vertex3D getVertex(int index) {
        if (index < 0 || index >= vertexs.length) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        return vertexs[index];
    }
}