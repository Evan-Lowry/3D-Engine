public class UV extends Point2D{

    public UV(float u, float v) {
        super(u, v);
    }

    public float getU() {
        return super.getX();
    }

    public float getV() {
        return super.getY();
    }

    public void setU(float u) {
        super.setX(u);
    }

    public void setV(float v) {
        super.setY(v);
    }
}
