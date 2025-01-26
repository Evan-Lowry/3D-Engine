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
    public void testFloorDetection() {
        // flat triangle
        Vertex3D v1 = new Vertex3D(0, 0, 0);
        Vertex3D v2 = new Vertex3D(100, 0, 0);
        Vertex3D v3 = new Vertex3D(100, 100, 0);
        // non flat vertex
        Vertex3D v4 = new Vertex3D(0, 100, 100);

        // flat triangle
        Triangle t1 = new Triangle(v1, v2, v3, null, null, null, null, null, null, 0);
        // non flat triangle
        Triangle t2 = new Triangle(v1, v2, v4, null, null, null, null, null, null, 0);
        ArrayList<Triangle> triangles = new ArrayList<>();
        triangles.add(t1);
        triangles.add(t2);
        this.sector = new Sector(triangles);
        assertEquals(t1, this.sector.getFloors().getFirst());
    }
}
