package Game.main;
import javax.swing.JFrame;
// import java.awt.Graphics;

public class Game {

    public void main(String[] args) {

        JFrame frame = new JFrame("GTA VI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        GamePanel gamePanel = new GamePanel();
        frame.add(gamePanel);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        frame.pack();


        gamePanel.startGameThread();
        
        //frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        //frame.setUndecorated(true);
    }
}