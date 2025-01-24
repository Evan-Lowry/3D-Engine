public class Normal extends Point3D{

    public Normal(float x, float y, float z) {
        super(x, y, z);
    }

    // returns the average normal
    // used for three normals making up a face
    public Normal averageNormals(Normal n1, Normal n2) {
        float newX = (this.getX() + n1.getX() + n2.getX()) / 3;
        float newY = (this.getY() + n1.getY() + n2.getY()) / 3;
        float newZ = (this.getZ() + n1.getZ() + n2.getZ()) / 3;
        return new Normal(newX, newY, newZ);
    }
}