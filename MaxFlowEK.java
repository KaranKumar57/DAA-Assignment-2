import java.util.*;

public class MaxFlowEK {
    public static int getMaxFlow(int[][] cap, int src, int sink) {
        int n = cap.length, maxFlow = 0;
        int[][] flow = new int[n][n];
        int[] parent = new int[n];

        while (true) {
            Arrays.fill(parent, -1);
            Queue<Integer> q = new LinkedList<>();
            q.add(src);
            parent[src] = src;

            while (!q.isEmpty() && parent[sink] == -1) {
                int curr = q.poll();
                for (int nxt = 0; nxt < n; nxt++) {
                    if (parent[nxt] == -1 && cap[curr][nxt] - flow[curr][nxt] > 0) {
                        parent[nxt] = curr;
                        q.add(nxt);
                    }
                }
            }

            if (parent[sink] == -1) break;

            int bottleneck = Integer.MAX_VALUE;
            for (int v = sink; v != src; v = parent[v]) 
                bottleneck = Math.min(bottleneck, cap[parent[v]][v] - flow[parent[v]][v]);

            for (int v = sink; v != src; v = parent[v]) {
                flow[parent[v]][v] += bottleneck;
                flow[v][parent[v]] -= bottleneck;
            }
            maxFlow += bottleneck;
        }

        System.out.println("Maximum Flow: " + maxFlow);
        printMinCut(cap, flow, src, n);
        return maxFlow;
    }

    private static void printMinCut(int[][] cap, int[][] flow, int src, int n) {
        boolean[] visited = new boolean[n];
        Queue<Integer> q = new LinkedList<>();
        q.add(src); visited[src] = true;

        while(!q.isEmpty()) {
            int u = q.poll();
            for(int v=0; v<n; v++) {
                if(!visited[v] && cap[u][v] - flow[u][v] > 0) {
                    visited[v] = true;
                    q.add(v);
                }
            }
        }
        System.out.println("Edges in Minimum Cut:");
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                if (visited[i] && !visited[j] && cap[i][j] > 0)
                    System.out.println((char)('A'+i) + " -> " + (char)('A'+j));
    }

    public static void main(String[] args) {
        int[][] cap = new int[7][7];
        cap[0][1] = 3; cap[0][3] = 3; cap[1][2] = 4; cap[2][0] = 3; 
        cap[2][3] = 1; cap[2][4] = 2; cap[3][4] = 2; cap[3][5] = 6; 
        cap[4][1] = 1; cap[4][6] = 1; cap[5][6] = 9;
        getMaxFlow(cap, 0, 6); 
    }
}
