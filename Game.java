import javax.swing.JFrame;
import java.awt.*;

public class Game {

    // NOTE: basic setup, and frame pacing is taken from a 2D game tutorial on youtube

    // main method to run everything
    public static void main(String[] args) {

        // sets the name of the window to Half Life 3 (But way worse)
        JFrame frame = new JFrame("Half Life 3");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        // creates the GamePanel to run game logic and game loop through
        GamePanel gamePanel = new GamePanel();
        // removes top bar
        frame.setUndecorated(true);
        // adds the GamePanel to the frame so you can see whats happening
        frame.add(gamePanel);
        frame.requestFocusInWindow();
        frame.setVisible(true);
        frame.setFocusTraversalKeysEnabled(false);
        frame.pack();
        // creates an invisible cursor
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image cursorImage = toolkit.createImage("");
        Cursor invisibleCursor = toolkit.createCustomCursor(cursorImage, new Point(0, 0), "InvisibleCursor");

        // sets the invisible cursor to overide the system cursor
        frame.setCursor(invisibleCursor);

        // starts the thread which statrs the game loop
        gamePanel.startGameThread();
    }
}