import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.Robot;

public class MouseAiming implements MouseMotionListener {

    // stores data for mouse movement
    // sensitivity can be changed
    public double rot, pitch, sensitivity = 0.1;
    // to avoid useless checks if mouse hasn't moved
    public boolean mouseMoved;

    public MouseAiming() {
        // sets mouse in center of screen
        rot = 0;
        pitch = 0;
        mouseMoved = false;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // gets location of moused relative to center of screen
        rot = e.getXOnScreen() - (GamePanel.windowWidth / 2);
        // adjusts the rotaion based on sensitivity
        rot = rot * sensitivity;

        // gets location of moused relative to center of screen
        pitch = e.getYOnScreen() - (GamePanel.windowHeight / 2);
        // adjusts the pitch based on sensitivity
        pitch = - pitch * sensitivity;

        // sets mouse moved to true
        mouseMoved = true;
    }

    public void recenterMouse() {
        try {
            // creates a mouse robot to move mouse
            Robot robot = new Robot();
            // sets mouse back to center of screen
            robot.mouseMove(GamePanel.windowWidth / 2, GamePanel.windowHeight / 2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // act as if mouse is moved
        mouseMoved(e);
    }

}