import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Map {
    // sectors are blocks of triangles that are loaded in at once
    private ArrayList <Sector> sectors = new ArrayList<>();
    private Sector currentSector;
    // stores everything to be scanned in ArrayLists
    private ArrayList <Vertex3D> vertexs = new ArrayList<>();
    private ArrayList <Triangle> triangles = new ArrayList<>();
    private ArrayList <Normal> normals = new ArrayList<>();
    private ArrayList <UV> uvs = new ArrayList<>();
    private int materialIndex;

    public Map(String filename) {
        // create a new scanner to read the file
        try {
            // finds the file the map.obj data is stored in
            Scanner input = new Scanner(new File(filename));
            // while there is still something to scan
            while (input.hasNext()) {
                // reads the first char/String of each line
                String nextLine = input.next();
                // if scanning vertex
                if (nextLine.equals("v")) {
                    readVertex(input);
                    // if scanning normals
                } else if (nextLine.equals("vn")) {
                    readNormal(input);
                    // if scanning UVs
                } else if (nextLine.equals("vt")) {
                    readUV(input);
                    // if scanning faces/triangles
                } else if (nextLine.equals("f")) {
                    readFace(input);
                    // if scanning in material
                } else if (nextLine.equals("usemtl")) {
                    readMaterial(input);
                    // if either scanning in comments or empty lines
                } else {
                    // skip line
                    input.nextLine();
                }
            }

            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.sectors.add(new Sector(this.triangles));
        this.currentSector = this.sectors.get(0);
        // prints out how may triangles are in the scene
        System.out.println("Number of Triangles: " + this.triangles.size());
    }

    // reads in vertex data and adds it to arraylist of vertexs
    private void readVertex(Scanner input) {
        // scans in coordinates
        float x = 100 * input.nextFloat();
        float z = 100 * input.nextFloat();
        float y = 100 * input.nextFloat();
        // adds the new vertex to ArrayList
        this.vertexs.add(new Vertex3D(x, y, z));
        // System.out.println("Vertex: (" + x + ", " + y + ", " + z + ")");
    }

    // reads in normal data and adds it to arraylist of normals
    private void readNormal(Scanner input) {
        // scans normals
        float x = input.nextFloat();
        float y = input.nextFloat();
        float z = input.nextFloat();
        // adds the new normal to ArrayList
        this.normals.add(new Normal(x, y, z));
        // System.out.println("Normal: (" + x + ", " + y + ", " + z + ")");
    }

    // reads in UV data and adds it to arraylist of UVs
    private void readUV(Scanner input) {
        // scans u and v coordinates
        float u = input.nextFloat();
        float v = input.nextFloat();
        // adds new UV to ArrayList
        this.uvs.add(new UV(u, v));
        // System.out.println("UV: (" + u + ", " + v + ")");
    }

    // reads in the face data and adds it to arraylist of faces
    private void readFace(Scanner input) {
        // creates string array to store data
        String[] data;
        // vertex data is stored:
        // vertexIndex/UVIndex/NormalIndex

        // scans in first vertex and splits at "/"
        data = input.next().split("/");
        // scans in each index for first vertex
        int indexV1 = Integer.parseInt(data[0]);
        int indexUV1 = Integer.parseInt(data[1]);
        int indexNormal1 = Integer.parseInt(data[2]);

        // second vertex
        data = input.next().split("/");
        int indexV2 = Integer.parseInt(data[0]);
        int indexUV2 = Integer.parseInt(data[1]);
        int indexNormal2 = Integer.parseInt(data[2]);

        // thrid vertex
        data = input.next().split("/");
        int indexV3 = Integer.parseInt(data[0]);
        int indexUV3 = Integer.parseInt(data[1]);
        int indexNormal3 = Integer.parseInt(data[2]);

        // fetches the vertexs from the ArrayList useing the indexs
        Vertex3D v1 = this.vertexs.get(indexV1-1);
        Vertex3D v2 = this.vertexs.get(indexV2-1);
        Vertex3D v3 = this.vertexs.get(indexV3-1);

        // fetches the UVs from the ArrayList useing the indexs
        UV uv1 = this.uvs.get(indexUV1-1);
        UV uv2 = this.uvs.get(indexUV2-1);
        UV uv3 = this.uvs.get(indexUV3-1);

        // fetches the normals from the ArrayList useing the indexs
        Normal normal1 = this.normals.get(indexNormal1-1);
        Normal normal2 = this.normals.get(indexNormal2-1);
        Normal normal3 = this.normals.get(indexNormal3-1);
        
        // creates the triangle with all of the data
        this.triangles.add(new Triangle(v1, v2, v3, uv1, uv2, uv3, normal1, normal2, normal3, this.materialIndex));
        // System.out.println("Triangle: (" + v1 + ", " + v2 + ", " + v3 + ")");
    }

    // reads in the material name
    private void readMaterial(Scanner input) {
        // all material are named coresponding to the index of the texture they reference
        //therefore I just need to store that index
        this.materialIndex = input.nextInt();
        // System.out.println("Material index: " + this.materialIndex);
    }

    // gets the sector
    public Sector getSector() {
        return this.currentSector;
    }

    // used for when the character goes from one to another 
    public void changeSector() {
        // not implememnted yet
    }

    // updates every frame
    // transforms all of the vertexs so the camera position is (0, 0)
    public void updateVertexs() {
        for (Vertex3D v : this.vertexs) {
            v.normalizeCoordinates();
        }
    }
}