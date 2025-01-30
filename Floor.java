import java.util.ArrayList;

public class Floor {

    private Vertex3D[] vertexs = new Vertex3D[3];
    private float floorHeight;
    private ArrayList <Wall> walls = new ArrayList<>();
    
    private double maxX, minX, maxY, minY;

    public Floor(Vertex3D v1, Vertex3D v2, Vertex3D v3, ArrayList <Wall> walls) {
        this.vertexs[0] = v1;
        this.vertexs[1] = v2;
        this.vertexs[2] = v3;
        this.floorHeight = this.vertexs[0].getZ();

        // calculates the min and max values on each axis
        this.maxX = Math.max(this.vertexs[0].getX(), Math.max(this.vertexs[1].getX(), this.vertexs[2].getX()));
        this.minX = Math.min(this.vertexs[0].getX(), Math.min(this.vertexs[1].getX(), this.vertexs[2].getX()));

        this.maxY = Math.max(this.vertexs[0].getY(), Math.max(this.vertexs[1].getY(), this.vertexs[2].getY()));
        this.minY = Math.min(this.vertexs[0].getY(), Math.min(this.vertexs[1].getY(), this.vertexs[2].getY()));

        for (Wall w : walls) {
            if (isWallInside(w)) {
                if (w.getMaxZ() > this.floorHeight) {
                    this.walls.add(w);   
                }
            }
        }
    }

    private boolean isWallInside(Wall w) {
        return insideAABB(w.getPoint1().getX(), w.getPoint1().getY()) && insideAABB(w.getPoint2().getX(), w.getPoint2().getY());
    }

    public float getFloorHeight() {
        return floorHeight;
    }

    public boolean isPlayerInside() {
        return insideAABB(GamePanel.c.getNewX(), GamePanel.c.getNewY());
    }

    // checks if point is inside axis aligned bounding boxes (AABB)
    private boolean insideAABB(float x, float y) {

        // checks if point is inside bounding box on the x axis
        if (x > this.maxX || x < this.minX) {
            return false;
        }

        // checks if point is inside bounding box on the y axis
        if (y > this.maxY || y < this.minY) {
            return false;
        }

        return true;
    }

    // checks if player passes the seperating axis therom (SAT)
    private boolean passesSAT() {
        boolean passSAT = true;

        for (int i = 0; i < this.vertexs.length; i++) {
            if (shortestDistanceToLine(GamePanel.c.toVertex(), this.vertexs[i], this.vertexs[(i+1)%3]) > 0) {
                passSAT = false;
            }
        }

        return passSAT;
    }

    public double shortestDistanceToLine(Vertex3D c, Vertex3D v1, Vertex3D v2) {
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

    public Triangle getTriangle() {
        // creates placeholder normal
        Normal n = new Normal(0, 0, 1);
        // creates placeholder UV
        UV uv = new UV(0, 0);
        return new Triangle(vertexs[0], vertexs[1], vertexs[2], uv, uv, uv, n, n, n, 1);
    }

    public ArrayList <Wall> getWalls() {
        return this.walls;
    }

    public double getMaxX() {
        return this.maxX;
    }

    public double getMinX() {
        return this.minX;
    }

    public double getMaxY() {
        return this.maxY;
    }

    public double getMinY() {
        return this.minY;
    }
}