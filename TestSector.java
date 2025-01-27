import org.junit.*;
import static org.junit.Assert.*;

import java.beans.Transient;
import java.util.ArrayList;

public class TestSector {

    private Sector sector;

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {

    }

    @Test
    // tests to see if floor method properly detects flat triangles
    public void testFloorDetection() {
        // flat triangle
        Vertex3D v1 = new Vertex3D(0, 0, 0);
        Vertex3D v2 = new Vertex3D(100, 0, 0);
        Vertex3D v3 = new Vertex3D(100, 100, 0);
        // non flat vertex
        Vertex3D v4 = new Vertex3D(0, 100, 100);
        // creates placeholder normal
        Normal n = new Normal(0, 0, 1);
        // creates placeholder UV
        UV uv = new UV(0, 0);
        // flat triangle
        Triangle t1 = new Triangle(v1, v2, v3, uv, uv, uv, n, n, n, 1);
        // non flat triangle
        Triangle t2 = new Triangle(v1, v2, v4, uv, uv, uv, n, n, n, 1);
        ArrayList<Triangle> triangles = new ArrayList<>();
        triangles.add(t1);
        triangles.add(t2);
        this.sector = new Sector(triangles);
        // creates a triangle of the detected floor
        Triangle t3 = this.sector.getFloors().getFirst().getTriangle();
        // check if all vertices match
        boolean equal = t1.getV1() == t3.getV1() && t1.getV2() == t3.getV2() && t1.getV3() == t3.getV3();
        assertEquals(true, equal);
    }

    @Test
    // tests to see if floor method properly detects flat triangles
    public void testsLackFloorDetection() {
        // flat triangle
        Vertex3D v1 = new Vertex3D(0, 0, 0);
        Vertex3D v2 = new Vertex3D(100, 0, 0);
        Vertex3D v3 = new Vertex3D(100, 100, 0);
        // non flat vertex
        Vertex3D v4 = new Vertex3D(0, 100, 100);
        // creates placeholder normal
        Normal n = new Normal(0, 0, 1);
        // creates placeholder UV
        UV uv = new UV(0, 0);
        // non flat triangle
        Triangle t1 = new Triangle(v1, v4, v3, uv, uv, uv, n, n, n, 1);
        // non flat triangle
        Triangle t2 = new Triangle(v1, v2, v4, uv, uv, uv, n, n, n, 1);
        ArrayList<Triangle> triangles = new ArrayList<>();
        triangles.add(t1);
        triangles.add(t2);
        this.sector = new Sector(triangles);
        // checks if array is empty
        boolean empty = this.sector.getFloors().isEmpty();
        assertEquals(true, empty);
    }
}
