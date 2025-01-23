public class Obstacle {

private Vertex3D[] vertexs = new Vertex3D[3];

    public Obstacle(Vertex3D v1, Vertex3D v2, Vertex3D v3) {
        this.vertexs[0] = v1;
        this.vertexs[1] = v2;
        this.vertexs[2] = v3;
    }
    
    public boolean isPlayerInside(Camera c) {
    
        for (int i = 0; i < 3; i++) {
            if (shortestDistanceToLine(c, this.vertexs[i], this.vertexs[(i+1)%3]) < 30) {
                return true;
            }
        }
        return false;
    }

    public double shortestDistanceToLine(Camera c, Vertex3D v1, Vertex3D v2) {

        double x0 = c.getX();
        double y0 = c.getY();
        double x1 = v1.getX();
        double y1 = v1.getY();
        double x2 = v2.getX();
        double y2 = v2.getY();


        // Numerator: (y2 - y1)x0 - (x2 - x1)y0 + x2y1 - y2x1
        double numerator = (y2 - y1) * x0 - (x2 - x1) * y0 + (x2 * y1) - (y2 * x1);

        // Denominator: √((y2 - y1)^2 + (x2 - x1)^2)
        double denominator = Math.sqrt(Math.pow(y2 - y1, 2) + Math.pow(x2 - x1, 2));


        System.out.println(numerator / denominator);

        // Final distance
        return numerator / denominator;
    }
}
