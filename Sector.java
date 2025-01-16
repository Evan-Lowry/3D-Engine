import java.util.ArrayList;

public class Sector {
    private Triangle[] triangles;
    private Floor[] floors;
    private Floor currentFloor;
    private Obstacle[] obstacles;
    private ArrayList <Edge> sharedEdges;

    public Sector(Triangle[] triangles, Floor[] floors, Obstacle[] obstacles) {
        this.triangles = triangles;
        this.floors = floors;
        this.obstacles = obstacles;
    }

        public Triangle[] getTriangles() {
        return this.triangles;
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
