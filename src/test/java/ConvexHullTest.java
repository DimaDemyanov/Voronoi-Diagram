import diagram.Diagram;
import diagram.Point;
import org.junit.Assert;
import org.junit.Test;

public class ConvexHullTest {

    @Test
    public void testFindConvexHull() {
        Point [] points1 = new Point[]{
                new Point(-1, -1),
                new Point(-1, 1),
        };
        Point [] points2 = new Point[]{
                new Point(1, -1),
                new Point(1, 1),
        };

        Diagram.ConvexHull ch = Diagram.findConvexHull(points1, points2);

        Assert.assertEquals(4, ch.points.length);
        Assert.assertEquals(points1[1], ch.upBridge1);
        Assert.assertEquals(points1[0], ch.downBridge1);
        Assert.assertEquals(points2[1], ch.upBridge2);
        Assert.assertEquals(points2[0], ch.downBridge2);
    }

    @Test
    public void testFindConvexHull2() {
        Point [] points1 = new Point[]{
                new Point(-1, 1),
        };
        Point [] points2 = new Point[]{
                new Point(1, 1),
        };

        Diagram.ConvexHull ch = Diagram.findConvexHull(points1, points2);

        Assert.assertEquals(2, ch.points.length);
        Assert.assertEquals(points1[0], ch.upBridge1);
        Assert.assertEquals(points1[0], ch.downBridge1);
        Assert.assertEquals(points2[0], ch.upBridge2);
        Assert.assertEquals(points2[0], ch.downBridge2);
    }

    @Test
    public void testFindConvexHull3() {
        Point [] points1 = new Point[]{
                new Point(-1, 0),
                new Point(-1, -1),
                new Point(-1, 1),
        };
        Point [] points2 = new Point[]{
                new Point(1, -1),
                new Point(1, 1),
        };

        Diagram.ConvexHull ch = Diagram.findConvexHull(points1, points2);

        Assert.assertEquals(5, ch.points.length);
        Assert.assertEquals(points1[2], ch.upBridge1);
        Assert.assertEquals(points1[1], ch.downBridge1);
        Assert.assertEquals(points2[1], ch.upBridge2);
        Assert.assertEquals(points2[0], ch.downBridge2);
    }

    @Test
    public void testFindConvexHullBigWithXIntersections() {
        Point [] points1 = new Point[]{
                new Point(-2, 2),
                new Point(-2, -2),
                new Point(-1, 3),
                new Point(0, -2),
                new Point(1, 3),
                new Point(1, 0),
        };
        Point [] points2 = new Point[]{
                new Point(1, 1),
                new Point(1, -2),
                new Point(2, -3),
                new Point(4, -4),
                new Point(6, 1),
                new Point(6, -4),
                new Point(7, -2),
        };

        Diagram.ConvexHull ch = Diagram.findConvexHull(points1, points2);

        Assert.assertEquals(8, ch.points.length);
        Assert.assertEquals(points1[4], ch.upBridge1);
        Assert.assertEquals(points1[1], ch.downBridge1);
        Assert.assertEquals(points2[4], ch.upBridge2);
        Assert.assertEquals(points2[3], ch.downBridge2);
    }

    @Test
    public void testFindConvexHullBig() {
        Point [] points1 = new Point[]{
                new Point(-2, 2),
                new Point(-2, -2),
                new Point(-1, 3),
                new Point(0, -2),
                new Point(1, 3),
                new Point(1, 0),
        };
        Point [] points2 = new Point[]{
                new Point(2, -2),
                new Point(2, -3),
                new Point(3, 1),
                new Point(4, -4),
                new Point(6, 1),
                new Point(6, -4),
                new Point(7, -2),
        };

        Diagram.ConvexHull ch = Diagram.findConvexHull(points1, points2);

        Assert.assertEquals(8, ch.points.length);
        Assert.assertEquals(points1[4], ch.upBridge1);
        Assert.assertEquals(points1[1], ch.downBridge1);
        Assert.assertEquals(points2[4], ch.upBridge2);
        Assert.assertEquals(points2[3], ch.downBridge2);
    }

    @Test
    public void testFindConvexHull4() {
        Point [] points1 = new Point[]{
                new Point(2, 2),
                new Point(-5, 5),
                new Point(3, 0),
        };
        Point [] points2 = new Point[]{
                new Point(4, 5),
                new Point(6, -1),
        };

        Diagram.ConvexHull ch = Diagram.findConvexHull(points1, points2);

        Assert.assertEquals(4, ch.points.length);
        Assert.assertEquals(points1[1], ch.upBridge1);
        Assert.assertEquals(points1[2], ch.downBridge1);
        Assert.assertEquals(points2[0], ch.upBridge2);
        Assert.assertEquals(points2[1], ch.downBridge2);
    }

}
