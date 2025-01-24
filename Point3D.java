public class Point3D extends Point2D{

    float z;

    public Point3D(float x, float y, float z) {
        super(x, y);
        this.z = z;
    }

        // setters
    
        public void setZ(float z) {
            this.z = z;
        }
    
        // getters
    
        public float getZ() {
            return this.z;
        }
}
