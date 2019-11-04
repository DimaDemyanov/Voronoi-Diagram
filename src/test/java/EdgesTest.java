import diagram.Diagram;
import diagram.Edge;
import diagram.Point;
import org.junit.Assert;
import org.junit.Test;

import static java.lang.Math.abs;

public class EdgesTest {

    @Test
    public void findMidEdgeTest() {
        Point p1 = new Point(1, 2);
        Point p2 = new Point(-30, -50);
        Edge edge = Diagram.findEdgeBetweenTwoPoints(p1, p2);
        Assert.assertTrue(abs((p2.y - p1.y) * (edge.p2.y - edge.p1.y) + (p2.x - p1.x) * (edge.p2.x - edge.p1.x)) < 0.0001);
        Assert.assertTrue(abs((edge.p2.y - edge.p1.y) * ((p1.x + p2.x) / 2 - edge.p1.x) - (edge.p2.x - edge.p1.x) * ((p1.y + p2.y) / 2 - edge.p1.y)) < 0.0001);
    }

    @Test
    public void findMidEdgeTestVertical() {
        Point p1 = new Point(-4, 0);
        Point p2 = new Point(4, 0);
        Edge edge = Diagram.findEdgeBetweenTwoPoints(p1, p2);
        Assert.assertTrue(abs((p2.y - p1.y) * (edge.p2.y - edge.p1.y) + (p2.x - p1.x) * (edge.p2.x - edge.p1.x)) < 0.0001);
        Assert.assertTrue(abs((edge.p2.y - edge.p1.y) * ((p1.x + p2.x) / 2 - edge.p1.x) - (edge.p2.x - edge.p1.x) * ((p1.y + p2.y) / 2 - edge.p1.y)) < 0.0001);
    }

    @Test
    public void findMidEdgeTestHorizontal() {
        Point p1 = new Point(0, -4);
        Point p2 = new Point(0, 4);
        Edge edge = Diagram.findEdgeBetweenTwoPoints(p1, p2);
        Assert.assertTrue(abs((p2.y - p1.y) * (edge.p2.y - edge.p1.y) + (p2.x - p1.x) * (edge.p2.x - edge.p1.x)) < 0.0001);
        Assert.assertTrue(abs((edge.p2.y - edge.p1.y) * ((p1.x + p2.x) / 2 - edge.p1.x) - (edge.p2.x - edge.p1.x) * ((p1.y + p2.y) / 2 - edge.p1.y)) < 0.0001);
    }

}
