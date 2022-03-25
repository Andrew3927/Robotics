package solution;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;

public class BFS {
    /**
     * @param adjacencyMatrix an adjacency-matrix-representation of the adjacencyMatrix where (x,y) is true if the the there is an edge between nodes x and y.
     * @param sourceVertex the node to sourceVertex from.
     * @return an array containing the shortest distances from the given sourceVertex node to each other node
     */
    public static int[] bfs(boolean[][] adjacencyMatrix, int sourceVertex) {
        int numVertex = adjacencyMatrix[0].length;
        //A Queue to manage the nodes that have yet to be visited
        Queue<Integer> queue = new PriorityQueue<>();
        //Adding the node to sourceVertex from
        queue.add(sourceVertex);
        //A boolean array indicating whether we have already visited a node
        boolean[] visited = new boolean[numVertex];
        //(The sourceVertex node is already visited)
        visited[sourceVertex] = true;
        // Keeping the distances (might not be necessary depending on your use case)
        int[] distances = new int[numVertex]; // No need to set initial values since every node is visted exactly once
        //While there are nodes left to visit...
        while (!queue.isEmpty()) {
            System.out.println("Visited nodes: " + Arrays.toString(visited));
            System.out.println("\tDistances: " + Arrays.toString(distances));
            int node = queue.remove();
            System.out.println("\t\tRemoving node " + node + " from the queue...");
            //...for all neighboring nodes that haven't been visited yet....
            for (int i = 1; i < adjacencyMatrix[node].length; i++) {
                if (adjacencyMatrix[node][i] && !visited[i]) {
                    // Do whatever you want to do with the node here.
                    // Visit it, set the distance and add it to the queue
                    visited[i] = true;
                    distances[i] = distances[node] + 1;
                    queue.add(i);
                    System.out.println("Visiting node " + i + ", setting its distance to " + distances[i] + " and adding it to the queue");
                } else if (adjacencyMatrix[node][i] && visited[i]) {
                    System.out.println("\t\t\t→ Node " + i + " has been visited.");
                }
            }
            System.out.println("---------------------------------------------------------");
        }
        System.out.println("No more nodes in the queue. Distances: " + Arrays.toString(distances));
        return distances;
    }

    public static void main(String[] args) {
        BFS b = new BFS();
        boolean[][] adjacencyMatrix = {
                {false, true, true, false, false, false}, // 0
                {false, false, false, true, true, false},  // 1
                {false, true, false, false, true, false},  // 2
                {false, false, false, false, true, true},  // 3
                {false, false, false, false, false, true},  // 4
                {false, false, false, false, false, false}  // 5
        };
        int[] distance = b.bfs(adjacencyMatrix, 0);
        System.out.println(Arrays.toString(distance));
    }
}

