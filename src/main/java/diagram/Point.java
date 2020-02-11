package diagram;

import java.util.Objects;

public class Point {

    public static final double EPS = 0.000000001f;

    public double x;
    public double y;
    public int index;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
        this.index = -1;
    }

    public Point(double x, double y, int index) {
        this.x = x;
        this.y = y;
        this.index = index;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return Double.compare(point.x, x) == 0 &&
                Double.compare(point.y, y) == 0;
    }

    public boolean likeEquals(Point p) {
        if (Math.abs(x - p.x) < EPS && Math.abs(y - p.y) < EPS)
            return true;
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return x + " " + y;
    }

    public static double calcDist(Point p1, Point p2) {
        return (double)Math.sqrt(Math.pow(p2.x - p1.x, 2) + Math.pow(p2.y - p1.y, 2));
    }
}
