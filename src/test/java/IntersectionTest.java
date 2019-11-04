import diagram.Diagram;
import diagram.Edge;
import diagram.Point;
import org.junit.Assert;
import org.junit.Test;

import static java.lang.Math.abs;

public class IntersectionTest {

    @Test
    public void findIntersectionTest() {
        Edge edge1 = new Edge(new Point(-1, -1), new Point(1, 1),true, true, null, null);
        Edge edge2 = new Edge(new Point(1, -1), new Point(-1, 1),true, true, null, null);
        Point p = Diagram.findIntersection(edge1, edge2);
        Assert.assertTrue(abs(p.x) < 0.00001);
        Assert.assertTrue(abs(p.y) < 0.00001);
    }

    @Test
    public void findIntersectionTest2() {
        Edge edge1 = new Edge(new Point(2, 6), new Point(-1, -3),true, true, null, null);
        Edge edge2 = new Edge(new Point(-2, 7), new Point(4, -1),true, true, null, null);
        Point p = Diagram.findIntersection(edge1, edge2);
        Assert.assertTrue(abs(p.x - 1) < 0.00001);
        Assert.assertTrue(abs(p.y - 3) < 0.00001);
    }

    @Test
    public void findIntersectionTestNull1() {
        Edge edge1 = new Edge(new Point(0, 0), new Point(-1, -3),true, true, null, null);
        Edge edge2 = new Edge(new Point(-2, 7), new Point(4, -1),true, true, null, null);
        Point p = Diagram.findIntersection(edge1, edge2);
        Assert.assertNull(p);
        edge1.p1Done = false;
        p = Diagram.findIntersection(edge1, edge2);
        Assert.assertTrue(abs(p.x - 1) < 0.00001);
        Assert.assertTrue(abs(p.y - 3) < 0.00001);
    }

    @Test
    public void findIntersectionTestNull2() {
        Edge edge1 = new Edge(new Point(3, 9), new Point(2, 6),true, true, null, null);
        Edge edge2 = new Edge(new Point(-2, 7), new Point(4, -1),true, true, null, null);
        Point p = Diagram.findIntersection(edge1, edge2);
        Assert.assertNull(p);
        edge1.p2Done = false;
        p = Diagram.findIntersection(edge1, edge2);
        Assert.assertTrue(abs(p.x - 1) < 0.00001);
        Assert.assertTrue(abs(p.y - 3) < 0.00001);
    }

    @Test
    public void findIntersectionTestNull3() {
        Edge edge1 = new Edge(new Point(2, 6), new Point(-1, -3),true, true, null, null);
        Edge edge2 = new Edge(new Point(-2, 7), new Point(-5, 11),true, true, null, null);
        Point p = Diagram.findIntersection(edge1, edge2);
        Assert.assertNull(p);
        edge2.p1Done = false;
        p = Diagram.findIntersection(edge1, edge2);
        Assert.assertTrue(abs(p.x - 1) < 0.00001);
        Assert.assertTrue(abs(p.y - 3) < 0.00001);
    }

    @Test
    public void findIntersectionTestNull4() {
        Edge edge1 = new Edge(new Point(2, 6), new Point(-1, -3),true, true, null, null);
        Edge edge2 = new Edge(new Point(7, -5), new Point(4, -1),true, true, null, null);
        Point p = Diagram.findIntersection(edge1, edge2);
        Assert.assertNull(p);
        edge2.p2Done = false;
        p = Diagram.findIntersection(edge1, edge2);
        Assert.assertTrue(abs(p.x - 1) < 0.00001);
        Assert.assertTrue(abs(p.y - 3) < 0.00001);
    }
}
