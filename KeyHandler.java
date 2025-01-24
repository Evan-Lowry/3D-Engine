import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class KeyHandler implements KeyListener{

    // stores all input
    // stored as boolean so you can hold down keys
    public boolean upPressed, downPressed, leftPressed, rightPressed;
    public boolean turnLeftPressed, turnRightPressed, turnUpPressed, turnDownPressed;
    public boolean spacePressed, shiftPressed, fullscreen;

    public KeyHandler() {
        upPressed = false;
        downPressed = false;
        leftPressed = false;
        rightPressed = false;
        turnLeftPressed = false;
        turnRightPressed = false;
        turnUpPressed = false;
        turnDownPressed = false;
        spacePressed = false;
        fullscreen = false;
        shiftPressed = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {

        // used for the Microsoft virtual desktop which only detects key typed

        char keyChar = Character.toLowerCase(e.getKeyChar());
        if (keyChar == 't') {
            upPressed = !upPressed;
            downPressed = false;
        } else if (keyChar == 'f') {
            leftPressed = !leftPressed;
            rightPressed = false;
        } else if (keyChar == 'g') {
            downPressed = !downPressed;
            upPressed = false;
        } else if (keyChar == 'h') {
            rightPressed = !rightPressed;
            leftPressed = false;
        } else if (keyChar == 'j') {
            turnLeftPressed = !turnLeftPressed;
            turnRightPressed = false;
        } else if (keyChar == 'l') {
            turnRightPressed = !turnRightPressed;
            turnLeftPressed = false;
        } else if (keyChar == 'i') {
            turnUpPressed = !turnUpPressed;
            turnDownPressed = false;
        } else if (keyChar == 'k') {
            turnDownPressed = !turnDownPressed;
            turnUpPressed = false;
        } else if (keyChar == ' ') {
            spacePressed = true;
        } else if (keyChar == 'q') {
            GamePanel.exit();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // captures key as char
        // to lower case to avoid changes when sprinting
        char keyChar = Character.toLowerCase(e.getKeyChar());

        // when pressed set the key pressed to true
        if (keyChar == 'w') {
            upPressed = true;
        } else if (keyChar == 'a') {
            leftPressed = true;
        } else if (keyChar == 's') {
            downPressed = true;
        } else if (keyChar == 'd') {
            rightPressed = true;
        } else if (e.isShiftDown()) {
            shiftPressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // captures key as char
        // to lower case to avoid changes when sprinting
        char keyChar = Character.toLowerCase(e.getKeyChar());

        // when released set the key pressed to false
        if (keyChar == 'w') {
            upPressed = false;
        } else if (keyChar == 'a') {
            leftPressed = false;
        } else if (keyChar == 's') {
            downPressed = false;
        } else if (keyChar == 'd') {
            rightPressed = false;
        } else if (!e.isShiftDown()) {
            shiftPressed = false;
        }
    }
}
