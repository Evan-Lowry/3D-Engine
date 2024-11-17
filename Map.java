import java.io.File;
import java.util.Scanner;
import java.awt.image.BufferedImage;

public class Map {
    private int numSectors;
    private Sector[] sectors;
    private int currentSectorIndex;
    private Sector currentSector;
    private int numWalls;
    private Wall[] walls;
    private int numVertexs;
    private Vertex[] vertexs;

    public Map(String filename) {
        // create the scanner to read the file
        try {
            Scanner input = new Scanner(new File(filename));
            // scanning number of sectors
            this.numSectors = input.nextInt();
            // drop to new line
            input.nextLine();
            // create the locations array
            this.sectors = new Sector[this.numSectors];
            // read in all of the sectors
            for(int i = 0; i < this.sectors.length; i++) {
                // scan in data
                // scanning number of vertexs
                this.numVertexs = input.nextInt();
                // drop to new line
                input.nextLine();
                // creates an array to store vertexs for a wall
                this.vertexs = new Vertex[numVertexs];
                for (int j = 0; j < vertexs.length; j++) {
                    Vertex v;
                    int x = input.nextInt();
                    int y = input.nextInt();
                    int z = input.nextInt();
                    // drop to new line
                    input.nextLine();
                    v = new Vertex(x,y,z);
                    vertexs[j] = v;
                }
                // scanning number of walls
                this.numWalls = input.nextInt();
                // drop to new line
                input.nextLine();
                // creates an array to store walls for a sector
                this.walls = new Wall[numWalls];
                for (int j = 0; j < walls.length; j++) {
                    Wall w;
                    int vertexIndex1 = input.nextInt();
                    int vertexIndex2 = input.nextInt();
                    Vertex v1 = this.vertexs[vertexIndex1-1];
                    Vertex v2 = this.vertexs[vertexIndex2-1];
                    int materialIndex = input.nextInt();
                    input.nextLine();
                    w = new Wall(v1, v2, materialIndex);
                    walls[j] = w;
                }
                this.sectors[i] = new Sector(walls);
            }
            
            // set the current location
            this.currentSector = sectors[0];
            // close the scanner
            input.close();
        } catch(Exception e) {
            // print out any errors
            e.printStackTrace();
        }
    }

    public Sector getSector() {
        return this.currentSector;
    }

    public void changeSector() {
    }
}