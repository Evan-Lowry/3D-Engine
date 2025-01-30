public class Wall {
    private Vertex2D point1;
    private Vertex2D point2;
    private float maxX;
    private float minX;
    private float maxY;
    private float minY;
    private float maxZ;
    private float minZ;
    private Normal normal;
    public Wall(Triangle t) {

        Vertex3D v1 = t.getV1();
        Vertex3D v2 = t.getV2();
        Vertex3D v3 = t.getV3();
        this.normal = t.getNormal();
        if (v1.getX() == v2.getX() && v1.getY() == v2.getY()) {
            point1 = new Vertex2D(v1.getX(), v1.getY());
            point2 = new Vertex2D(v3.getX(), v3.getY());
        } else if (v2.getX() == v3.getX() && v2.getY() == v3.getY()) {
            point1 = new Vertex2D(v2.getX(), v2.getY());
            point2 = new Vertex2D(v1.getX(), v1.getY());
        } else if (v3.getX() == v1.getX() && v3.getY() == v1.getY()) {
            point1 = new Vertex2D(v3.getX(), v3.getY());
            point2 = new Vertex2D(v2.getX(), v2.getY());
        } else {
            System.out.println("WHOOPS CAN'T VERIFY WALLS!!!!!");
        }

        this.maxZ = Math.max(v1.getZ(), Math.max(v2.getZ(), v3.getZ()));
        this.minZ = Math.min(v1.getZ(), Math.min(v2.getZ(), v3.getZ()));
    }

    public Vertex2D getPoint1() {
        return point1;
    }

    public Vertex2D getPoint2() {
        return point2;
    }

    public float getMaxZ() {
        return this.maxZ;
    }

    public float getMinZ() {
        return this.minZ;
    }

    public Normal getNormal() {
        return this.normal;
    }
}
