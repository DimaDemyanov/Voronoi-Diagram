package diagram;

import java.util.Objects;

public class Edge {
    public Point p1, p2;
    public boolean p1Done, p2Done;
    public Point main1, main2;
    public Point startP1, startP2;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge edge = (Edge) o;
        return Objects.equals(p1, edge.p1) &&
                Objects.equals(p2, edge.p2);
    }

    public Edge(Point p1, Point p2, boolean p1Done, boolean p2Done, Point main1, Point main2) {
        this.p1 = p1;
        this.p2 = p2;
        this.p1Done = p1Done;
        this.p2Done = p2Done;
        this.main1 = main1;
        this.main2 = main2;
        this.startP1 = new Point(p1.x, p1.y);
        this.startP2 = new Point(p2.x, p2.y);
    }

    public void sortEdge() {
        if (p1.x > p2.x) {
            copy(p2, p1, p2Done, p1Done, main1, main2, startP2, startP1);
        }
    }

    public void sortEdgeByY() {
        if (p1.y > p2.y) {
            copy(p2, p1, p2Done, p1Done, main1, main2, startP2, startP1);
        }
    }

    public void copy(Point p1, Point p2, boolean p1Done, boolean p2Done, Point main1, Point main2, Point startPoint1, Point startPoint2) {
        this.p1 = p1;
        this.p2 = p2;
        this.p1Done = p1Done;
        this.p2Done = p2Done;
        this.main1 = main1;
        this.main2 = main2;
        this.startP1 = startPoint1;
        this.startP2 = startPoint2;
    }
}
