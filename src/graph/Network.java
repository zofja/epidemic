package graph;

import java.util.List;
import java.util.Random;
import java.util.Vector;

public class Network {

    private List<Edge> edges = new Vector();
    private int n;
    private long seed;

    Random r = new Random(seed);

    public Network(int n, long seed) {
        this.n = n;
        this.seed = seed;
    }

    public void genEdges() {
        for (int i = 1; i <= n; i++) {
            for (int j = i + 1; j <= n; j++) {
                if (i != j) {

                    edges.add(new Edge(i, j));
                }
            }
        }
    }

    public Edge getRandomEdge() {
        if (edges.size() > 0) {
            return edges.remove(r.nextInt(edges.size()));
        } else return edges.remove(0);
    }
}



