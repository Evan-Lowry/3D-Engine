import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.Robot;

public class MouseAiming implements MouseMotionListener {

    public double rot, pitch, sensitivity = 0.1;
    public boolean mouseMoved;

    public MouseAiming() {
        rot = 0;
        pitch = 0;
        mouseMoved = false;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        rot = e.getXOnScreen() - (GamePanel.windowWidth / 2);
        rot = rot * sensitivity;

        pitch = e.getYOnScreen() - (GamePanel.windowHeight / 2);
        pitch = - pitch * sensitivity;

        mouseMoved = true;
    }

    public void recenterMouse() {
        try {
            Robot robot = new Robot();
            robot.mouseMove(GamePanel.windowWidth / 2, GamePanel.windowHeight / 2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseMoved(e);
    }
}