package draw;

import diagram.Cell;
import diagram.CellsWithConvexHull;
import diagram.Point;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class Drawer extends JComponent{

    private static int WIDTH = 1024;
    private static int HEIGHT = 800;
    private static int ONE = 10;

    Map<Point, Cell> cells;

    public void setCells(Map<Point, Cell> cells) {
        this.cells = cells;
    }

    public void draw() {
        JFrame frame = new JFrame();
        frame.setSize(WIDTH, HEIGHT); // Change width and height as needed
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(this);
        frame.setVisible(true);
    }

    public void paintComponent(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;
        cells.forEach((point, cell) -> {
            g2.drawOval((int)(point.x * ONE - 2) + WIDTH / 2, HEIGHT - ((int)(point.y * ONE + 2) + HEIGHT/2), 4, 4);
            cell.edges.forEach(edge -> {
                g2.drawLine((int)(edge.p1.x * ONE) + WIDTH / 2, HEIGHT - ((int)(edge.p1.y * ONE) + HEIGHT / 2),
                        (int)(edge.p2.x * ONE) + WIDTH / 2, HEIGHT - ((int)(edge.p2.y * ONE) + HEIGHT / 2));
            });
        });
    }

}
