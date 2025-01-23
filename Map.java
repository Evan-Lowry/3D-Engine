import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.awt.image.BufferedImage;

public class Map {
    private int numSectors;
    private ArrayList <Sector> sectors = new ArrayList<>();
    private int currentSectorIndex;
    private Sector currentSector;
    private ArrayList <Vertex3D> vertexs = new ArrayList<>();
    private ArrayList <Triangle> triangles = new ArrayList<>();
    private ArrayList <Normal> normals = new ArrayList<>();
    private ArrayList <UV> uvs = new ArrayList<>();
    private ArrayList <Floor> floors = new ArrayList<>();
    private ArrayList <Obstacle> obstacles = new ArrayList<>();
    private int materialIndex;

    public Map(String filename) {
        // create a new scanner to read the file
        try {
            Scanner input = new Scanner(new File(filename));

            while (input.hasNext()) {
                String nextLine = input.next();
                if (nextLine.equals("v")) {
                    readVertex(input);
                } else if (nextLine.equals("vn")) {
                    readNormal(input);
                } else if (nextLine.equals("vt")) {
                    readUV(input);
                } else if (nextLine.equals("f")) {
                    readFace(input);
                } else if (nextLine.equals("usemtl")) {
                    readMaterial(input);
                } else {
                    input.nextLine();
                }
            }

            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.sectors.add(new Sector(this.triangles, this.floors, this.obstacles));
        this.currentSector = this.sectors.get(0);
        System.out.println(this.triangles.size());
    }

    // reads in vertex data and adds it to arraylist of vertexs
    private void readVertex(Scanner input) {
        float x = 100 * input.nextFloat();
        float z = 100 * input.nextFloat();
        float y = 100 * input.nextFloat();
        this.vertexs.add(new Vertex3D(x, y, z));
        // System.out.println("Vertex: (" + x + ", " + y + ", " + z + ")");
    }

    // reads in normal data and adds it to arraylist of normals
    private void readNormal(Scanner input) {
        float x = input.nextFloat();
        float y = input.nextFloat();
        float z = input.nextFloat();
        this.normals.add(new Normal(x, y, z));
        // System.out.println("Normal: (" + x + ", " + y + ", " + z + ")");
    }

    // reads in UV data and adds it to arraylist of UVs
    private void readUV(Scanner input) {
        float u = input.nextFloat();
        float v = input.nextFloat();
        this.uvs.add(new UV(u, v));
        // System.out.println("UV: (" + u + ", " + v + ")");
    }

    // reads in the face data and adds it to arraylist of faces
    private void readFace(Scanner input) {
        String[] data;

        data = input.next().split("/");
        int indexV1 = Integer.parseInt(data[0]);
        int indexUV1 = Integer.parseInt(data[1]);
        int indexNormal1 = Integer.parseInt(data[2]);

        data = input.next().split("/");
        int indexV2 = Integer.parseInt(data[0]);
        int indexUV2 = Integer.parseInt(data[1]);
        int indexNormal2 = Integer.parseInt(data[2]);

        data = input.next().split("/");
        int indexV3 = Integer.parseInt(data[0]);
        int indexUV3 = Integer.parseInt(data[1]);
        int indexNormal3 = Integer.parseInt(data[2]);

        Vertex3D v1 = this.vertexs.get(indexV1-1);
        Vertex3D v2 = this.vertexs.get(indexV2-1);
        Vertex3D v3 = this.vertexs.get(indexV3-1);

        UV uv1 = this.uvs.get(indexUV1-1);
        UV uv2 = this.uvs.get(indexUV2-1);
        UV uv3 = this.uvs.get(indexUV3-1);

        Normal normal1 = this.normals.get(indexNormal1-1);
        Normal normal2 = this.normals.get(indexNormal2-1);
        Normal normal3 = this.normals.get(indexNormal3-1);
        
        this.triangles.add(new Triangle(v1, v2, v3, uv1, uv2, uv3, normal1, normal2, normal3, this.materialIndex));
        // System.out.println("Triangle: (" + v1 + ", " + v2 + ", " + v3 + ")");
    }

    private void readMaterial(Scanner input) {
        this.materialIndex = input.nextInt();
        // System.out.println("Material index: " + this.materialIndex);
    }

    public Sector getSector() {
        return this.currentSector;
    }

    public void changeSector() {
    }

    public void updateVertexs() {
        for (Vertex3D v : this.vertexs) {
            v.normalizeCoordinates();
        }
    }
}