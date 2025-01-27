import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Sector {

    // stores all triangles in sector
    private ArrayList<Triangle> triangles;
    private ArrayList<Floor> floors = new ArrayList<>();
    private Floor currentFloor = null;
    private ArrayList<Edge> boundingEdges;

    public Sector(ArrayList <Triangle> triangles) {
        this.triangles = triangles;
        generateFloors();
        generateBoundingEdges();
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
        this.currentFloor = this.floors.get(0);
    }

    // checks if triangle is horizontal
    private boolean isHorizontal(Triangle t) {
        // if all z coordinates are equal
        return t.getV1().getZ() == t.getV2().getZ() && t.getV1().getZ() == t.getV3().getZ();
    }

    private void generateBoundingEdges() {
        // this.boundingEdges = new ArrayList<>();
        // for (Floor f : this.floors) {
        //     this.boundingEdges.add(f.getEdges()[0]);
        //     this.boundingEdges.add(f.getEdges()[1]);
        //     this.boundingEdges.add(f.getEdges()[2]);
        // }

        // for (int i = 0; i < this.boundingEdges.size(); i++) {
        //     for (int j = i + 1; j < this.boundingEdges.size(); j++) {
        //     if (this.boundingEdges.get(i).equals(this.boundingEdges.get(j))) {
        //         this.boundingEdges.remove(j);
        //         this.boundingEdges.remove(i);
        //         i--;
        //         break;
        //     }
        //     }
        // }
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
            if (floor.isPlayerInside() && GamePanel.c.getZ() > floor.getFloorHeight() + 30) {
                possibleFloors.add(floor);
            }
        }
        if (possibleFloors.isEmpty()) {
            return this.currentFloor;
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
