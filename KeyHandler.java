import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{


    public boolean upPressed, downPressed, leftPressed, rightPressed, turnLeftPressed, turnRightPressed;

    @Override
    public void keyTyped(KeyEvent e) {
        // char keyChar = e.getKeyChar();

        // System.out.println(keyChar);
    
        // if (keyChar == 'w') {

        // } else if (keyChar == 'a') {

        // } else if (keyChar == 's') {
 
        // } else if (keyChar == 'd') {
   
        // } else if (keyChar == 'j') {
  
        // } else if (keyChar == 'l') {

        // }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        char keyChar = e.getKeyChar();

        // W
        if(keyChar == 'w') {
            upPressed = true;
        }

        // S
        if(keyChar == 's') {
            downPressed = true;
        }

        // A
        if(keyChar == 'a') {
            leftPressed = true;
        }

        // D
        if(keyChar == 'd') {
            rightPressed = true;
        }

        // <
        if(keyChar == 'j') {
            turnLeftPressed = true;
        }

        // >
        if(keyChar == 'l') {
            turnRightPressed = true;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        char keyChar = e.getKeyChar();

        // W
        if(keyChar == 'w') {
            upPressed = false;
        }

        // S
        if(keyChar == 's') {
            downPressed = false;
        }

        // A
        if(keyChar == 'a') {
            leftPressed = false;
        }

        // D
        if(keyChar == 'd') {
            rightPressed = false;
        }

        // <
        if(keyChar == 'j') {
            turnLeftPressed = false;
        }

        // >
        if(keyChar == 'l') {
            turnRightPressed = false;
        }
    }
}
