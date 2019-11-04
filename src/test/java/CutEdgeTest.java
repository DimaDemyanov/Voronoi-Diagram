import diagram.Diagram;
import diagram.Edge;
import diagram.Point;
import org.junit.Assert;
import org.junit.Test;

import static java.lang.Math.abs;

public class CutEdgeTest {
/*
    @Test
    public void cutEdgeTest() {
        Point p1 = new Point(1, 2);
        Point p2 = new Point(-1, -2);
        Point cutPoint = new Point(0, 0);
        Edge edge = new Edge(p1,
                p2,
                true,
                true,
                null,
                null);
        Diagram.cutEdge(edge, cutPoint, true);
        Assert.assertEquals(edge.p1, cutPoint);
        Assert.assertEquals(edge.p2, p1);
    }

    @Test
    public void cutEdgeTest2() {
        Point p2 = new Point(1, 2);
        Point p1 = new Point(-1, -2);
        Point cutPoint = new Point(0, 0);
        Edge edge = new Edge(p1,
                p2,
                true,
                true,
                null,
                null);
        Diagram.cutEdge(edge, cutPoint, true);
        Assert.assertEquals(edge.p1, cutPoint);
        Assert.assertEquals(edge.p2, p2);
    }

    @Test
    public void cutEdgeTest3() {
        Point p2 = new Point(-2, -4);
        Point p1 = new Point(-1, -2);
        Point cutPoint = new Point(0, 0);
        Edge edge = new Edge(p1,
                p2,
                true,
                true,
                null,
                null);
        edge = Diagram.cutEdge(edge, cutPoint, true);
        Assert.assertNull(edge);
    }

    @Test
    public void cutEdgeTest4() {
        Point p2 = new Point(-2, -4);
        Point p1 = new Point(-1, -2);
        Point cutPoint = new Point(0, 0);
        Edge edge = new Edge(p1,
                p2,
                false,
                true,
                null,
                null);
        Diagram.cutEdge(edge, cutPoint, true);
        Assert.assertEquals(edge.p1, cutPoint);
        Edge edge1 = new Edge(new Point(0, 1), new Point(2, 1), true, true, null, null);
        Point p = Diagram.findIntersection(edge, edge1);
        Assert.assertTrue(abs(p.x - 0.5f) < 0.0001);
        Assert.assertTrue(abs(p.y - 1f) < 0.0001);
    }

    @Test
    public void cutEdgeTest5() {
        Point p1 = new Point(1, 2);
        Point p2 = new Point(-1, -2);
        Point cutPoint = new Point(0, 0);
        Edge edge = new Edge(p1,
                p2,
                true,
                true,
                null,
                null);
        Diagram.cutEdge(edge, cutPoint, false);
        Assert.assertEquals(edge.p1, p2);
        Assert.assertEquals(edge.p2, cutPoint);
    }

    @Test
    public void cutEdgeTest6() {
        Point p2 = new Point(1, 2);
        Point p1 = new Point(-1, -2);
        Point cutPoint = new Point(0, 0);
        Edge edge = new Edge(p1,
                p2,
                true,
                true,
                null,
                null);
        Diagram.cutEdge(edge, cutPoint, false);
        Assert.assertEquals(edge.p1, p1);
        Assert.assertEquals(edge.p2, cutPoint);
    }

    @Test
    public void cutEdgeTest7() {
        Point p2 = new Point(2, 4);
        Point p1 = new Point(1, 2);
        Point cutPoint = new Point(0, 0);
        Edge edge = new Edge(p1,
                p2,
                true,
                true,
                null,
                null);
        edge = Diagram.cutEdge(edge, cutPoint, false);
        Assert.assertNull(edge);
    }

    @Test
    public void cutEdgeTest8() {
        Point p2 = new Point(2, 4);
        Point p1 = new Point(1, 2);
        Point cutPoint = new Point(0, 0);
        Edge edge = new Edge(p1,
                p2,
                false,
                true,
                null,
                null);
        Diagram.cutEdge(edge, cutPoint, false);
        Assert.assertEquals(edge.p2, cutPoint);
        Edge edge1 = new Edge(new Point(0, -1), new Point(-2, -1), true, true, null, null);
        Point p = Diagram.findIntersection(edge, edge1);
        Assert.assertTrue(abs(p.x + 0.5f) < 0.0001);
        Assert.assertTrue(abs(p.y + 1f) < 0.0001);
    }*/

}
