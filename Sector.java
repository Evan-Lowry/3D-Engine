import java.util.ArrayList;

public class Sector {

    // stores all triangles in sector
    private ArrayList<Triangle> triangles;
    private ArrayList<Floor> floors = new ArrayList<>();
    private Floor currentFloor = null;
    private ArrayList<Edge> boundingEdges;

    public Sector(ArrayList <Triangle> triangles) {
        this.triangles = triangles;
        generateFloors();
    }

    // takes all triangles and detects horizontal ones as floors
    private void generateFloors() {
        for (Triangle t : this.triangles) {
            // checks if it is horizontal
            if (isHorizontal(t)) {
                // adds to floor array
                this.floors.add(new Floor(t.getV1(), t.getV2(), t.getV3()));
            }
        }
    }

    // checks if triangle is horizontal
    private boolean isHorizontal(Triangle t) {
        // if all z coordinates are equal
        return t.getV1().getZ() == t.getV2().getZ() && t.getV1().getZ() == t.getV3().getZ();
    }

    private void generateBoundingEdges() {
        
    }



    // returns the triangles as an array
    public Triangle[] getTriangles() {
        Triangle[] array = new Triangle[this.triangles.size()];
        return this.triangles.toArray(array);
    }

    // returns the floor the player is in
    public Floor getFloor() {

        // if still inside current floor return the current floor
        if (this.currentFloor != null && this.currentFloor.isPlayerInside()) {
            return this.currentFloor;
        }

        // if not inside the current floor, check all other floors
        for (Floor floor : floors) { 
            if (floor != this.currentFloor && floor.isPlayerInside()) {
                this.currentFloor = floor;
                System.out.println("Changed Floor");
                return this.currentFloor;
            }
        }

        return this.currentFloor;
    }
}
