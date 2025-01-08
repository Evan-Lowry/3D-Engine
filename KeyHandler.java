import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class KeyHandler implements KeyListener{


    public boolean upPressed, downPressed, leftPressed, rightPressed;
    public boolean turnLeftPressed, turnRightPressed, turnUpPressed, turnDownPressed;
    public boolean spacePressed, fullscreen;

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
    }

    @Override
    public void keyTyped(KeyEvent e) {
        char keyChar = e.getKeyChar();

        // System.out.println(keyChar);
    
        // if (keyChar == 'w') {
        //     upPressed = !upPressed;
        //     downPressed = false;
        // } else if (keyChar == 'a') {
        //     leftPressed = !leftPressed;
        //     rightPressed = false;
        // } else if (keyChar == 's') {
        //     downPressed = !downPressed;
        //     upPressed = false;
        // } else if (keyChar == 'd') {
        //     rightPressed = !rightPressed;
        //     leftPressed = false;
        // } else if (keyChar == 'j') {
        //     turnLeftPressed = !turnLeftPressed;
        //     turnRightPressed = false;
        // } else if (keyChar == 'l') {
        //     turnRightPressed = !turnRightPressed;
        //     turnLeftPressed = false;
        // } else if (keyChar == 'i') {
        //     turnUpPressed = !turnUpPressed;
        //     turnDownPressed = false;
        // } else if (keyChar == 'k') {
        //     turnDownPressed = !turnDownPressed;
        //     turnUpPressed = false;
        // } else if (keyChar == ' ') {
        //     spacePressed = true;
        // } else if (keyChar == 'q') {
        //     System.exit(0);
        // } else if (keyChar == 'f') {
        //     fullscreen = !fullscreen;
        // }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        char keyChar = e.getKeyChar();

        if (keyChar == 'w') {
            upPressed = true;
        } else if (keyChar == 'a') {
            leftPressed = true;
        } else if (keyChar == 's') {
            downPressed = true;
        } else if (keyChar == 'd') {
            rightPressed = true;
        } else if (keyChar == 'j') {
            turnLeftPressed = true;
        } else if (keyChar == 'l') {
            turnRightPressed = true;
        } else if (keyChar == 'i') {
            turnUpPressed = true;
        } else if (keyChar == 'k') {
            turnDownPressed = true;
        } else if (keyChar == ' ') {
            spacePressed = true;
        } else if (keyChar == 'q') {
            System.exit(0);
        } else if (keyChar == 'f') {
            fullscreen = !fullscreen;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        char keyChar = e.getKeyChar();

        if (keyChar == 'w') {
            upPressed = false;
        } else if (keyChar == 'a') {
            leftPressed = false;
        } else if (keyChar == 's') {
            downPressed = false;
        } else if (keyChar == 'd') {
            rightPressed = false;
        } else if (keyChar == 'j') {
            turnLeftPressed = false;
        } else if (keyChar == 'l') {
            turnRightPressed = false;
        } else if (keyChar == 'i') {
            turnUpPressed = false;
        } else if (keyChar == 'k') {
            turnDownPressed = false;
        } else if (keyChar == ' ') {
            spacePressed = false;
        } else if (keyChar == 'q') {
            System.exit(0);
        } else if (keyChar == 'f') {
            fullscreen = !fullscreen;
        }
    }
}
