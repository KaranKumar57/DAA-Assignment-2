import java.util.*;

public class Dijkstra {
    static final int INF = Integer.MAX_VALUE;
    static final int NIL = -1;
    
    static final int WHITE = 0;
    static final int BLACK = 1;

    public static void dijkstra(int[][][] graph, int V, int s) {
        int[] d = new int[V];
        int[] color = new int[V];
        int[] pred = new int[V];

        for (int u = 0; u < V; u++) {
            d[u] = INF;
            color[u] = WHITE;
        }
        d[s] = 0;
        pred[s] = NIL;

        List<Integer> Q = new ArrayList<>();
        for (int i = 0; i < V; i++) {
            Q.add(i);
        }

        while (!Q.isEmpty()) {
            int u = extractMin(Q, d);
            
            for (int[] edge : graph[u]) {
                int v = edge[0];
                int w_uv = edge[1];

                if (d[u] != INF && d[u] + w_uv < d[v]) {
                    // relax
                    d[v] = d[u] + w_uv;
                    decreaseKey(Q, v, d[v]);
                    pred[v] = u;
                }
            }
            color[u] = BLACK;
        }

        System.out.println("Vertex\tDistance\tPredecessor");
        for (int i = 0; i < V; i++) {
            System.out.println(i + "\t" + d[i] + "\t\t" + (pred[i] == NIL ? "NIL" : pred[i]));
        }

        System.out.println("\nShortest Path Tree (SPT) Edges:");
        for (int i = 0; i < V; i++) {
            if (pred[i] != NIL) {
                System.out.println("(" + pred[i] + ", " + i + ")");
            }
        }
    }

    private static int extractMin(List<Integer> Q, int[] d) {
        int minIdx = 0;
        for (int i = 1; i < Q.size(); i++) {
            if (d[Q.get(i)] < d[Q.get(minIdx)]) {
                minIdx = i;
            }
        }
        return Q.remove(minIdx);
    }

    private static void decreaseKey(List<Integer> Q, int v, int newVal) {
    }

    public static void main(String[] args) {
        int V = 9;
        int[][][] graph = new int[V][][];

        graph[0] = new int[][]{{1, 4}, {7, 8}};
        graph[1] = new int[][]{{0, 4}, {2, 8}, {7, 11}};
        graph[2] = new int[][]{{1, 8}, {3, 7}, {5, 4}, {8, 2}};
        graph[3] = new int[][]{{2, 7}, {4, 9}, {5, 14}};
        graph[4] = new int[][]{{3, 9}, {5, 10}};
        graph[5] = new int[][]{{2, 4}, {3, 14}, {4, 10}, {6, 2}};
        graph[6] = new int[][]{{5, 2}, {7, 1}, {8, 6}};
        graph[7] = new int[][]{{0, 8}, {1, 11}, {6, 1}, {8, 7}};
        graph[8] = new int[][]{{2, 2}, {6, 6}, {7, 7}};

        dijkstra(graph, V, 0);
    }
}
