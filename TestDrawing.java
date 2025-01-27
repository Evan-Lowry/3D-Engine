import org.junit.*;
import static org.junit.Assert.*;

public class TestDrawing {

    private Drawing draw;

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {

    }

    @Test
    // tests to see if barycentric coordinates work
    public void testBarycentricCoordinates() {
        this.draw = new Drawing();
        int x = 5;
        int y = 5;
        // creates a triangle out of points
        int[] xPoints = {0, 10, 0};
        int[] yPoints = {0, 0, 10};

        // checks and stores the weights of each coordinate
        double[] bary = this.draw.getBarycentricCoordinates(x, y, xPoints, yPoints);

        assertEquals(0, bary[0], 0.000001);
        assertEquals(0.5, bary[1], 0.000001);
        assertEquals(0.5, bary[2], 0.000001);
    }

    @Test
    // tests to see if 4 barycentric coordinates work
    public void testBarycentric4Coordinates() {
        this.draw = new Drawing();
        int x = 5;
        int y = 5;
        // creates a triangle out of points
        int[] xPoints = {0, 10, 10, 0};
        int[] yPoints = {0, 0, 10, 10};

        // checks and stores the weights of each coordinate
        double[] bary = this.draw.getBarycentricCoordinates4Vertices(x, y, xPoints, yPoints);

        assertEquals(0.5, bary[0], 0.000001);
        assertEquals(0, bary[1], 0.000001);
        assertEquals(0.5, bary[2], 0.000001);
        assertEquals(0, bary[3], 0.000001);
    }
}