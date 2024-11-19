import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class KeyHandler implements KeyListener, MouseMotionListener{


    public boolean upPressed, downPressed, leftPressed, rightPressed;
    public boolean turnLeftPressed, turnRightPressed, turnUpPressed, turnDownPressed;

    @Override
    public void keyTyped(KeyEvent e) {
        char keyChar = e.getKeyChar();

        // System.out.println(keyChar);
    
        if (keyChar == 'w') {
            upPressed = !upPressed;
            downPressed = false;
        } else if (keyChar == 'a') {
            leftPressed = !leftPressed;
            rightPressed = false;
        } else if (keyChar == 's') {
            downPressed = !downPressed;
            upPressed = false;
        } else if (keyChar == 'd') {
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
        }

        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            System.out.println("ESC");
        }
        // System.out.println(e.getExtendedKeyCode());
    }

    @Override
    public void keyPressed(KeyEvent e) {
        char keyChar = e.getKeyChar();

        // // W
        // if(keyChar == 'w') {
        //     upPressed = true;
        // }

        // // S
        // if(keyChar == 's') {
        //     downPressed = true;
        // }

        // // A
        // if(keyChar == 'a') {
        //     leftPressed = true;
        // }

        // // D
        // if(keyChar == 'd') {
        //     rightPressed = true;
        // }

        // // <
        // if(keyChar == 'j') {
        //     turnLeftPressed = true;
        // }

        // // >
        // if(keyChar == 'l') {
        //     turnRightPressed = true;
        // }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        char keyChar = e.getKeyChar();

        // // W
        // if(keyChar == 'w') {
        //     upPressed = false;
        // }

        // // S
        // if(keyChar == 's') {
        //     downPressed = false;
        // }

        // // A
        // if(keyChar == 'a') {
        //     leftPressed = false;
        // }

        // // D
        // if(keyChar == 'd') {
        //     rightPressed = false;
        // }

        // // <
        // if(keyChar == 'j') {
        //     turnLeftPressed = false;
        // }

        // // >
        // if(keyChar == 'l') {
        //     turnRightPressed = false;
        // }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mouseDragged'");
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mouseMoved'");
    }
}
