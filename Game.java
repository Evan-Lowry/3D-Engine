import javax.swing.JFrame;
// import java.awt.Graphics;

public class Game {

    public static void main(String[] args) {

        JFrame frame = new JFrame("Half Life 3");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        GamePanel gamePanel = new GamePanel();
        frame.add(gamePanel);

        frame.requestFocusInWindow();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setFocusTraversalKeysEnabled(false);
        frame.pack();

        gamePanel.startGameThread();
        
        //frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        //frame.setUndecorated(true);
    }
}