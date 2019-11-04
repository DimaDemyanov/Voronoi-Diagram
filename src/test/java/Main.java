import diagram.*;
import draw.Drawer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("/Users/dm/IdeaProjects/voronoy/src/test/resources/test4.txt");
        PointReader reader = new PointReader(file);
        Point [] points = reader.readAllPoints();
        Drawer drawer = new Drawer();
        Map<Point, Cell> cells = Diagram.makeDiagram(points);
        drawer.setCells(cells);
        drawer.draw();
    }
}
