package Game.main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{


    public boolean upPressed, downPressed, leftPressed, rightPressed, turnLeftPressed, turnRightPressed;

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyChar();

        // W
        if(code == 119) {
            upPressed = true;
        }

        // S
        if(code == 115) {
            downPressed = true;
        }

        // A
        if(code == 97) {
            leftPressed = true;
        }

        // D
        if(code == 100) {
            rightPressed = true;
        }

        // <
        if(code == 106) {
            turnLeftPressed = true;
        }

        // >
        if(code == 108) {
            turnRightPressed = true;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyChar();

        // W
        if(code == 119) {
            upPressed = false;
        }

        // S
        if(code == 115) {
            downPressed = false;
        }

        // A
        if(code == 97) {
            leftPressed = false;
        }

        // D
        if(code == 100) {
            rightPressed = false;
        }

        // <
        if(code == 106) {
            turnLeftPressed = false;
        }

        // >
        if(code == 108) {
            turnRightPressed = false;
        }
    }


}
