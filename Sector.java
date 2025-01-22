import java.util.ArrayList;

public class Sector {
    private ArrayList<Triangle> triangles;
    private ArrayList<Floor> floors;
    private Floor currentFloor;
    private ArrayList<Obstacle> obstacles;
    private ArrayList<Edge> sharedEdges;

    public Sector(ArrayList <Triangle> triangles, ArrayList <Floor> floors, ArrayList <Obstacle> obstacles) {
        this.triangles = triangles;
        this.floors = floors;
        this.obstacles = obstacles;
        this.sharedEdges = new ArrayList<>();
        this.currentFloor = null;
    }

    public Triangle[] getTriangles() {
        Triangle[] array = new Triangle[this.triangles.size()];
        return this.triangles.toArray(array);
    }

    public Floor getFloor(Camera c) {

        // if still inside current floor return the current floor
        if (this.currentFloor != null && this.currentFloor.isPlayerInside(c)) {
            return this.currentFloor;
        }

        // if not inside the current floor, check all other floors
        for (Floor floor : floors) { 
            if (floor != this.currentFloor && floor.isPlayerInside(c)) {
                this.currentFloor = floor;
                System.out.println("Changed Floor");
                return this.currentFloor;
            }
        }

        return this.currentFloor;
    }
}
