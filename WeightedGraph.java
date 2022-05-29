import java.util.ArrayList;
import java.util.PriorityQueue;

public class WeightedGraph {
    public ArrayList<ArrayList<AdjListNode>> graph = new ArrayList<>();
    public double[][] _shortestPath = null;
    public int V;

    public WeightedGraph(int v){
        V = v;

        for(int i = 0; i < v; i++){
            graph.add(new ArrayList<>());
        }
    }

    static class AdjListNode {
        double distance;
        int vertex;
  
        AdjListNode(int v, double d)
        {
            vertex = v;
            distance = d;
        }

        int getVertex() { return vertex; }

        double getWeight() { return distance; }

    }

    public void addVertex(ArrayList<AdjListNode> user){
        graph.add(user);
        V++;
    }

    public void addEdge(int src, int dst, double d){
        graph.get(src).add(new AdjListNode(dst, d));  
        graph.get(dst).add(new AdjListNode(src, d));  
    }

    public static double[] dijkstra(int V, ArrayList<ArrayList<AdjListNode>> graph, int src) {
        double[] distance = new double[V]; 
        // int[] from = new int[V];

        for (int i = 0; i < V; i++) {
            distance[i] = Double.MAX_VALUE;
        }

        distance[src] = 0;
  
        PriorityQueue<AdjListNode> pq = new PriorityQueue<>((v1, v2) -> (int)(v1.getWeight() - v2.getWeight()));

        pq.add(new AdjListNode(src, 0));
        // from[src] = -1;
  
        while (pq.size() > 0) {
            AdjListNode current = pq.poll();

            for (AdjListNode n : graph.get(current.getVertex())) {
                if (distance[current.getVertex()] + n.getWeight() < distance[n.getVertex()]) {

                    distance[n.getVertex()] = n.getWeight() + distance[current.getVertex()];

                    pq.add(new AdjListNode(n.getVertex(),distance[n.getVertex()]));

                    // from[current.getVertex()] = n.getVertex();
                }
            }
        }
        // System.out.print("Route: ");
        // printRoute(from, src);
        // System.out.println(src);

        // return server/node
        return distance;
    }

    public static void printRoute(int[] from, int dst){
        if(from[dst] != 0){
            printRoute(from, from[dst]);
        }

        System.out.print(from[dst] + " -> ");
    }

    public double shortestPath(int i, int j) {
        if (_shortestPath == null) {
            floydWarshall();
        }

        return _shortestPath[i][j];
    }

    protected void floydWarshall() {
        _shortestPath = new double[V][V];

        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                _shortestPath[i][j] = 1e9+7;
            }
        }

        for (int i = 0; i < V; i++) {
            for (int j = 0; j < graph.get(i).size(); j++) {
                _shortestPath[i][graph.get(i).get(j).getVertex()] = graph.get(i).get(j).getWeight();
            }
        }

        for (int i = 0; i < V; i++) {
            for (int k = 0; k < V; k++) {
                for (int j = 0; j < V; j++) {
                    _shortestPath[i][k] = Math.min(_shortestPath[i][k], _shortestPath[i][j]+_shortestPath[j][k]);
                }
            }
        }
    }
}


// source: https://www.geeksforgeeks.org/dijkstras-algorithm-for-adjacency-list-representation-greedy-algo-8/, modified