package diagram;

import java.util.Objects;

public class Point {

    public static final float EPS = 0.01f;

    public float x;
    public float y;

    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return Float.compare(point.x, x) == 0 &&
                Float.compare(point.y, y) == 0;
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

    public static float calcDist(Point p1, Point p2) {
        return (float)Math.sqrt(Math.pow(p2.x - p1.x, 2) + Math.pow(p2.y - p1.y, 2));
    }
}
