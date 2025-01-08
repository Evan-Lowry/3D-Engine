import javax.swing.JFrame;
import java.awt.*;

public class Game {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Half Life 3");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        GamePanel gamePanel = new GamePanel();
        frame.setUndecorated(true);
        frame.add(gamePanel);
        frame.requestFocusInWindow();
        // frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setFocusTraversalKeysEnabled(false);
        frame.pack();
        gamePanel.startGameThread();

        // Create an invisible cursor
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image cursorImage = toolkit.createImage("");
        Cursor invisibleCursor = toolkit.createCustomCursor(cursorImage, new Point(0, 0), "InvisibleCursor");

        // Set the invisible cursor
        frame.setCursor(invisibleCursor);
    }
}