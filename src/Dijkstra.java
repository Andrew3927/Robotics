import java.util.Arrays;

class Dijkstra {
    // 未来这个值会赋进sourceVertex的parents的数组对应位里面，特别用于区分当前的vertex"是没有父节点的，为源头"
    private static final int NO_PARENT = -1;

    private static void dijkstra(int[][] adjacencyMatrix, int sourceVertex) {
        // 获得adjacencyMatrix里面的vertex数，用于紧接下来的一系列数组声明（要用几种数组来放/记录我们需要的东西）
        int numVertices = adjacencyMatrix[0].length;

        // 这是一些列的declaration和initialization
        int[] shortestDistances = new int[numVertices]; // 开一个数组来记录当前vertex到sourceVertex的最短路径
        boolean[] added = new boolean[numVertices]; // 开一个数组来记录已经圈定了的vertex
//        for (int i = 0; i < numVertices; i++) { // 初始化：那个记录最短路径的数组里面默认初始化后全是java里最大的数（因为路径不知道，所以搞个无穷，java里的无穷）；记录已经定了圈定vertices，默认初始化全是false（都没有定下来）
//            shortestDistances[i] = Integer.MAX_VALUE;
//            added[i] = false;
//        }
        Arrays.fill(shortestDistances, Integer.MAX_VALUE); // Todo: code in beauty
        Arrays.fill(added, false);// Todo: code in beauty
        shortestDistances[sourceVertex] = 0; // 刚刚全部最短路径默认初始化后的值是正无穷(java的正无穷)，现在对于sourceVertex而言，因为是自己到自己的距离，因此肯定是0
        int[] parents = new int[numVertices]; // 开一个数组用来记录当前节点的父节点 其实对应的关系是：在cut上，父节点和当前节点的路径是cut上最短的路径。之后用来回归地遍历路径，然后把路径输出的
        parents[sourceVertex] = NO_PARENT; // 初始化：对于sourceVertex节点而言，他肯定是没有父节点的，所以就标为-1加以区分（因为我们默认我们的vertex是从数字0到n标号的）

        // 在这一个loop循环里，遍历cut上所有的边edges，然后得到最小的边edge（把将要最小边要连进来的vertex记为nearestVertexIndex）；
        // 把要连进来的vertex在added数组中的位置记为true（已经圈定）
        // relaxation（dijkstra算法中的一个术语）去看所有的vertex，如果在adjacencyMatrix上是大于0的（等于零只用情况是专门对于sourceVertex到自身，那距离肯定是没有的，记为0；另外一种是在两个vertices之间，没有直接路径到达的，也就是没有直接的路，也记为0）
        // 并且在选出的在cut上最短距离边的长度加上那个vertex自己到sourceVertex的edgeDistance后，居然能够...
        for (int i = 1; i < numVertices; i++) {
            int nearestVertexIndex = -1;
            int shortestDistance = Integer.MAX_VALUE;
            for (int vertexIndex = 0; vertexIndex < numVertices; vertexIndex++) {
                if (!added[vertexIndex] && shortestDistances[vertexIndex] < shortestDistance) {
                    nearestVertexIndex = vertexIndex;
                    shortestDistance = shortestDistances[vertexIndex];
                }
            }
            added[nearestVertexIndex] = true;
            System.out.println("[" + nearestVertexIndex + "] added✔️ " + " The currently lowest priority f(" + nearestVertexIndex + ") is " + shortestDistance);
            for (int vertexIndex = 0; vertexIndex < numVertices; vertexIndex++) {
                int edgeDistance = adjacencyMatrix[nearestVertexIndex][vertexIndex];
                if (edgeDistance > 0 && (shortestDistance + edgeDistance) < shortestDistances[vertexIndex]) {
                    parents[vertexIndex] = nearestVertexIndex;
                    shortestDistances[vertexIndex] = shortestDistance + edgeDistance;
                    System.out.println("\t\tUpdating distance f(" + vertexIndex +") of node(vertex)" + " to \t" + shortestDistances[vertexIndex]);
                }
            }
        }
        printSolution(sourceVertex, shortestDistances, parents);
    }

    private static void printSolution(int sourceVertex, int[] shortestDistances, int[] parents) {
        int numVertices = shortestDistances.length;

        System.out.print("\n\nVertex\t Distance\t Path");

        for (int vertexIndex = 0; vertexIndex < numVertices; vertexIndex++) {
            System.out.print("\n" + sourceVertex + " → " + vertexIndex + "\t\t" + shortestDistances[vertexIndex] + "\t\t ");
            printPath(vertexIndex, parents);
        }
    }

    private static void printPath(int currentVertex, int[] parents) {
        if (currentVertex == NO_PARENT) return;
        printPath(parents[currentVertex], parents);
        System.out.print(currentVertex + ", ");
    }

    public static void main(String[] args) {
//        int[][] adjacencyMatrix = {
//                {0, 4, 0, 0, 0, 0, 0, 8, 0},
//                {4, 0, 8, 0, 0, 0, 0, 11, 0},
//                {0, 8, 0, 7, 0, 4, 0, 0, 2},
//                {0, 0, 7, 0, 9, 14, 0, 0, 0},
//                {0, 0, 0, 9, 0, 10, 0, 0, 0},
//                {0, 0, 4, 0, 10, 0, 2, 0, 0},
//                {0, 0, 0, 14, 0, 2, 0, 1, 6},
//                {8, 11, 0, 0, 0, 0, 1, 0, 7},
//                {0, 0, 2, 0, 0, 0, 6, 7, 0}
//        };
//        int[][] adjacencyMatrix = {
//              // A, B, D,  E,  F
//                {0, 69, 0, 135, 0},
//                {69, 0, 75, 0, 0},
//                {0, 75, 0, 165, 180},
//                {135, 0, 165, 0, 80},
//                {0, 0, 180, 80, 0}
//        };

        int[][] adjacencyMatrix = {
                {0, 3, 4, 0, 0, 0},
                {0, 0, 0, 6, 10, 0},
                {0, 5, 0, 0, 8, 0},
                {0, 0, 0, 0, 7, 3},
                {0, 0, 0, 0, 0, 9},
                {0, 0, 0, 0, 0, 0}
        };

        dijkstra(adjacencyMatrix, 0);
    }
}