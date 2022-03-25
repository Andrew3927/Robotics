package solution;

import java.util.Arrays;

public class AStar2 {
    private static final int INFINITY = Integer.MAX_VALUE;
    private static final int NO_PARENT = -1;
    private static int GOAL_VERTEX = -2;

    private static void aStar(int[][] adjacencyMatrix, double[][] heuristic, int sourceVertex, int goalVertex) {
        GOAL_VERTEX = goalVertex;

        int numVertices = adjacencyMatrix[0].length;

        int[] g = new int[numVertices]; // g[] = distance[]
        Arrays.fill(g, INFINITY);
        g[sourceVertex] = 0;
        double[] f = new double[numVertices];
        Arrays.fill(f, INFINITY);
        f[sourceVertex] = heuristic[sourceVertex][goalVertex];
        boolean[] added = new boolean[numVertices];
        Arrays.fill(added, false);
        int[] parents = new int[numVertices];
        parents[sourceVertex] = NO_PARENT;

        int index_lowestVertex = -2; // just to make it can be sourceVertex
        while (index_lowestVertex != -1) {
            double lowest_f = INFINITY;
            index_lowestVertex = -1;
            for (int vertexIndex = 0; vertexIndex < numVertices; vertexIndex++) {
                if (!added[vertexIndex] && f[vertexIndex] < lowest_f) {
                    lowest_f = f[vertexIndex];
                    index_lowestVertex = vertexIndex;
                }
            }

            if (index_lowestVertex == -1) {
                return;
            } else if (index_lowestVertex == goalVertex) {
//                System.out.printf("Hit the goal vertex [" + index_lowestVertex + "] and the distance g(x) is " + g[index_lowestVertex]);
                printSolution(sourceVertex, goalVertex, g, parents);
                return;
            }

            added[index_lowestVertex] = true;
            System.out.println("[" + index_lowestVertex + "] added✔️ " + " The currently lowest priority f(" + index_lowestVertex + ") is " + lowest_f);

            for (int vertexIndex = 0; vertexIndex < numVertices; vertexIndex++) {
                if (!added[vertexIndex] && (adjacencyMatrix[index_lowestVertex][vertexIndex] != 0)) {
                    int g_currentLowestVertex = g[index_lowestVertex];
                    int edgeDistance = adjacencyMatrix[index_lowestVertex][vertexIndex];
                    int g_newEdge = g_currentLowestVertex + edgeDistance;
                    if (g_newEdge < g[vertexIndex]) {
                        g[vertexIndex] = g_newEdge;
                        f[vertexIndex] = g_newEdge + heuristic[vertexIndex][goalVertex];
                        parents[vertexIndex] = index_lowestVertex;
                        System.out.println("\t\tUpdating distance g(" + vertexIndex + ") of node(vertex)" + " to \t" + g[vertexIndex] + "\tand priority f(" + vertexIndex + ") to " + f[vertexIndex]);
                    }
                }
            }
        }
    }

    private static void printSolution(int sourceVertex, int goalVertex, int[] g, int[] parents) {
        System.out.print("\n\nVertex\tDistance\tPath");
        System.out.print("\n" + sourceVertex + " → " + goalVertex + "\t\t" + g[goalVertex] + "\t\t");
        printPath(goalVertex, parents);
    }

    private static void printPath(int currentVertex, int[] parents) {
        if (currentVertex == NO_PARENT) {
            return;
        }
        printPath(parents[currentVertex], parents);
        if (currentVertex == GOAL_VERTEX) {
            System.out.print(currentVertex);
        } else {
            System.out.print(currentVertex + ", ");
        }

    }


    public static void main(String[] args) {
        int[][] adjacencyMatrix = {
                {0, 3, 4, 0, 0, 0},
                {0, 0, 0, 6, 10, 0},
                {0, 5, 0, 0, 8, 0},
                {0, 0, 0, 0, 7, 3},
                {0, 0, 0, 0, 0, 9},
                {0, 0, 0, 0, 0, 0}
        };

        double[][] heuristic = {
                {0, 0, 0, 0, 0, 8},
                {0, 0, 0, 0, 0, 6.324555}, // the Pythagorean theorem
                {0, 0, 0, 0, 0, 6.324555}, // the Pythagorean theorem
                {0, 0, 0, 0, 0, 2},
                {0, 0, 0, 0, 0, 2},
                {0, 0, 0, 0, 0, 0}
        };

        int sourceVertex = 0;
        int goalVertex = 5;
//        int[][] adjacencyMatrix = {
//
//                {0, 69, 0, 135, 0},
//                {69, 0, 75, 0, 0},
//                {0, 75, 0, 165, 180},
//                {135, 0, 165, 0, 80},
//                {0, 0, 180, 80, 0}
//        };
//
//        double[][] heuristic = {
//                {0, 69, 120, 135, 195},
//                {69, 0, 75, 170, 190},
//                {120, 75, 0, 165, 180},
//                {135, 170, 165, 0, 80},
//                {195, 190, 180, 80, 0}
//        };
//        int sourceVertex = 0;
//        int goalVertex = 4;
        aStar(adjacencyMatrix, heuristic, sourceVertex, goalVertex);
    }
}