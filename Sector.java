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

        calculateSharedEdges();
        }

        private void calculateSharedEdges() {
            sharedEdges = new ArrayList<>();
            for (Floor f1 : floors) {
            for (Floor f2 : floors) {
                if (f1 != f2) {
                for (Edge e1 : f1.getEdges()) {
                    for (Edge e2 : f2.getEdges()) {
                    if (e1.equals(e2)) {
                        this.sharedEdges.add(e1);
                    }
                    }
                }
                }
            }
            }

            for (Floor floor : floors) {
                floor.addSharedEdges(this.sharedEdges);
            }
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
