package diagram;

import draw.Drawer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        // Main main = new Main();
        // ClassLoader classLoader = main.getClass().getClassLoader();
        File file = null;
        if (args.length == 1 && (file = new File(args[0])).exists()) {
            ;
        } else {
            System.out.println("Cannot open file");
            System.exit(1);
        }
        PointReader reader = new PointReader(file);
        Point [] points = reader.readAllPoints();
        // Drawer drawer = new Drawer();
        Map<Point, Cell> cells = Diagram.makeDiagram(points);
        Set<Edge> edgesDone = new HashSet<>();
        for (int i = 0; i < points.length; i++) {
            LinkedList<Edge> edges = cells.get(points[i]).edges;
            // System.out.println(edges.size());
            for (Edge e: edges) {
                if(!edgesDone.contains(e)) {
                    edgesDone.add(e);
                    System.out.println(e.main1.index + " " + e.main2.index + " " + (e.p1Done ? 1 : 0) + " " + e.p1 + " " + e.p2 + " " + (e.p2Done ? 1 : 0));
                }
            }
        }
        // drawer.setCells(cells);
        // drawer.draw();
    }
}
