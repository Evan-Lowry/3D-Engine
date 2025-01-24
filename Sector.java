import java.util.ArrayList;

public class Sector {

    // stores all triangles in sector
    private ArrayList<Triangle> triangles;
    private ArrayList<Floor> floors;
    private Floor currentFloor = null;

    public Sector(ArrayList <Triangle> triangles) {
        this.triangles = triangles;
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
