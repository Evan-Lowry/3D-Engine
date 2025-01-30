import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Sector {

    // stores all triangles in sector
    private ArrayList<Triangle> triangles;
    private ArrayList<Floor> floors = new ArrayList<>();
    private Floor currentFloor = null;
    private ArrayList<Wall> walls = new ArrayList<>();

    public Sector(ArrayList <Triangle> triangles) {
        this.triangles = triangles;
        generateWalls();
        generateFloors();
    }

    // takes all triangles and detects horizontal ones as floors
    private void generateFloors() {
        for (Triangle t : this.triangles) {
            // checks if it is horizontal
            if (isHorizontal(t)) {
                // adds to floor array
                this.floors.add(new Floor(t.getV1(), t.getV2(), t.getV3(), this.walls));
            }
        }
        this.currentFloor = this.floors.get(0);
    }

    // checks if triangle is horizontal
    private boolean isHorizontal(Triangle t) {
        // if all z coordinates are equal
        return t.getV1().getZ() == t.getV2().getZ() && t.getV1().getZ() == t.getV3().getZ();
    }

    private void generateWalls() {
        for (Triangle t : this.triangles) {
            // checks if it is vertical
            if (isVertical(t)) {
                // adds to wall array
                this.walls.add(new Wall(t));
            }
        }
    }

    // checks if triangle is horizontal
    private boolean isVertical(Triangle t) {
        // if all z coordinates are equal
        return verticalLine(t.getV1(), t.getV2()) || verticalLine(t.getV2(), t.getV3()) || verticalLine(t.getV3(), t.getV1());
    }

    //checks if two vertexs are on the same vertical line
    private boolean verticalLine(Vertex3D v1, Vertex3D v2) {
        return v1.getX() == v2.getX() && v1.getY() == v2.getY();
    }

    // returns the triangles as an array
    public Triangle[] getValidTriangles() {
        ArrayList<Triangle> validTriangles = new ArrayList<>();
        for (Triangle triangle : this.triangles) {
            triangle.update();
            if (triangle.isValid()) {
                validTriangles.add(triangle);
            }
        }
        Triangle[] array = new Triangle[validTriangles.size()];
        return validTriangles.toArray(array);
    }

    // returns the floor the player is in
    public Floor getFloor() {
        ArrayList<Floor> possibleFloors = new ArrayList<>();

        // if still inside current floor return the current floor
        // if (this.currentFloor != null && this.currentFloor.isPlayerInside()) {
        //     return this.currentFloor;
        // }

        // if not inside the current floor, check all other floors
        for (Floor floor : floors) { 
            if (floor.isPlayerInside() && GamePanel.c.getZ() > floor.getFloorHeight() + 20) {
                possibleFloors.add(floor);
            }
        }
        if (possibleFloors.isEmpty()) {
            GamePanel.c.setFloorHeight(-10000);
            return null;
        }

        Collections.sort(possibleFloors, (f1, f2) -> Double.compare(f2.getFloorHeight(), f1.getFloorHeight()));
        this.currentFloor = possibleFloors.getFirst();
        GamePanel.c.setFloorHeight(this.currentFloor.getFloorHeight());
        return this.currentFloor;
    }

    public ArrayList<Floor> getFloors() {
        return this.floors;
    }
}
