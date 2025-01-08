import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.Robot;

public class MouseAiming implements MouseMotionListener {

    public double rot, pitch;

    public MouseAiming() {
        mouseX = 0;
        mouseY = 0;
        mouseMoved = false;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
        mouseMoved = true;
    }

    private void recenterMouse() {
        robot.mouseMove(screenWidth/2, screenHeight/2);
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }
}