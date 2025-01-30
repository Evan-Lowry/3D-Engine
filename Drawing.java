import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.util.Arrays;

public class Drawing {

    private int pixelSize = 8;

    public Drawing() {
    }

    // used to draw all items to screen
    public void draw(Graphics2D g2) {
        // gets all valid triangles to be drawn
        Triangle[] triangles = GamePanel.map.getSector().getValidTriangles();
        // sorts the triangles in order of farthest from camera to closest
        Arrays.sort(triangles, (t1, t2) -> Double.compare(t2.getDepthFromCamera(), t1.getDepthFromCamera()));
        
        for (Triangle t : triangles) {
            if (t.gTexture() != null) {
                if (t.getNumberOfVertices() == 3) {
                    drawTexturedTriangle(g2, t);   
                } else {
                    drawTexturedQuad(g2, t);
                }
            }
        }
    }

        public void drawTexturedTriangle(Graphics2D g2, Triangle t) {

            // Get triangle points and UVs
            int[] xPoints = t.xPoints();
            int[] yPoints = t.yPoints();
            double[] uPoints = {t.getUV1().getU(), t.getUV2().getU(), t.getUV3().getU()};
            double[] vPoints = {t.getUV1().getV(), t.getUV2().getV(), t.getUV3().getV()};
            
            // Find bounding box and constrain to screen dimensions
            int minX = Math.max(0, Math.min(Math.min(xPoints[0], xPoints[1]), xPoints[2]));
            minX = ((int)(minX/pixelSize))*pixelSize;
            int maxX = Math.min(GamePanel.windowWidth, Math.max(Math.max(xPoints[0], xPoints[1]), xPoints[2]));
            maxX = ((int)(maxX/pixelSize))*pixelSize;
            int minY = Math.max(0, Math.min(Math.min(yPoints[0], yPoints[1]), yPoints[2]));
            minY = ((int)(minY/pixelSize))*pixelSize;
            int maxY = Math.min(GamePanel.windowHeight, Math.max(Math.max(yPoints[0], yPoints[1]), yPoints[2]));
            maxY = ((int)(maxY/pixelSize))*pixelSize;

            // Store Z coordinates for perspective correction
            double[] zPoints = {t.getNewV1().getNewX(), t.getNewV2().getNewX(), t.getNewV3().getNewX()};
            
            // For each pixel in the bounding box
            for (int x = minX; x <= maxX; x+=pixelSize) {
                for (int y = minY; y <= maxY; y+=pixelSize) {
                    // Calculate barycentric coordinates
                    double[] bary = getBarycentricCoordinates(x, y, xPoints, yPoints);
                    
                    if (bary[0] >= 0 && bary[1] >= 0 && bary[2] >= 0) {
                        // Perspective correction factors
                        double w0 = bary[0] / zPoints[0];
                        double w1 = bary[1] / zPoints[1];
                        double w2 = bary[2] / zPoints[2];
                        double wSum = w0 + w1 + w2;
                        
                        // Perspective-correct UV interpolation
                        double u = (w0 * uPoints[0] + w1 * uPoints[1] + w2 * uPoints[2]) / wSum;
                        double v = (w0 * vPoints[0] + w1 * vPoints[1] + w2 * vPoints[2]) / wSum;

                        // Get color from texture and draw pixel
                        int textureX = (int)((u * 32) % 32);
                        int textureY = (int)((v * 32) % 32);
                        if (textureX >= 0 && textureX < 32 && 
                            textureY >= 0 && textureY < 32) {
                            Color pixelColor = t.gTexture().getColor(textureY, textureX);
                            float r = pixelColor.getRed() * t.getColorIntensity() / 255;
                            float g = pixelColor.getGreen() * t.getColorIntensity() / 255;
                            float b = pixelColor.getBlue() * t.getColorIntensity() / 255;
                            g2.setColor(new Color(r,g,b));
                            g2.fillRect(x, y, pixelSize, pixelSize);
                        }
                    }
                }
            }
        }

        public double[] getBarycentricCoordinates(int x, int y, int[] xPoints, int[] yPoints) {
            double denominator = ((yPoints[1] - yPoints[2]) * (xPoints[0] - xPoints[2]) + 
                                    (xPoints[2] - xPoints[1]) * (yPoints[0] - yPoints[2]));
            double a = ((yPoints[1] - yPoints[2]) * (x - xPoints[2]) + 
                        (xPoints[2] - xPoints[1]) * (y - yPoints[2])) / denominator;
            double b = ((yPoints[2] - yPoints[0]) * (x - xPoints[2]) + 
                        (xPoints[0] - xPoints[2]) * (y - yPoints[2])) / denominator;
            double c = 1 - a - b;
            return new double[]{a, b, c};
        }

        public void drawTexturedQuad(Graphics2D g2, Triangle t) {
            int[] xPoints = t.xPoints();
            int[] yPoints = t.yPoints();
            double[] uPoints = {t.getUV1().getU(), t.getUV2().getU(), t.getUV3().getU(), t.getUV4().getU()};
            double[] vPoints = {t.getUV1().getV(), t.getUV2().getV(), t.getUV3().getV(), t.getUV4().getV()};

            int minX = Math.max(0, Math.min(Math.min(Math.min(xPoints[0], xPoints[1]), xPoints[2]), xPoints[3]));
            minX = ((int)(minX/pixelSize))*pixelSize;
            int maxX = Math.min(GamePanel.windowWidth, Math.max(Math.max(Math.max(xPoints[0], xPoints[1]), xPoints[2]), xPoints[3]));
            maxX = ((int)(maxX/pixelSize))*pixelSize;
            int minY = Math.max(0, Math.min(Math.min(Math.min(yPoints[0], yPoints[1]), yPoints[2]), yPoints[3]));
            minY = ((int)(minY/pixelSize))*pixelSize;
            int maxY = Math.min(GamePanel.windowHeight, Math.max(Math.max(Math.max(yPoints[0], yPoints[1]), yPoints[2]), yPoints[3]));
            maxY = ((int)(maxY/pixelSize))*pixelSize;

            double[] zPoints = {t.getNewV1().getNewX(), t.getNewV2().getNewX(), t.getNewV3().getNewX(), t.getNewV4().getNewX()};

            for (int x = minX; x <= maxX; x+=pixelSize) {
                for (int y = minY; y <= maxY; y+=pixelSize) {
                    double[] bary = getBarycentricCoordinates4Vertices(x, y, xPoints, yPoints);
                    
                    if (bary[0] >= 0 && bary[1] >= 0 && bary[2] >= 0 && bary[3] >= 0) {
                        double w0 = bary[0] / zPoints[0];
                        double w1 = bary[1] / zPoints[1];
                        double w2 = bary[2] / zPoints[2];
                        double w3 = bary[3] / zPoints[3];
                        double wSum = w0 + w1 + w2 + w3;
                        
                        double u = (w0 * uPoints[0] + w1 * uPoints[1] + w2 * uPoints[2] + w3 * uPoints[3]) / wSum;
                        double v = (w0 * vPoints[0] + w1 * vPoints[1] + w2 * vPoints[2] + w3 * vPoints[3]) / wSum;
                        int textureX = (int)((u * 32) % 32);
                        int textureY = (int)((v * 32) % 32);
                        if (textureX >= 0 && textureX < 32 && textureY >= 0 && textureY < 32) {
                            Color pixelColor = t.gTexture().getColor(textureY, textureX);
                            float r = pixelColor.getRed() * t.getColorIntensity() / 255;
                            float g = pixelColor.getGreen() * t.getColorIntensity() / 255;
                            float b = pixelColor.getBlue() * t.getColorIntensity() / 255;
                            g2.setColor(new Color(r,g,b));
                            g2.fillRect(x, y, pixelSize, pixelSize);
                        }
                    }
                }
            }
        }

        public double[] getBarycentricCoordinates4Vertices(int x, int y, int[] xPoints, int[] yPoints) {
            double[] weights = new double[4];
            
            // Split quad into two triangles and check which one contains the point
            double[] bary1 = getBarycentricCoordinates(x, y, 
                new int[]{xPoints[0], xPoints[1], xPoints[2]}, 
                new int[]{yPoints[0], yPoints[1], yPoints[2]});
                
            if (bary1[0] >= 0 && bary1[1] >= 0 && bary1[2] >= 0) {
                weights[0] = bary1[0];
                weights[1] = bary1[1];
                weights[2] = bary1[2];
                weights[3] = 0;
            } else {
                double[] bary2 = getBarycentricCoordinates(x, y, 
                    new int[]{xPoints[0], xPoints[2], xPoints[3]}, 
                    new int[]{yPoints[0], yPoints[2], yPoints[3]});
                weights[0] = bary2[0];
                weights[2] = bary2[1];
                weights[3] = bary2[2];
                weights[1] = 0;
            }
            
            return weights;
        }
}