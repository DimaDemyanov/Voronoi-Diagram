package diagram;

import java.util.Map;

public class CellsWithConvexHull {
    public Map<Point, Cell> cells;
    public Point[] convexHull;

    public CellsWithConvexHull(Map<Point, Cell> cells, Point[] convexHull) {
        this.cells = cells;
        this.convexHull = convexHull;
    }
}
