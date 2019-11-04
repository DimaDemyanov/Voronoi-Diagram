package diagram;

import java.util.LinkedList;

public class Cell {
    public LinkedList<Edge> edges;

    public Cell() {
        edges = new LinkedList<Edge>();
    }

    public void addEdge(Edge p) {
        edges.add(p);
    }

    public void deleteEdge(Edge p) {
        edges.remove(p);
    }

}
