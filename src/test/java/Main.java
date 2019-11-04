import diagram.*;
import draw.Drawer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Main main = new Main();
        ClassLoader classLoader = main.getClass().getClassLoader();
        File file = new File(classLoader.getResource("test9.txt").getFile());
        PointReader reader = new PointReader(file);
        Point [] points = reader.readAllPoints();
        Drawer drawer = new Drawer();
        Map<Point, Cell> cells = Diagram.makeDiagram(points);
        for (int i = 0; i < points.length; i++) {
            for (Edge e: cells.get(points[i]).edges) {
                System.out.println((e.p1Done ? 1 : 0) + " " + e.p1 + " " + e.p2 + " " + (e.p2Done ? 1 : 0));
            }
        }
        drawer.setCells(cells);
        drawer.draw();
    }
}
