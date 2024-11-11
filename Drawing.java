// package Game.main;
// import java.awt.Graphics;
// import java.awt.Color;

// public class Drawing {

//     Camera c = new Camera(0,0,0,0,5);
//     Vertex v1 = new Vertex(50,50,0);
//     Vertex v2 = new Vertex(100,-50,0);
//     Wall w1 = new Wall(c,v1,v2);
    
//     public void paint(Graphics g) {
//         g.setColor(Color.white);
//         g.drawLine(w1.getP1().getX(), w1.getP1().getY(), w1.getP3().getX(), w1.getP3().getY());
//         g.drawLine(w1.getP1().getX(), w1.getP1().getY(), w1.getP2().getX(), w1.getP2().getY());
//         g.drawLine(w1.getP4().getX(), w1.getP4().getY(), w1.getP2().getX(), w1.getP2().getY());
//         g.drawLine(w1.getP4().getX(), w1.getP4().getY(), w1.getP3().getX(), w1.getP3().getY());
//         g.drawLine(w1.getP4().getX(), w1.getP4().getY(), w1.getP1().getX(), w1.getP1().getY());

//         // g.drawLine(Game.windowWidth / 2, 0, Game.windowWidth / 2, Game.windowHeight);
//         // g.drawLine(0 / 2, Game.windowHeight / 2, Game.windowWidth, Game.windowHeight / 2);
//     }
// }