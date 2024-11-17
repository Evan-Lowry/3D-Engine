public class Sector {
    private Wall[] walls;

    public Sector(Wall[] walls){
        // create the scanner to read the file
        this.walls = walls;
    }

    public Wall[] getWalls() {
        return this.walls;
    }
}
