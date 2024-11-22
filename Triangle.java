public class Triangle {
    private Vertex[] vertexs;
    private Vertex v1;
    private Vertex v2;
    private Vertex v3;
    private Vertex2D p1;
    private Vertex2D p2;
    private Vertex2D p3;
    private int materialIndex;

    public Triangle (Vertex[] vertexs, int materialIndex) {
        this.vertexs = vertexs;
        this.materialIndex = materialIndex;
    }

    public void update() {
        v1 = vertexs[0].normalizeCoordinates();
        v2 = vertexs[1].normalizeCoordinates();
        v3 = vertexs[2].normalizeCoordinates();

        // if (isValid()) {
        //     p1 = v1.castToScreen();
        //     p2 = v2.castToScreen();
        //     p3 = v3.castToScreen();
        // }
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
        return this.;
    }

    public int getColorIndex() {
        return this.materialIndex-1;
    }

    public boolean isValid() {
        boolean isvalid = false;
        if () {
            
        }
        return isValid;
    }
}