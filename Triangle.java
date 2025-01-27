import java.util.ArrayList;
import java.util.List;
import java.awt.Color;
import java.awt.Polygon;

public class Triangle {
    private Vertex3D v1;
    private Vertex3D v2;
    private Vertex3D v3;
    private Vertex3D newV1;
    private Vertex3D newV2;
    private Vertex3D newV3;
    private Vertex3D newV4;
    private UV uv1;
    private UV uv2;
    private UV uv3;
    private UV newUV1;
    private UV newUV2;
    private UV newUV3;
    private UV newUV4;
    private Normal triangleNormal;
    private Vertex2D p1;
    private Vertex2D p2;
    private Vertex2D p3;
    private Vertex2D p4;
    private Texture texture;
    private float colorIntensity;
    private double depthFromCamera;
    private int numVertices = 3;
    private boolean isValid = true;

    public Triangle (Vertex3D v1, Vertex3D v2, Vertex3D v3, UV uv1, UV uv2, UV uv3, Normal normal1, Normal normal2, Normal normal3, int materialIndex) {
        this.v1 = v1;
        this.v2 = v2;
        this.v3 = v3;
        this.uv1 = uv1;
        this.uv2 = uv2;
        this.uv3 = uv3;
        this.newV1 = new Vertex3D(0, 0, 0);
        this.newV2 = new Vertex3D(0, 0, 0);
        this.newV3 = new Vertex3D(0, 0, 0);
        this.newV4 = new Vertex3D(0, 0, 0);

        this.triangleNormal = normal1.averageNormals(normal2, normal3);

        this.texture = GamePanel.texturess.getTexture(materialIndex-1);
        computeShade();
    }

    public void update() {

        this.isValid = true;
        this.numVertices = 3;

        newV1.setUV(this.uv1);
        newV2.setUV(this.uv2);
        newV3.setUV(this.uv3);

        this.newV1.setNewCords(v1.getNewX(), v1.getNewY(), v1.getNewZ(), this.newV1.getUV());
        this.newV2.setNewCords(v2.getNewX(), v2.getNewY(), v2.getNewZ(), this.newV2.getUV());
        this.newV3.setNewCords(v3.getNewX(), v3.getNewY(), v3.getNewZ(), this.newV3.getUV());

        backfaceCulling();

        checkClipping();

        if (this.isValid) {
            calculateDepthFromCamera();
            projectToScreen();
        }
    }

    public void projectToScreen() {
        p1 = newV1.castToScreen();
        p2 = newV2.castToScreen();
        p3 = newV3.castToScreen();
        p4 = newV4.castToScreen();
    }

    private void checkClipping() {
        float nearPlaneX = 10;

        List<Vertex3D> behind = new ArrayList<>();
        List<Vertex3D> inFront = new ArrayList<>();

        // classify the vertices
        if (this.newV1.getNewX() <= nearPlaneX) behind.add(this.newV1); else inFront.add(this.newV1);
        if (this.newV2.getNewX() <= nearPlaneX) behind.add(this.newV2); else inFront.add(this.newV2);
        if (this.newV3.getNewX() <= nearPlaneX) behind.add(this.newV3); else inFront.add(this.newV3);

        // Handle cases
        if (behind.size() == 0) {
            // All vertices in front, draw the triangle as-is
        } else if (behind.size() == 1) {
            // One vertex behind, clip into two triangles
            Vertex3D P1 = interpolate(behind.get(0), inFront.get(0), nearPlaneX);
            Vertex3D P2 = interpolate(behind.get(0), inFront.get(1), nearPlaneX);

            this.newV1.setNewCords(inFront.get(0).getNewX(), inFront.get(0).getNewY(), inFront.get(0).getNewZ(), inFront.get(0).getUV());
            this.newV2.setNewCords(inFront.get(1).getNewX(), inFront.get(1).getNewY(), inFront.get(1).getNewZ(), inFront.get(1).getUV());
            this.newV3.setNewCords(P2.getX(), P2.getY(), P2.getZ(), P2.getUV());
            this.newV4.setNewCords(P1.getX(), P1.getY(), P1.getZ(), P1.getUV());

            this.numVertices = 4;

        } else if (behind.size() == 2) {
            // Two vertices behind, clip into one triangle
            Vertex3D P1 = interpolate(behind.get(0), inFront.get(0), nearPlaneX);
            Vertex3D P2 = interpolate(behind.get(1), inFront.get(0), nearPlaneX);

            this.newV1.setNewCords(inFront.get(0).getNewX(), inFront.get(0).getNewY(), inFront.get(0).getNewZ(), inFront.get(0).getUV());
            this.newV2.setNewCords(P2.getX(), P2.getY(), P2.getZ(), P2.getUV());
            this.newV3.setNewCords(P1.getX(), P1.getY(), P1.getZ(), P1.getUV());
            
        } else {
            // All vertices behind, discard triangle
            this.isValid = false;
        }
    }

    private Vertex3D interpolate(Vertex3D from, Vertex3D to, float nearPlaneX) {
        float t = (nearPlaneX - from.getNewX()) / (to.getNewX() - from.getNewX());
        float newY = from.getNewY() + t * (to.getNewY() - from.getNewY());
        float newZ = from.getNewZ() + t * (to.getNewZ() - from.getNewZ());
        float newU = from.getUV().getU() + t * (to.getUV().getU() - from.getUV().getU());
        float newV = from.getUV().getV() + t * (to.getUV().getV() - from.getUV().getV());
        return new Vertex3D(nearPlaneX, newY, newZ, newU, newV);
    }

    private void calculateDepthFromCamera() {
        double distance1 = distanceToCamera(newV1);
        double distance2 = distanceToCamera(newV2);
        double distance3 = distanceToCamera(newV3);
        this.depthFromCamera = (distance1 + distance2 + distance3) / 3;
    }

    private double distanceToCamera(Vertex3D v) {
        double x = v.getNewX();
        double y = v.getNewY();
        double z = v.getNewZ();

        return v.getNewX();
    }

    private void backfaceCulling() {
        // Calculate vectors from v1 to v2 and v1 to v3
        float vectorAX = v2.getNewX() - v1.getNewX();
        float vectorAY = v2.getNewY() - v1.getNewY();
        float vectorAZ = v2.getNewZ() - v1.getNewZ();
        float vectorBX = v3.getNewX() - v1.getNewX();
        float vectorBY = v3.getNewY() - v1.getNewY();
        float vectorBZ = v3.getNewZ() - v1.getNewZ();

        // Calculate the normal of the triangle using the cross product
        float normalX = vectorAY * vectorBZ - vectorAZ * vectorBY;
        float normalY = vectorAZ * vectorBX - vectorAX * vectorBZ;
        float normalZ = vectorAX * vectorBY - vectorAY * vectorBX;

        // Calculate the vector from the camera to the triangle
        float viewVectorX = v1.getNewX();
        float viewVectorY = v1.getNewY();
        float viewVectorZ = v1.getNewZ();

        // Calculate the dot product of the normal and the view vector
        float dotProduct = normalX * viewVectorX + normalY * viewVectorY + normalZ * viewVectorZ;

        // If the dot product is positive, the triangle is facing away from the camera
        if (dotProduct < 0) {
            this.isValid = false;
        }
    }

    private void computeShade() {
        float ambientLight = 0.8f; // Increase ambient light to make everything brighter

        // Light intensities for each axis
        float lightIntensityX = 1.2f; // Increase light intensity
        float lightIntensityY = 1.5f; // Increase light intensity
        float lightIntensityZ = 1.3f; // Increase light intensity

        // Light directions for each axis
        float lightX = 1;
        float lightY = 1;
        float lightZ = 1;

        // Normalize the light directions
        float lengthX = (float)Math.sqrt(lightX * lightX);
        float lengthY = (float)Math.sqrt(lightY * lightY);
        float lengthZ = (float)Math.sqrt(lightZ * lightZ);
        lightX /= lengthX;
        lightY /= lengthY;
        lightZ /= lengthZ;

        // Calculate the dot product of the normal and the light directions
        float dotProductX = triangleNormal.getX() * lightX;
        float dotProductY = triangleNormal.getY() * lightY;
        float dotProductZ = triangleNormal.getZ() * lightZ;

        // Calculate the final intensity for each axis
        float intensityX = Math.max(ambientLight, lightIntensityX * dotProductX);
        float intensityY = Math.max(ambientLight, lightIntensityY * dotProductY);
        float intensityZ = Math.max(ambientLight, lightIntensityZ * dotProductZ);

        // Average the intensities
        this.colorIntensity = (intensityX + intensityY + intensityZ) / 3;
        this.colorIntensity = Math.min(this.colorIntensity, 1);
    }

    // getters and setters

    public int getNumberOfVertices() {
        return numVertices;
    }

    public Polygon toPolygon() {
        int[] xPoints = {(int)this.p1.getX(), (int)this.p2.getX(), (int)this.p3.getX()};
        int[] yPoints = {(int)this.p1.getY(), (int)this.p2.getY(), (int)this.p3.getY()};
        Polygon p = new Polygon(xPoints, yPoints, 3);
        return p;
    }

    public int[] xPoints() {
        if (this.getNumberOfVertices() == 3) {
            int[] xPoints = {(int)this.p1.getX(), (int)this.p2.getX(), (int)this.p3.getX()};
            return xPoints;
        } else {
            int[] xPoints = {(int)this.p1.getX(), (int)this.p2.getX(), (int)this.p3.getX(), (int)this.p4.getX()};
            return xPoints;
        }

    }

    public int[] yPoints() {
        if (this.getNumberOfVertices() == 3) {
            int[] yPoints = {(int)this.p1.getY(), (int)this.p2.getY(), (int)this.p3.getY()};
            return yPoints;
        } else {
            int[] yPoints = {(int)this.p1.getY(), (int)this.p2.getY(), (int)this.p3.getY(), (int)this.p4.getY()};
            return yPoints;
        }
    }

    public Vertex3D getV1() {
        return this.v1;
    }

    public Vertex3D getV2() {
        return this.v2;
    }

    public Vertex3D getV3() {
        return this.v3;
    }

    public UV getUV1() {
        return this.newV1.getUV();
    }

    public UV getUV2() {
        return this.newV2.getUV();
    }

    public UV getUV3() {
        return this.newV3.getUV();
    }

    public UV getUV4() {
        return this.newV4.getUV();
    }

    public Vertex3D getNewV1() {
        return this.newV1;
    }
    public Vertex3D getNewV2() {
        return this.newV2;
    }

    public Vertex3D getNewV3() {
        return this.newV3;
    }

    public Vertex3D getNewV4() {
        return this.newV4;
    }

    public Vertex2D getP1() {
        return this.p1;
    }

    public Vertex2D getP2() {
        return this.p2;
    }

    public Vertex2D getP3() {
        return this.p3;
    }

    public Vertex2D getP4() {
        return this.p4;
    }

    public double getDepthFromCamera() {
        return this.depthFromCamera;
    }

    public Texture gTexture() {
        return this.texture;
    }

    public float getColorIntensity() {
        return this.colorIntensity;
    }

    public boolean isValid() {
        return this.isValid;
    }
}