public class Sector {
    private Triangle[] triangles;

    public Sector(Triangle[] triangles){
        // create the scanner to read the file
        this.triangles = triangles;
    }

    public Triangle[] getTriangles() {
        return this.triangles;
    }

    public Floor getFloor() {
        return new Floor();
    }
}
