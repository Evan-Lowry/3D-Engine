public class Triangle {
    private Vertex[] vertexs = new Vertex[3];
    private Vertex v1;
    private Vertex v2;
    private Vertex v3;
    private Vertex2D p1;
    private Vertex2D p2;
    private Vertex2D p3;
    private int materialIndex;
    private double depthFromCamera;

    public Triangle (Vertex v1, Vertex v2, Vertex v3, int materialIndex) {
        this.vertexs[0] = v1;
        this.vertexs[1] = v2;
        this.vertexs[2] = v3;
        this.materialIndex = materialIndex;
    }

    public void update() {
        v1 = vertexs[0];
        v2 = vertexs[1];
        v3 = vertexs[2];

        v1.normalizeCoordinates();
        v2.normalizeCoordinates();
        v3.normalizeCoordinates();

        // if (isValid()) {
            p1 = v1.castToScreen();
            // p2 = v2.castToScreen();
            // p3 = v3.castToScreen();
        // }
    }

    public Vertex[] getOriginalVertexs() {
        return this.vertexs;
    }

    public Vertex getV1() {
        return this.v1;
    }

    public Vertex getV2() {
        return this.v2;
    }

    public Vertex getV3() {
        return this.v3;
    }

    public Vertex2D getP1() {
        return this.p1;
    }

    public Vertex2D getP2() {
        return this.p2;
    }

    public Vertex2D getP3() {
        return this.p3;
    }

    public double getDepthFromCamera() {
        return this.depthFromCamera;
    }

    public int getColorIndex() {
        return this.materialIndex-1;
    }

    public boolean isValid() {
        // if (v1.getLegnth() < 0 || v2.getLegnth() < 0 || v3. getLegnth() < 0) {
        //     System.out.println(false);
        //     return false;
        // }
        System.out.println(true);
        return true;
    }
}