public class Edge {
    private Vertex3D vertex1;
    private Vertex3D vertex2;

    public Edge(Vertex3D vertex1, Vertex3D vertex2) {
        this.vertex1 = vertex1;
        this.vertex2 = vertex2;
    }

    public boolean equals(Edge e) {
        boolean eqauls1 = this.getVertex1() == e.getVertex1() && this.getVertex2() == e.getVertex2();
        boolean eqauls2 = this.getVertex1() == e.getVertex2() && this.getVertex2() == e.getVertex1();

        return eqauls1 || eqauls2;
    }

    public Vertex3D getVertex1() {
        return vertex1;
    }

    public void setVertex1(Vertex3D vertex1) {
        this.vertex1 = vertex1;
    }

    public Vertex3D getVertex2() {
        return vertex2;
    }

    public void setVertex2(Vertex3D vertex2) {
        this.vertex2 = vertex2;
    }

    public String toString() {
        return "Edge from " + vertex1 + " to " + vertex2;
    }
}
