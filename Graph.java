import java.util.ArrayList;
import java.util.PriorityQueue;

public class Graph {
    public ArrayList<ArrayList<AdjListNode>> graph = new ArrayList<>();
    public double[][] _shortestPath = null;
    public int V;

    public Graph(int v){
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

        for (int i = 0; i < V; i++) {
            distance[i] = Double.MAX_VALUE;
        }

        distance[src] = 0;
  
        PriorityQueue<AdjListNode> pq = new PriorityQueue<>((v1, v2) -> (int)(v1.getWeight() - v2.getWeight()));

        pq.add(new AdjListNode(src, 0));
  
        while (pq.size() > 0) {
            AdjListNode current = pq.poll();

            for (AdjListNode n : graph.get(current.getVertex())) {
                if (distance[current.getVertex()] + n.getWeight() < distance[n.getVertex()]) {

                    distance[n.getVertex()] = n.getWeight() + distance[current.getVertex()];

                    pq.add(new AdjListNode(n.getVertex(),distance[n.getVertex()]));

                }
            }
        }

        // return server/node
        return distance;
    }
}


// source: https://www.geeksforgeeks.org/dijkstras-algorithm-for-adjacency-list-representation-greedy-algo-8/, modified