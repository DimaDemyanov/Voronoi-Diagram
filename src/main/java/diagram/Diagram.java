package diagram;

import java.util.*;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class Diagram {

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
        float maxY;

        public IntersectionResult(Point p1, Point p2, Point lastPoint, Point lastRemoved, float maxY) {
            this.p1 = p1;
            this.p2 = p2;
            this.lastPoint = lastPoint;
            this.lastRemoved = lastRemoved;
            this.maxY = maxY;
        }
    }

    static boolean cw(Point a, Point b, Point c) {
        return a.x * (b.y - c.y) + b.x * (c.y - a.y) + c.x * (a.y - b.y) <= 0;
    }

    static boolean ccw(Point a, Point b, Point c) {
        return a.x * (b.y - c.y) + b.x * (c.y - a.y) + c.x * (a.y - b.y) >= 0;
    }

    public static Point[] merge(LinkedList<Point> l, LinkedList<Point> r) {
        Point[] result = new Point[l.size() + r.size()];
        int i = 0;
        while (!l.isEmpty() || !r.isEmpty()) {
            if (!l.isEmpty() && (r.isEmpty() || l.getFirst().x < r.getFirst().x)) {
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

    /*public static Edge findEdgeBetweenTwoPoints(Point p1, Point p2) {
        Function<Float, Float> func = x -> (p2.x - p1.x) / (p2.y - p1.y) * (x - (p1.x + p2.x) / 2) + (p1.y + p2.y) / 2;

        return new Edge(
                new Point((p1.x + p2.x) / 2 + 1, func.apply((p1.x + p2.x) / 2 + 1)),
                new Point((p1.x + p2.x) / 2 - 1, func.apply((p1.y + p2.y) / 2 - 1)),
                false,
                false,
                p1,
                p2
        );
    }*/

    public static Edge findEdgeBetweenTwoPoints(Point p1, Point p2) {

        Point m = new Point((p1.x + p2.x) / 2, (p1.y + p2.y) / 2);
        float l = (float) sqrt(pow(p2.x - p1.x, 2) + pow(p2.y - p1.y, 2));
        float cos = (p2.x - p1.x) / l;
        float sin = (p2.y - p1.y) / l;

        return new Edge(
                new Point(m.x - sin / 2, m.y + cos / 2),
                new Point(m.x + sin / 2, m.y - cos / 2),
                false,
                false,
                p1,
                p2
        );
    }

    public static Map<Point, Cell> makeDiagram(Point[] points) {
        Arrays.sort(points, (point1, point2) -> Float.compare(point1.x, point2.x));
        HashMap<Point, Cell> pointToCell = new HashMap<>();
        for (int i = 0; i < points.length; i++) {
            pointToCell.put(points[i], new Cell());
        }
        return makeDiagramDC(pointToCell, points).cells;
    }

   /* public static Point findIntersection(Edge edge1, Edge edge2) {
        Edge e1 = edge1.sortEdge();
        Edge e2 = edge2.sortEdge();
        if (e1.p2.x < e2.p1.x && e1.p2Done || e2.p2.x < e1.p1.x && e2.p2Done) {
            return null;
        }

        boolean isA1Ok = true, isA2Ok = true;
        float a1 = 0, a2 = 0, b1 = 0, b2 = 0, xa = 0, ya = 0;
        if(edge1.p1.x - edge1.p2.x != 0)
            a1 = (edge1.p1.y - edge1.p2.y) / (edge1.p1.x - edge1.p2.x);
        else
            isA1Ok = false;

        if(edge1.p1.x - edge1.p2.x != 0)
            a2 = (edge2.p1.y - edge2.p2.y) / (edge2.p1.x - edge2.p2.x);
        else
            isA2Ok = false;

        if(isA1Ok)
            b1 = edge1.p1.y - a1 * edge1.p1.x;

        if (isA2Ok)
            b2 = edge2.p1.y - a2 * edge2.p1.x;
        if(isA1Ok && isA2Ok)
            xa = (b2 - b1) / (a1 - a2);
        else
            if (!isA1Ok)
                xa = edge1.p1.x;
            else
                xa = edge2.p1.x;
        if(isA1Ok)
            ya = a1*xa + b1;
        else
            ya = a2*xa + b2;
        return new Point(xa, ya);
    }*/

    public static Point findIntersection(Edge l1, Edge l2) {

        l1.sortEdge();
        l2.sortEdge();

        float a1 = l1.p2.y - l1.p1.y;
        float b1 = l1.p1.x - l1.p2.x;
        float c1 = a1 * l1.p1.x + b1 * l1.p1.y;

        float a2 = l2.p2.y - l2.p1.y;
        float b2 = l2.p1.x - l2.p2.x;
        float c2 = a2 * l2.p1.x + b2 * l2.p1.y;

        float delta = a1 * b2 - a2 * b1;
        if (delta == 0) return null;
        float xa = (b2 * c1 - b1 * c2) / delta;
        float ya = (a1 * c2 - a2 * c1) / delta;
        if (l1.p1.x > xa && l1.p1Done ||
                l1.p2.x < xa && l1.p2Done ||
                l2.p1.x > xa && l2.p1Done ||
                l2.p2.x < xa && l2.p2Done
        )
            return null;
        return new Point(xa, ya);
    }

    /*public static Edge cutEdge(Edge edge, Point p, Point farPoint, boolean isUp) {
     */
    /*edge.sortEdgeByY();
        if (isUp) {
            if (edge.p2.y >= p.y) {
                edge.p1 = p;
                edge.p1Done = true;
                return edge;
            }
        } else {
            if (edge.p1.y <= p.y) {
                edge.p2 = p;
                edge.p2Done = true;
                return edge;
            }
        }
        if (isUp) {
            if (!edge.p2Done) {
                edge.copy(
                        p,
                        new Point(p.x + (edge.p2.x - edge.p1.x), p.y + (edge.p2.y - edge.p1.y)),
                        true,
                        false,
                        edge.main1,
                        edge.main1
                );
                return edge;
            }
            return null;
        } else {
            if (!edge.p1Done) {
                edge.copy(
                        new Point(p.x - (edge.p2.x - edge.p1.x), p.y - (edge.p2.y - edge.p1.y)),
                        p,
                        false,
                        true,
                        edge.main1,
                        edge.main1
                );
                return edge;
            }
            return null;
        }*//*
        if (!edge.p1Done) {
            if (!edge.p2Done) {
                if(Point.calcDist(edge.p1, farPoint) < Point.calcDist(edge.p2, farPoint)) {
                    edge.p2.x = p.x + edge.p2.x - edge.p1.x;
                    edge.p2.y = p.y + edge.p2.y - edge.p1.y;
                    edge.p1 = p;
                    edge.p1Done = true;
                } else {
                    edge.p1.x = p.x + edge.p1.x - edge.p2.x;
                    edge.p1.y = p.y + edge.p1.y - edge.p2.y;
                    edge.p2 = p;
                    edge.p2Done = true;
                }
                return edge;
            }
            edge.p1 = p;
            edge.p1Done = true;
        } else {
            edge.p2 = p;
            edge.p2Done = true;
        }
        return edge;
    }*/

    public static Point cutEdge(Edge edge, Point p, Point farPoint, boolean isUp) {
        Point result = null;
        if(Point.calcDist(edge.startP1, farPoint) < Point.calcDist(edge.startP2, farPoint)) {
            if (!edge.p2Done) {
                edge.p2.x = p.x + edge.p2.x - edge.p1.x;
                edge.p2.y = p.y + edge.p2.y - edge.p1.y;
            }
            if (edge.p1Done /*&& !edge.p1.equals(p)*/) {
                result = edge.p1;
            }
            edge.p1 = p;
            edge.p1Done = true;
        } else {
            if (!edge.p1Done  /*&& !edge.p2.equals(p)*/) {
                edge.p1.x = p.x + edge.p1.x - edge.p2.x;
                edge.p1.y = p.y + edge.p1.y - edge.p2.y;
            }
            if (edge.p2Done) {
                result = edge.p2;
            }
            edge.p2 = p;
            edge.p2Done = true;
        }
        return result;
    }

    public static IntersectionResult intersectOnce(float limitY, Point u, Point w, Map<Point, Cell> pointToCell, Point lastPoint, Point lastRemoved, HashSet<Edge> edgesDone) {
        Edge l = findEdgeBetweenTwoPoints(u, w);
        if (lastPoint != null) {
            cutEdge(l, lastPoint, lastRemoved, false);
        }
        Cell cell1 = pointToCell.get(u);
        Cell cell2 = pointToCell.get(w);
        if (limitY == Float.NEGATIVE_INFINITY) {
            cell1.addEdge(l);
            cell2.addEdge(l);
            edgesDone.add(l);
            return null;
        }
        boolean isCell1 = true;
        float maxY = Float.NEGATIVE_INFINITY;
        Edge resultEdge = null;
        Point resultPoint = null;
        for (Edge e : cell1.edges) {
            if (edgesDone.contains(e)) continue;
            Point intersection = findIntersection(e, l);
            if (intersection != null && intersection.y > maxY && intersection.y <= limitY) {
                maxY = intersection.y;
                resultEdge = e;
                resultPoint = intersection;
            }
        }
        for (Edge e : cell2.edges) {
            if (edgesDone.contains(e)) continue;
            Point intersection = findIntersection(e, l);
            if (intersection != null && intersection.y > maxY && intersection.y <= limitY) {
                maxY = intersection.y;
                resultEdge = e;
                isCell1 = false;
                resultPoint = intersection;
            }
        }
        Point farPoint;
        if (resultEdge == null){
            return null;
        }
        if (resultEdge.main1.equals(u) || resultEdge.main1.equals(w) ) {
            farPoint = resultEdge.main2;
        } else {
            farPoint = resultEdge.main1;
        }
        cutEdge(l, resultPoint, farPoint, true);
        cell1.addEdge(l);
        cell2.addEdge(l);
        edgesDone.clear();
        edgesDone.add(l);
        edgesDone.add(resultEdge);

        if (resultEdge.main1.equals(u) || resultEdge.main2.equals(u) ) {
            farPoint = w;
        } else {
            farPoint = u;
        }

        if (isCell1) {
//            cell1.deleteEdge(resultEdge);
            Point pointToDelete = cutEdge(resultEdge, resultPoint, farPoint, true);
//            cell1.addEdge(resultEdge);
            if (pointToDelete != null) {
                List<Edge> edgesToRemove = new ArrayList<>();
                for (Edge e : cell1.edges) {
                    if (!e.equals(resultEdge) && (e.p1.equals(pointToDelete) || e.p2.equals(pointToDelete)))
                        edgesToRemove.add(e);
                }
                for (Edge edge: edgesToRemove) {
                    pointToCell.get(edge.main1).edges.remove(edge);
                    pointToCell.get(edge.main2).edges.remove(edge);
                }
            }
            if (resultEdge.main1.equals(u))
                return new IntersectionResult(resultEdge.main2, w, resultPoint, u, maxY);
            else
                return new IntersectionResult(resultEdge.main1, w, resultPoint, u,  maxY);
        } else {
//            cell2.deleteEdge(resultEdge);
            Point pointToDelete = cutEdge(resultEdge, resultPoint, farPoint, true);
//            cell2.addEdge(resultEdge);
            if (pointToDelete != null) {
                List<Edge> edgesToRemove = new ArrayList<>();
                for (Edge e : cell2.edges) {
                    if (!e.equals(resultEdge) && (e.p1.equals(pointToDelete) || e.p2.equals(pointToDelete)))
                        edgesToRemove.add(e);
                }
                for (Edge edge: edgesToRemove) {
                    pointToCell.get(edge.main1).edges.remove(edge);
                    pointToCell.get(edge.main2).edges.remove(edge);
                }
            }
            if (resultEdge.main1.equals(w))
                return new IntersectionResult(u, resultEdge.main2, resultPoint, w,  maxY);
            else
                return new IntersectionResult(u, resultEdge.main1, resultPoint, w, maxY);
        }
    }

    public static void findIntersectionDiagram(ConvexHull ch, HashMap<Point, Cell> pointToCell) {
        HashSet<Edge> edgesDone = new HashSet<>();
        IntersectionResult result = new IntersectionResult(ch.upBridge1, ch.upBridge2, null, null, Float.MAX_VALUE);
        while (result != null && (!result.p1.likeEquals(ch.downBridge1) || !result.p2.likeEquals(ch.downBridge2)) &&
                (!result.p1.likeEquals(ch.downBridge2) || !result.p2.likeEquals(ch.downBridge1))) {
            result = intersectOnce(result.maxY, result.p1, result.p2 , pointToCell, result.lastPoint, result.lastRemoved, edgesDone);
        }
        if (result != null)
            intersectOnce(Float.NEGATIVE_INFINITY, result.p1, result.p2 , pointToCell, result.lastPoint, result.lastRemoved, edgesDone);
    }

    public static CellsWithConvexHull makeDiagramDC(HashMap<Point, Cell> pointToCell, Point[] points) {
        if (points.length == 1)
            return new CellsWithConvexHull(pointToCell, new Point[]{points[0]});

        if (points.length == 2) {
            Edge edge = findEdgeBetweenTwoPoints(points[0], points[1]);
            pointToCell.get(points[0]).addEdge(edge);
            pointToCell.get(points[1]).addEdge(edge);
            return new CellsWithConvexHull(pointToCell, new Point[]{points[0], points[1]});
        }

        CellsWithConvexHull cellwc1 = makeDiagramDC(pointToCell, Arrays.copyOfRange(points, 0, (points.length + 1)/ 2));
        CellsWithConvexHull cellwc2 = makeDiagramDC(pointToCell, Arrays.copyOfRange(points, (points.length + 1) / 2, points.length));

        ConvexHull ch = findConvexHull(cellwc1.convexHull, cellwc2.convexHull);

        findIntersectionDiagram(ch, pointToCell);

        return new CellsWithConvexHull(pointToCell, ch.points);
    }

}
