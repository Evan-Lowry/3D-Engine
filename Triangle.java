import java.util.ArrayList;
import java.util.List;
import java.awt.Color;

public class Triangle {
    private Vertex v1;
    private Vertex v2;
    private Vertex v3;
    private UV uv1;
    private UV uv2;
    private UV uv3;
    private Normal normal1;
    private Normal normal2;
    private Normal normal3;
    private Vertex newV1;
    private Vertex newV2;
    private Vertex newV3;
    private Vertex newV4;
    private Vertex2D p1;
    private Vertex2D p2;
    private Vertex2D p3;
    private Vertex2D p4;
    private Color material;
    private double depthFromCamera;
    private int numVertices = 3;
    private boolean isValid = true;

    public Triangle (Vertex v1, Vertex v2, Vertex v3, UV uv1, UV uv2, UV uv3, Normal normal1, Normal normal2, Normal normal3, int materialIndex) {
        this.v1 = v1;
        this.v2 = v2;
        this.v3 = v3;
        this.uv1 = uv1;
        this.uv2 = uv2;
        this.uv3 = uv3;
        this.normal1 = normal1;
        this.normal2 = normal2;
        this.normal3 = normal3;
        this.material = GamePanel.textures[materialIndex-1];
        computeColor();
    }

    public void update() {

        this.isValid = true;
        this.numVertices = 3;

        this.newV1 = this.v1.normalizeCoordinates();
        this.newV2 = this.v2.normalizeCoordinates();
        this.newV3 = this.v3.normalizeCoordinates();
        this.newV4 = new Vertex(0, 0, 0);

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
        double nearPlaneX = 10;

        List<Vertex> behind = new ArrayList<>();
        List<Vertex> inFront = new ArrayList<>();

        // classify the vertices
        if (newV1.getX() <= nearPlaneX) behind.add(newV1); else inFront.add(newV1);
        if (newV2.getX() <= nearPlaneX) behind.add(newV2); else inFront.add(newV2);
        if (newV3.getX() <= nearPlaneX) behind.add(newV3); else inFront.add(newV3);

        // Handle cases
        if (behind.size() == 0) {
            // All vertices in front, draw the triangle as-is
        } else if (behind.size() == 1) {
            // One vertex behind, clip into two triangles
            Vertex P1 = interpolate(behind.get(0), inFront.get(0), nearPlaneX);
            Vertex P2 = interpolate(behind.get(0), inFront.get(1), nearPlaneX);

            this.newV1 = inFront.get(0);
            this.newV2 = inFront.get(1);
            this.newV3 = P2;
            this.newV4 = P1;

            this.numVertices = 4;

        } else if (behind.size() == 2) {
            // Two vertices behind, clip into one triangle
            Vertex P1 = interpolate(behind.get(0), inFront.get(0), nearPlaneX);
            Vertex P2 = interpolate(behind.get(1), inFront.get(0), nearPlaneX);

            this.newV1 = P1;
            this.newV2 = P2;
            this.newV3 = inFront.get(0);
            
        } else {
            // All vertices behind, discard triangle
            this.isValid = false;
        }
    }

    private Vertex interpolate(Vertex from, Vertex to, double nearPlaneX) {
        double t = (nearPlaneX - from.getX()) / (to.getX() - from.getX());
        double newY = from.getY() + t * (to.getY() - from.getY());
        double newZ = from.getZ() + t * (to.getZ() - from.getZ());
        return new Vertex(nearPlaneX, newY, newZ);
    }

    private void calculateDepthFromCamera() {
        double distance1 = distanceToCamera(newV1); // double distance1 = newV1
        double distance2 = distanceToCamera(newV2); // double distance2 = newV2
        double distance3 = distanceToCamera(newV3); // double distance3 = newV3
        this.depthFromCamera = Math.max(distance1, Math.max(distance2, distance3));
    }

    private double distanceToCamera(Vertex v) {
        double x = v.getX();
        double y = v.getY();
        double z = v.getZ();

        return Math.sqrt(x*x + y*y + z*z);
    }

    private void backfaceCulling() {
        double x1 = newV2.getX() - newV1.getX();
        double y1 = newV2.getY() - newV1.getY();
        double z1 = newV2.getZ() - newV1.getZ();

        double x2 = newV3.getX() - newV1.getX();
        double y2 = newV3.getY() - newV1.getY();
        double z2 = newV3.getZ() - newV1.getZ();

        double normalX = y1*z2 - z1*y2;
        double normalY = z1*x2 - x1*z2;
        double normalZ = x1*y2 - y1*x2;

        double viewX = newV1.getX();
        double viewY = newV1.getY();
        double viewZ = newV1.getZ();

        double dotProduct = normalX*viewX + normalY*viewY + normalZ*viewZ;

        if (dotProduct > 0) {
            this.isValid = false;
        }
    }

    private void computeColor() {
        double ambientLight = 0.8; // Increase ambient light to make everything brighter

        // Calculate the normal of the triangle
        double x1 = v2.getX() - v1.getX();
        double y1 = v2.getY() - v1.getY();
        double z1 = v2.getZ() - v1.getZ();

        double x2 = v3.getX() - v1.getX();
        double y2 = v3.getY() - v1.getY();
        double z2 = v3.getZ() - v1.getZ();

        double normalX = y1 * z2 - z1 * y2;
        double normalY = z1 * x2 - x1 * z2;
        double normalZ = x1 * y2 - y1 * x2;

        // Normalize the normal
        double length = Math.sqrt(normalX * normalX + normalY * normalY + normalZ * normalZ);
        normalX /= length;
        normalY /= length;
        normalZ /= length;

        // Light intensities for each axis
        double lightIntensityX = 1.2; // Increase light intensity
        double lightIntensityY = 1.5; // Increase light intensity
        double lightIntensityZ = 1.3; // Increase light intensity

        // Light directions for each axis
        double lightX = 1;
        double lightY = 1;
        double lightZ = 1;

        // Normalize the light directions
        double lengthX = Math.sqrt(lightX * lightX);
        double lengthY = Math.sqrt(lightY * lightY);
        double lengthZ = Math.sqrt(lightZ * lightZ);
        lightX /= lengthX;
        lightY /= lengthY;
        lightZ /= lengthZ;

        // Calculate the dot product of the normal and the light directions
        double dotProductX = normalX * lightX;
        double dotProductY = normalY * lightY;
        double dotProductZ = normalZ * lightZ;

        // Calculate the final intensity for each axis
        double intensityX = Math.max(ambientLight, lightIntensityX * dotProductX);
        double intensityY = Math.max(ambientLight, lightIntensityY * dotProductY);
        double intensityZ = Math.max(ambientLight, lightIntensityZ * dotProductZ);

        // Average the intensities
        double intensity = (intensityX + intensityY + intensityZ) / 3;

        // Apply the intensity to the material color
        int red = (int) (material.getRed() * intensity);
        int green = (int) (material.getGreen() * intensity);
        int blue = (int) (material.getBlue() * intensity);

        // Ensure the color components are within the valid range
        red = Math.min(255, Math.max(0, red));
        green = Math.min(255, Math.max(0, green));
        blue = Math.min(255, Math.max(0, blue));

        // Set the new color
        this.material = new Color(red, green, blue);
    }

    public int getNumberOfVertices() {
        return numVertices;
    }

    public Vertex getV1() {
        return this.v1;
    }

    public Vertex getV2() {
        return this.v2;
    }

    public Vertex getV3() {
        return this.v3;
    }

    public UV getUV1() {
        return this.uv1;
    }

    public UV getUV2() {
        return this.uv2;
    }

    public UV getUV3() {
        return this.uv3;
    }

    public Vertex getNewV1() {
        return this.newV1;
    }

    public Vertex getNewV2() {
        return this.newV2;
    }

    public Vertex getNewV3() {
        return this.newV3;
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

    public Color getMaterial() {
        return this.material;
    }

    public boolean isValid() {
        return this.isValid;
    }
}