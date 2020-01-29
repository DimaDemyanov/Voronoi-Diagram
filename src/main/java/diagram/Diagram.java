package diagram;

import draw.Drawer;

import java.util.*;

import static java.lang.Math.*;

public class Diagram {

    public final static int LENGTH = 1000;

    public static class ConvexHull {
        public Point[] points;
        public Point upBridge1;
        public Point upBridge2;
        public Point downBridge1;
        public Point downBridge2;

        public ConvexHull(Point[] points, Point upBridge1, Point upBridge2, Point downBridge1, Point downBridge2) {
            this.points = points;
            this.upBridge1 = upBridge1;
            this.upBridge2 = upBridge2;
            this.downBridge1 = downBridge1;
            this.downBridge2 = downBridge2;
        }
    }

    public static class IntersectionResult {
        Point p1;
        Point p2;
        Point lastPoint;
        Point lastRemoved;
        double maxY;

        public IntersectionResult(Point p1, Point p2, Point lastPoint, Point lastRemoved, double maxY) {
            this.p1 = p1;
            this.p2 = p2;
            this.lastPoint = lastPoint;
            this.lastRemoved = lastRemoved;
            this.maxY = maxY;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            IntersectionResult that = (IntersectionResult) o;
            return Objects.equals(p1, that.p1) &&
                    Objects.equals(p2, that.p2);
        }

        @Override
        public int hashCode() {
            return Objects.hash(p1, p2);
        }
    }

    static boolean cwe(Point a, Point b, Point c) {
        return a.x * (b.y - c.y) + b.x * (c.y - a.y) + c.x * (a.y - b.y) < 0;
    }

    static boolean cw(Point a, Point b, Point c) {
        return a.x * (b.y - c.y) + b.x * (c.y - a.y) + c.x * (a.y - b.y) <= 0;
    }

    static boolean ccw(Point a, Point b, Point c) {
        return a.x * (b.y - c.y) + b.x * (c.y - a.y) + c.x * (a.y - b.y) > 0;
    }

    public static Point[] merge(LinkedList<Point> l, LinkedList<Point> r) {
        Point[] result = new Point[l.size() + r.size()];
        int i = 0;
        while (!l.isEmpty() || !r.isEmpty()) {
            if (!l.isEmpty() && (r.isEmpty() || l.getFirst().x < r.getFirst().x || l.getFirst().x == r.getFirst().x && l.getFirst().y < r.getFirst().y)) {
                result[i++] = l.removeFirst();
            } else {
                result[i++] = r.removeFirst();
            }
        }
        return result;
    }

    public static ConvexHull findConvexHull(Point[] points1, Point[] points2) {
        Point p1 = points1[0], p2 = points2[points2.length - 1];
        LinkedList<Point> up = new LinkedList<>(), down = new LinkedList<>();
        Point upBridge1 = p1, upBridge2 = p2;
        Point downBridge1 = p1, downBridge2 = p2;
        up.add(p1);
        down.add(p1);
        for (int i = 1; i < points1.length; ++i) {
            if (cw(p1, points1[i], p2)) {
                while (up.size() >= 2 && !cw(up.get(up.size() - 2), up.get(up.size() - 1), points1[i]))
                    up.removeLast();
                upBridge1 = points1[i];
                up.addLast(points1[i]);
            }
            if (ccw(p1, points1[i], p2)) {
                while (down.size() >= 2 && !ccw(down.get(down.size() - 2), down.get(down.size() - 1), points1[i]))
                    down.removeLast();
                downBridge1 = points1[i];
                down.addLast(points1[i]);
            }
        }
        boolean isUpBidge2Set = false, isDownBidge2Set = false;
        for (int i = 0; i < points2.length; ++i) {
            if (i == points2.length - 1 || cw(p1, points2[i], p2)) {
                boolean isNeededUpdBridge2 = false;
                while (up.size() >= 2 && !cw(up.get(up.size() - 2), up.get(up.size() - 1), points2[i])) {
                    Point lastRemoved = up.removeLast();
                    if (lastRemoved == upBridge1) {
                        upBridge1 = up.get(up.size() - 1);
                    }
                    if (lastRemoved == upBridge2) {
                        isNeededUpdBridge2 = true;
                    }
                }
                up.addLast(points2[i]);
                if (!isUpBidge2Set || isNeededUpdBridge2) {
                    upBridge2 = points2[i];
                    isUpBidge2Set = true;
                }
            }
            if (i == points2.length - 1 || ccw(p1, points2[i], p2)) {
                boolean isNeededUpdBridge2 = false;
                while (down.size() >= 2 && !ccw(down.get(down.size() - 2), down.get(down.size() - 1), points2[i])) {
                    Point lastRemoved = down.removeLast();
                    if (lastRemoved == downBridge1) {
                        downBridge1 = down.get(down.size() - 1);
                    }
                    if (lastRemoved == downBridge2) {
                        isNeededUpdBridge2 = true;
                    }
                }
                down.addLast(points2[i]);
                if (!isDownBidge2Set || isNeededUpdBridge2) {
                    downBridge2 = points2[i];
                    isDownBidge2Set = true;
                }
            }
        }
        up.removeLast();
        down.removeFirst();
        return new ConvexHull(merge(up, down), upBridge1, upBridge2, downBridge1, downBridge2);
    }

    public static Edge findEdgeBetweenTwoPoints(Point p1, Point p2) {

        Point m = new Point(((p1.x + p2.x) / 2), ((p1.y + p2.y) / 2));
        double cos = /*LENGTH * */(p2.x - p1.x);
        double sin = /*LENGTH * */(p2.y - p1.y);

        return new Edge(
                new Point(m.x - sin, m.y + cos),
                new Point(m.x + sin, m.y - cos),
                false,
                false,
                p1,
                p2
        );
    }

    public static Map<Point, Cell> makeDiagram(Point[] points) {
        Arrays.sort(points, (point1, point2) -> {
            int cmpX = Double.compare(point1.x, point2.x);
            if (cmpX != 0) {
                return cmpX;
            } else {
                return Double.compare(point1.y, point2.y);
            }
        });
        HashMap<Point, Cell> pointToCell = new HashMap<>();
        for (int i = 0; i < points.length; i++) {
            pointToCell.put(points[i], new Cell());
        }
        return makeDiagramDC(pointToCell, points).cells;
    }

    public static Point findIntersection(Edge l1, Edge l2) {

        double eps = Point.EPS;

        l1.sortEdge();
        l2.sortEdge();

        double a1 = l1.p2.y - l1.p1.y;
        double b1 = l1.p1.x - l1.p2.x;
        double c1 = a1 * l1.p1.x + b1 * l1.p1.y;

        double a2 = l2.p2.y - l2.p1.y;
        double b2 = l2.p1.x - l2.p2.x;
        double c2 = a2 * l2.p1.x + b2 * l2.p1.y;

        double delta = a1 * b2 - a2 * b1;
        if (abs(delta) < eps)
            return null;
        double xa = ((b2 * c1 - b1 * c2) / delta);
        double ya = ((a1 * c2 - a2 * c1) / delta);
        if (l1.p1.x > xa + eps && l1.p1Done ||
                l1.p2.x < xa - eps && l1.p2Done ||
                l2.p1.x > xa + eps && l2.p1Done ||
                l2.p2.x < xa - eps && l2.p2Done
        )
            return null;
        return new Point(xa, ya);
    }

    public static Point cutEdge(Edge edge, Point p, Point farPoint, boolean isUp) {
        Point result = null;

        if (Point.calcDist(edge.startP1, farPoint) < Point.calcDist(edge.startP2, farPoint)) {
            if (!edge.p2Done) {
                edge.p2.x = (p.x + edge.p2.x - edge.p1.x);
                edge.p2.y = (p.y + edge.p2.y - edge.p1.y);
            }
            if (edge.p1Done) {
                result = edge.p1;
            }
            edge.p1 = p;
            edge.p1Done = true;
        } else {
            if (!edge.p1Done) {
                edge.p1.x = (p.x + edge.p1.x - edge.p2.x);
                edge.p1.y = (p.y + edge.p1.y - edge.p2.y);
            }
            if (edge.p2Done) {
                result = edge.p2;
            }
            edge.p2 = p;
            edge.p2Done = true;
        }
        return result;
    }

    public static IntersectionResult intersectOnce(double limitY, Point u, Point w, Map<Point, Cell> pointToCell, Point lastPoint, Point lastRemoved, HashSet<Edge> edgesDone, HashSet<IntersectionResult> pointsResulted) {
        Edge l = findEdgeBetweenTwoPoints(u, w);
        if (lastPoint != null) {
            cutEdge(l, lastPoint, lastRemoved, false);
        }
        Cell cell1 = pointToCell.get(u);
        Cell cell2 = pointToCell.get(w);
        if (limitY == Double.NEGATIVE_INFINITY) {
            cell1.addEdge(l);
            cell2.addEdge(l);
            edgesDone.add(l);
            return null;
        }
        boolean isCell1 = true;
        double maxY = Double.NEGATIVE_INFINITY;
        Edge resultEdge = null;
        Point resultPoint = null;
        for (Edge e : cell1.edges) {
            if (edgesDone.contains(e)) continue;
            Point intersection = findIntersection(e, l);
            if (intersection != null && intersection.y > maxY && intersection.y <= limitY + Point.EPS
                    && !(pointsResulted.contains(new IntersectionResult(e.p1, e.p2, null, null, 0)))
                    && !(pointsResulted.contains(new IntersectionResult(e.p2, e.p1, null, null, 0)))) {
                maxY = intersection.y;
                resultEdge = e;
                resultPoint = intersection;
            }
        }
        for (Edge e : cell2.edges) {
            if (edgesDone.contains(e)) continue;
            Point intersection = findIntersection(e, l);
            if (intersection != null && intersection.y > maxY && intersection.y <= limitY + Point.EPS &&
                    !(pointsResulted.contains(new IntersectionResult(e.p1, e.p2, null, null, 0)))
                    && !(pointsResulted.contains(new IntersectionResult(e.p2, e.p1, null, null, 0)))) {
                maxY = intersection.y;
                resultEdge = e;
                isCell1 = false;
                resultPoint = intersection;
            }
        }
        Point farPoint;
        if (resultEdge == null) {
            cell1.addEdge(l);
            cell2.addEdge(l);
            edgesDone.add(l);
            return null;
        }
        if (resultEdge.main1.equals(u) || resultEdge.main1.equals(w)) {
            farPoint = resultEdge.main2;
        } else {
            farPoint = resultEdge.main1;
        }
        cutEdge(l, resultPoint, farPoint, false);
        cell1.addEdge(l);
        cell2.addEdge(l);
        if (Math.abs(maxY - limitY) > Point.EPS) {
            edgesDone.clear();
        }
        edgesDone.add(l);
        edgesDone.add(resultEdge);

        if (resultEdge.main1.equals(u) || resultEdge.main2.equals(u)) {
            farPoint = w;
        } else {
            farPoint = u;
        }

        if (isCell1) {
            Point pointToDelete = cutEdge(resultEdge, resultPoint, farPoint, false);
            LinkedList<Point> pointsToDelete = new LinkedList<>();
            pointsToDelete.add(pointToDelete);
            while (!pointsToDelete.isEmpty()) {
                pointToDelete = pointsToDelete.removeFirst();
                if (pointToDelete != null) {
                    List<Edge> edgesToRemove = new ArrayList<>();
                    for (Edge e : cell1.edges) {
                        if (!e.equals(resultEdge) && (e.p1 == pointToDelete || e.p2 == pointToDelete)) {
                            if (e.p1.equals(pointToDelete) && e.p2Done) {
                                pointsToDelete.addLast(e.p2);
                            }
                            if (e.p2.equals(pointToDelete) && e.p1Done) {
                                pointsToDelete.addLast(e.p1);
                            }
                            edgesToRemove.add(e);
                        }
                    }
                    for (Edge edge : edgesToRemove) {
                        pointToCell.get(edge.main1).edges.remove(edge);
                        pointToCell.get(edge.main2).edges.remove(edge);
                    }
                }
            }
            if (resultEdge.main1.equals(u))
                return new IntersectionResult(resultEdge.main2, w, resultPoint, u, maxY);
            else
                return new IntersectionResult(resultEdge.main1, w, resultPoint, u, maxY);
        } else {
            Point pointToDelete = cutEdge(resultEdge, resultPoint, farPoint, false);
            LinkedList<Point> pointsToDelete = new LinkedList<>();
            pointsToDelete.add(pointToDelete);
            while (!pointsToDelete.isEmpty()) {
                pointToDelete = pointsToDelete.removeFirst();
                if (pointToDelete != null) {
                    List<Edge> edgesToRemove = new ArrayList<>();
                    for (Edge e : cell2.edges) {
                        if (!e.equals(resultEdge) && (e.p1 == pointToDelete || e.p2 == pointToDelete)) {
                            if (e.p1.equals(pointToDelete) && e.p2Done) {
                                pointsToDelete.addLast(e.p2);
                            }
                            if (e.p2.equals(pointToDelete) && e.p1Done) {
                                pointsToDelete.addLast(e.p1);
                            }
                            edgesToRemove.add(e);
                        }
                    }
                    for (Edge edge : edgesToRemove) {
                        pointToCell.get(edge.main1).edges.remove(edge);
                        pointToCell.get(edge.main2).edges.remove(edge);
                    }
                }
            }
            if (resultEdge.main1.equals(w))
                return new IntersectionResult(u, resultEdge.main2, resultPoint, w, maxY);
            else
                return new IntersectionResult(u, resultEdge.main1, resultPoint, w, maxY);
        }
    }

    public static void findIntersectionDiagram(ConvexHull ch, HashMap<Point, Cell> pointToCell) {
        HashSet<Edge> edgesDone = new HashSet<>();
        HashSet<IntersectionResult> pointsResulted = new HashSet<>();

        if (ch.upBridge1.x == ch.upBridge2.x && ch.upBridge1.x == ch.downBridge1.x && ch.upBridge1.x == ch.downBridge2.x) {
            List<Point> bridges = Arrays.asList(ch.upBridge1, ch.upBridge2, ch.downBridge1, ch.downBridge2);
            bridges.sort(Comparator.comparingDouble(p -> p.y));
            ch.upBridge1 = bridges.get(1);
            ch.upBridge2 = bridges.get(2);
            ch.downBridge1 = bridges.get(0);
            ch.downBridge2 = bridges.get(3);
        }

        IntersectionResult result = new IntersectionResult(ch.upBridge1, ch.upBridge2, null, null, Double.MAX_VALUE);
        pointsResulted.add(result);
//        int i = 0;
        while (result != null && (!result.p1.likeEquals(ch.downBridge1) || !result.p2.likeEquals(ch.downBridge2)) &&
                (!result.p1.likeEquals(ch.downBridge2) || !result.p2.likeEquals(ch.downBridge1))) {
            result = intersectOnce(result.maxY, result.p1, result.p2, pointToCell, result.lastPoint, result.lastRemoved, edgesDone, pointsResulted);
            pointsResulted.add(result);
//            Drawer drawer = new Drawer();
//            drawer.setCells(pointToCell);
//            drawer.draw();
//            if (i++ >= 9) break;
        }
        if (result != null)
            intersectOnce(Double.NEGATIVE_INFINITY, result.p1, result.p2, pointToCell, result.lastPoint, result.lastRemoved, edgesDone, pointsResulted);
    }

    public static CellsWithConvexHull makeDiagramDC(HashMap<Point, Cell> pointToCell, Point[] points) {
        if (points.length == 1)
            return new CellsWithConvexHull(pointToCell, new Point[]{points[0]});

        CellsWithConvexHull cellwc1 = makeDiagramDC(pointToCell, Arrays.copyOfRange(points, 0, (points.length + 1) / 2));
        CellsWithConvexHull cellwc2 = makeDiagramDC(pointToCell, Arrays.copyOfRange(points, (points.length + 1) / 2, points.length));

        ConvexHull ch = findConvexHull(cellwc1.convexHull, cellwc2.convexHull);

        findIntersectionDiagram(ch, pointToCell);

//        Drawer drawer = new Drawer();
//        drawer.setCells(pointToCell);
//        drawer.draw();

        return new CellsWithConvexHull(pointToCell, ch.points);
    }

}
