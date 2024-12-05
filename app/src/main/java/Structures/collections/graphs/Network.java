package Structures.collections.graphs;

import Structures.collections.lists.ArrayUnorderedList;
import Structures.collections.queues.LinkedQueue;
import Structures.collections.stacks.LinkedStack;
import Structures.collections.trees.heaps.LinkedHeap;

import java.util.Iterator;

public class Network <T> extends Graph<T> implements NetworkADT <T>{


    protected double[][] adjMatrix;

    /**
     * Constructs an empty network with default capacity.
     */

    public Network() {
        numVertices = 0;
        this.adjMatrix = new double[DEFAULT_CAPACITY][DEFAULT_CAPACITY];
        this.vertices = (T[]) (new Object[DEFAULT_CAPACITY]);
    }

    /**
     * Adds a vertex to the network with an associated value.
     *
     * @param vertex The value of the vertex to be added.
     */


    @Override
    public void addVertex(T vertex) {
        if (numVertices == vertices.length) {
            expandCapacity();
        }

        vertices[numVertices] = vertex;
        for (int i = 0; i <= numVertices; i++) {
            adjMatrix[numVertices][i] = Double.POSITIVE_INFINITY;
            adjMatrix[i][numVertices] = Double.POSITIVE_INFINITY;
        }
        numVertices++;
    }

    /**
     * Removes a vertex from the network based on its value.
     * If the vertex is not found, no action is performed.
     *
     * @param vertex The value of the vertex to be removed.
     */


    @Override
    public void removeVertex(T vertex) {
        for (int i = 0; i < numVertices; i++) {
            if (vertex.equals(vertices[i])) {
                removeVertex(i);
                return;
            }
        }
    }

    /**
     * Removes a vertex from the network based on its index.
     * If the index is not valid, no action is performed.
     *
     * @param index The index of the vertex to be removed.
     */

    @Override
    public void removeVertex(int index) {
        if (indexIsValid(index)) {
            numVertices--;

            for (int i = index; i < numVertices; i++) {
                vertices[i] = vertices[i + 1];
            }

            for (int i = index; i < numVertices; i++) {
                for (int j = 0; j <= numVertices; j++) {
                    adjMatrix[i][j] = adjMatrix[i + 1][j];
                }
            }

            for (int i = index; i < numVertices; i++) {
                for (int j = 0; j < numVertices; j++) {
                    adjMatrix[j][i] = adjMatrix[j][i + 1];
                }
            }
        }
    }
    /**
     * Returns an iterator that performs a depth-first traversal
     * starting from the vertex with the specified value.
     *
     * @param startVertex The value of the start vertex.
     * @return An iterator for the depth-first traversal.
     */

    @Override
    public Iterator<T> iteratorDFS(T startVertex) {
        return iteratorDFS(getIndex(startVertex));
    }

    /**
     * Returns an iterator that performs a depth-first traversal
     * starting from the vertex at the specified index.
     *
     * @param startIndex The index of the start vertex.
     * @return An iterator for the depth-first traversal.
     */

    public Iterator<T> iteratorDFS(int startIndex) {
        Integer x;
        boolean found;
        LinkedStack<Integer> traversalStack = new LinkedStack<>();
        ArrayUnorderedList<T> resultList = new ArrayUnorderedList<>();
        boolean[] visited = new boolean[numVertices];

        if (!indexIsValid(startIndex)) {
            return resultList.iterator();
        }

        for (int i = 0; i < numVertices; i++) {
            visited[i] = false;
        }

        traversalStack.push(startIndex);
        resultList.addToRear(vertices[startIndex]);
        visited[startIndex] = true;

        while (!traversalStack.isEmpty()) {
            x = traversalStack.peek();
            found = false;

            for (int i = 0; (i < numVertices) && !found; i++) {
                if ((adjMatrix[x][i] < Double.POSITIVE_INFINITY) && !visited[i]) {
                    traversalStack.push(i);
                    resultList.addToRear(vertices[i]);
                    visited[i] = true;
                    found = true;
                }
            }
            if (!found && !traversalStack.isEmpty()) {
                traversalStack.pop();
            }
        }
        return resultList.iterator();
    }

    /**
     * Returns an iterator that performs a breadth-first traversal
     * starting from the vertex with the specified value.
     *
     * @param startVertex The value of the start vertex.
     * @return An iterator for the breadth-first traversal.
     */


    @Override
    public Iterator<T> iteratorBFS(T startVertex) {

        return iteratorBFS(getIndex(startVertex));
    }

    /**
     * Returns an iterator that performs a breadth-first traversal
     * starting from the vertex at the specified index.
     *
     * @param startIndex The index of the start vertex.
     * @return An iterator for the breadth-first traversal.
     */


    public Iterator<T> iteratorBFS(int startIndex) {
        Integer x;
        LinkedQueue<Integer> traversalQueue = new LinkedQueue<>();
        ArrayUnorderedList<T> resultList = new ArrayUnorderedList<>();

        if (!indexIsValid(startIndex)) {
            return resultList.iterator();
        }

        boolean[] visited = new boolean[numVertices];
        for (int i = 0; i < numVertices; i++) {
            visited[i] = false;
        }

        traversalQueue.enqueue(startIndex);
        visited[startIndex] = true;

        while (!traversalQueue.isEmpty()) {
            x = traversalQueue.dequeue();
            resultList.addToRear(vertices[x]);

            int count = 0;
            for (int i = 0; i < numVertices; i++) {
                if ((adjMatrix[x][i] < Double.POSITIVE_INFINITY) && !visited[i]) {
                    traversalQueue.enqueue(i);
                    visited[i] = true;
                    count++;
                }
            }

            if (count == 0) {
                for (int i = 0; i < numVertices; i++) {
                    if (adjMatrix[x][i] < Double.POSITIVE_INFINITY){
                        count++;
                    }
                }
                if(count == 0) {
                    resultList = new ArrayUnorderedList<>();
                    return resultList.iterator();
                }
            }
        }
        return resultList.iterator();
    }

    /**
     * Returns an iterator that provides the indices of the vertices in the
     * shortest path from the vertex at the specified start index to the
     * vertex at the specified target index.
     *
     * @param startIndex The index of the start vertex.
     * @param targetIndex The index of the target vertex.
     * @return An iterator for the indices of the vertices in the shortest path.
     */

    @Override
    protected Iterator<Integer> iteratorShortestPathIndices(int startIndex, int targetIndex) {
        int index;
        double weight;
        int[] predecessor = new int[numVertices];
        LinkedHeap<Double> traversalMinHeap = new LinkedHeap<>();
        ArrayUnorderedList<Integer> resultList = new ArrayUnorderedList<>();
        LinkedStack<Integer> stack = new LinkedStack<>();

        int[] pathIndex = new int[numVertices];
        double[] pathWeight = new double[numVertices];
        for (int i = 0; i < numVertices; i++) {
            pathWeight[i] = Double.POSITIVE_INFINITY;
        }

        boolean[] visited = new boolean[numVertices];
        for (int i = 0; i < numVertices; i++) {
            visited[i] = false;
        }

        if (!indexIsValid(startIndex) || !indexIsValid(targetIndex)
                || (startIndex == targetIndex) || isEmpty()) {
            return resultList.iterator();
        }

        pathWeight[startIndex] = 0;
        predecessor[startIndex] = -1;
        visited[startIndex] = true;
        weight = 0;

        for (int i = 0; i < numVertices; i++) {
            if (!visited[i]) {
                pathWeight[i] = pathWeight[startIndex] + adjMatrix[startIndex][i];
                predecessor[i] = startIndex;
                traversalMinHeap.addElement(pathWeight[i]);
            }
        }

        do {
            weight = traversalMinHeap.removeMin();
            traversalMinHeap.removeAllElements();
            if (weight == Double.POSITIVE_INFINITY) // no possible path
            {
                return resultList.iterator();
            } else {
                index = getIndexOfAdjVertexWithWeightOf(visited, pathWeight, weight);
                visited[index] = true;
            }

            for (int i = 0; i < numVertices; i++) {
                if (!visited[i]) {
                    if ((adjMatrix[index][i] < Double.POSITIVE_INFINITY)
                            && (pathWeight[index] + adjMatrix[index][i]) < pathWeight[i]) {
                        pathWeight[i] = pathWeight[index] + adjMatrix[index][i];
                        predecessor[i] = index;
                    }
                    traversalMinHeap.addElement(pathWeight[i]);
                }
            }
        } while (!traversalMinHeap.isEmpty() && !visited[targetIndex]);

        index = targetIndex;
        stack.push(index);
        do {
            index = predecessor[index];
            stack.push(index);
        } while (index != startIndex);

        while (!stack.isEmpty()) {
            resultList.addToRear((stack.pop()));
        }

        return resultList.iterator();
    }

    /**
     * Returns an iterator that provides the indices of the vertices in the shortest path.
     *
     * @param startIndex The index of the start vertex.
     * @param targetIndex The index of the target vertex.
     * @return An iterator for the indices of the vertices in the shortest path.
     */

    public Iterator<Integer> iteratorShortestPathIndicesDijkstra(int startIndex, int targetIndex) {
        ArrayUnorderedList<Integer> resultList = new ArrayUnorderedList<>();
        boolean[] visited = new boolean[numVertices];
        double[] distances = new double[numVertices];
        int[] predecessors = new int[numVertices];

        if (!indexIsValid(startIndex) || !indexIsValid(targetIndex)) {
            return resultList.iterator();
        }

        for (int i = 0; i < numVertices; i++) {
            visited[i] = false;
            distances[i] = Double.POSITIVE_INFINITY;
            predecessors[i] = -1;
        }

        distances[startIndex] = 0;

        while (true) {
            int minIndex = -1;
            double minDistance = Double.POSITIVE_INFINITY;

            for (int i = 0; i < numVertices; i++) {
                if (!visited[i] && distances[i] < minDistance) {
                    minIndex = i;
                    minDistance = distances[i];
                }
            }

            if (minIndex == -1) {
                break;
            }

            visited[minIndex] = true;

            if (minIndex == targetIndex) {

                int current = targetIndex;
                while (current != -1) {
                    resultList.addToFront(current);
                    current = predecessors[current];
                }
                return resultList.iterator();
            }

            for (int i = 0; i < numVertices; i++) {
                if (!visited[i] && adjMatrix[minIndex][i] < Double.POSITIVE_INFINITY) {
                    double newDistance = distances[minIndex] + adjMatrix[minIndex][i];
                    if (newDistance < distances[i]) {
                        distances[i] = newDistance;
                        predecessors[i] = minIndex;
                    }
                }
            }
        }

        return resultList.iterator();
    }

    /**
     * Returns an iterator that provides the vertices in the shortest path
     * from the vertex at the specified start index to the vertex at the
     * specified target index.
     *
     * @param startIndex The index of the start vertex.
     * @param targetIndex The index of the target vertex.
     * @return An iterator for the vertices in the shortest path.
     */

    @Override
    public Iterator<T> iteratorShortestPath(int startIndex, int targetIndex) {
        ArrayUnorderedList<T> templist = new ArrayUnorderedList<>();
        if (!indexIsValid(startIndex) || !indexIsValid(targetIndex)) {
            return templist.iterator();
        }

        if (isBidirectional()) {
            Iterator<Integer> it = iteratorShortestPathIndices(startIndex, targetIndex);
            while (it.hasNext()) {
                templist.addToRear(vertices[it.next()]);
            }
            return templist.iterator();
        }else {
            Iterator<Integer> it = iteratorShortestPathIndicesDijkstra(startIndex, targetIndex);
            while (it.hasNext()) {
                templist.addToRear(vertices[it.next()]);
            }
            return templist.iterator();
        }
    }

    /**
     * Method to check if this network is bidirectional.
     * @return true if this network is bidirectional.
     */

    public boolean isBidirectional() {
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {

                if (adjMatrix[i][j] != adjMatrix[j][i]) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Returns an iterator that provides the vertices in the shortest path
     * from the vertex with the specified start value to the vertex with the
     * specified target value.
     *
     * @param startVertex The start vertex.
     * @param targetVertex The target vertex.
     * @return An iterator for the vertices in the shortest path.
     */

    @Override
    public Iterator<T> iteratorShortestPath(T startVertex, T targetVertex) {
        return iteratorShortestPath(getIndex(startVertex), getIndex(targetVertex));
    }

    /**
     * Returns an iterator containing the vertices with the highest weights in the network
     * between the specified first and last vertices.
     *
     * @param firstVertex The first vertex in the range.
     * @param lastVertex The last vertex in the range.
     * @return An iterator for the vertices with the highest weights in the specified range.
     */

    //rever esta parte --->

    public Iterator<T> iteratorVerticesWithHighestWeight(T firstVertex, T lastVertex) {
        ArrayUnorderedList<T> verticesWithHighestWeight = new ArrayUnorderedList<>();
        double highestWeight = Double.NEGATIVE_INFINITY;

        int startIndex = getIndex(firstVertex);
        int lastIndex = getIndex(lastVertex);

        if (!indexIsValid(startIndex) || !indexIsValid(lastIndex)) {
            return verticesWithHighestWeight.iterator();
        }

        verticesWithHighestWeight.addToRear(vertices[startIndex]);
        int k = startIndex;

        while (k != lastIndex) {
            int nextVertex = -1;
            double maxWeight = Double.NEGATIVE_INFINITY;


            for (int j = 0; j < numVertices; j++) {
                if (adjMatrix[k][j] > maxWeight && adjMatrix[k][j] < Double.POSITIVE_INFINITY &&
                        !verticesWithHighestWeight.contains(vertices[j])) {
                    maxWeight = adjMatrix[k][j];
                    nextVertex = j;
                }
            }


            if (nextVertex == -1) {
                break;
            }


            verticesWithHighestWeight.addToRear(vertices[nextVertex]);
            k = nextVertex;
        }

        return verticesWithHighestWeight.iterator();
    }

    /**
     * Returns an iterator containing the vertices with the lowest weights in the network
     * between the specified first and last vertices.
     *
     * @param firstVertex The first vertex in the range.
     * @param lastVertex The last vertex in the range.
     * @return An iterator for the vertices with the lowest weights in the specified range.
     */

    //rever esta parte --->

    public Iterator<T> iteratorVerticesWithSmallestWeight(T firstVertex, T lastVertex) {
        ArrayUnorderedList<T> verticesWithSmallestWeight = new ArrayUnorderedList<>();
        double smallestWeight = Double.POSITIVE_INFINITY;

        int startIndex = getIndex(firstVertex);
        int lastIndex = getIndex(lastVertex);

        if (!indexIsValid(startIndex) || !indexIsValid(lastIndex)) {
            return verticesWithSmallestWeight.iterator();
        }

        verticesWithSmallestWeight.addToRear(vertices[startIndex]);
        int k = startIndex;

        while (k != lastIndex) {
            int nextVertex = -1;
            smallestWeight = Double.POSITIVE_INFINITY; // Resetar o menor peso a cada iteração


            for (int j = 0; j < numVertices; j++) {
                if (adjMatrix[k][j] < smallestWeight && adjMatrix[k][j] > 0 &&
                        !verticesWithSmallestWeight.contains(vertices[j])) {
                    smallestWeight = adjMatrix[k][j];
                    nextVertex = j;
                }
            }


            if (nextVertex == -1) {
                break;
            }


            verticesWithSmallestWeight.addToRear(vertices[nextVertex]);
            k = nextVertex;
        }

        return verticesWithSmallestWeight.iterator();
    }

    /**
     * Returns the index of an adjacent vertex with a specific weight
     * in the context of Dijkstra's algorithm.
     *
     * @param visited An array indicating whether a vertex has been visited.
     * @param pathWeight An array of path weights for each vertex.
     * @param weight The weight to be found in the pathWeight array.
     * @return The index of an adjacent vertex with the specified weight.
     */

    protected int getIndexOfAdjVertexWithWeightOf(boolean[] visited, double[] pathWeight, double weight) {
        for (int i = 0; i < numVertices; i++) {
            if ((pathWeight[i] == weight) && !visited[i]) {
                for (int j = 0; j < numVertices; j++) {
                    if ((adjMatrix[i][j] < Double.POSITIVE_INFINITY) && visited[j]) {
                        return i;
                    }
                }
            }
        }

        return -1;
    }

    public Network<T> mstNetworkk() {
        int x, y;
        int index;
        double weight;
        int[] edge = new int[2];
        LinkedHeap<Double> minHeap = new LinkedHeap<Double>();
        Network<T> resultGraph = new Network<T>();

        if (isEmpty() || !isConnected()) {
            return resultGraph;
        }
        resultGraph.adjMatrix = new double[numVertices][numVertices];
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                resultGraph.adjMatrix[i][j] = Double.POSITIVE_INFINITY;
            }
            resultGraph.vertices = (T[]) (new Object[numVertices]);
        }
        boolean[] visited = new boolean[numVertices];
        for (int i = 0; i < numVertices; i++) {
            visited[i] = false;
        }
        edge[0] = 0;
        resultGraph.vertices[0] = this.vertices[0];
        resultGraph.numVertices++;
        visited[0] = true;


        for (int i = 0; i < numVertices; i++) {
            if (adjMatrix[0][i] < Double.POSITIVE_INFINITY) {
                minHeap.addElement(adjMatrix[0][i]);
            }
        }

        while ((resultGraph.size() < this.size()) && !minHeap.isEmpty()) {

            do {
                weight = minHeap.removeMin();
                edge = getEdgeWithWeightOf(weight, visited);
            } while (!indexIsValid(edge[0]) || !indexIsValid(edge[1]));

            x = edge[0];
            y = edge[1];
            if (!visited[x]) {
                index = x;
            } else {
                index = y;
            }


            resultGraph.vertices[index] = this.vertices[index];
            visited[index] = true;
            resultGraph.numVertices++;

            resultGraph.adjMatrix[x][y] = this.adjMatrix[x][y];


            for (int i = 0; i < numVertices; i++) {
                if (!visited[i] && (this.adjMatrix[index][i] < Double.POSITIVE_INFINITY)) {
                    edge[0] = index;
                    edge[1] = i;
                    minHeap.addElement(adjMatrix[index][i]);
                }
            }
        }
        return resultGraph;
    }

    public Iterator<T> shortestPathMTS(T startVertex, T endVertex) {
        Network<T> mst = mstNetworkk();
        ArrayUnorderedList<T> path = new ArrayUnorderedList<>();
        boolean[] visited = new boolean[numVertices];


        boolean pathExists = dfs(startVertex, endVertex, mst, visited, path);

        if (pathExists) {
            return path.iterator();
        } else {
            return new ArrayUnorderedList<T>().iterator();
        }
    }

    private boolean dfs(T currentVertex, T endVertex, Network<T> graph, boolean[] visited, ArrayUnorderedList<T> path) {
        visited[graph.getIndex(currentVertex)] = true;
        path.addToRear(currentVertex);

        if (currentVertex.equals(endVertex)) {
            return true;
        }

        for (int i = 0; i < numVertices; i++) {
            if (!visited[i] && graph.adjMatrix[graph.getIndex(currentVertex)][i] < Double.POSITIVE_INFINITY) {
                T nextVertex = graph.vertices[i];
                if (dfs(nextVertex, endVertex, graph, visited, path)) {
                    return true;
                }
            }
        }


        path.removeLast();
        return false;
    }

    /**
     * Returns the weight of the edge between two vertices in the network.
     *
     * @param vertex1 The first vertex.
     * @param vertex2 The second vertex.
     * @return The weight of the specified edge.
     */

    public double getWeight(T vertex1, T vertex2) {
        return getWeight(getIndex(vertex1), getIndex(vertex2));
    }

    /**
     * Returns the weight of the edge between two vertices in the network.
     *
     * @param index1 The index of the first vertex.
     * @param index2 The index of the second vertex.
     * @return The weight of the specified edge.
     */


    private double getWeight(int index1, int index2) {
        return adjMatrix[index1][index2];
    }

    protected int[] getEdgeWithWeightOf(double weight, boolean[] visited) {
        int[] edge = new int[2];
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                if ((adjMatrix[i][j] == weight) && (visited[i] ^ visited[j])) {
                    edge[0] = i;
                    edge[1] = j;
                    return edge;
                }
            }
        }


        edge[0] = -1;
        edge[1] = -1;
        return edge;
    }

    /**
     * Calculates and returns the weight of the shortest path between two vertices
     * in the network. The weight is determined by summing the weights of the edges
     * along the shortest path.
     *
     * @param startIndex The index of the start vertex.
     * @param targetIndex The index of the target vertex.
     * @return The weight of the shortest path between the specified vertices.
     * If the vertices are invalid or no path exists, returns Double.POSITIVE_INFINITY.
     */


    public double shortestPathWeight(int startIndex, int targetIndex) {
        double result = 0;
        if (!indexIsValid(startIndex) || !indexIsValid(targetIndex)) {
            return Double.POSITIVE_INFINITY;
        }

        int index1, index2;
        Iterator<Integer> it = iteratorShortestPathIndices(startIndex,
                targetIndex);

        if (it.hasNext()) {
            index1 = it.next();
        } else {
            return Double.POSITIVE_INFINITY;
        }

        while (it.hasNext()) {
            index2 = it.next();
            result += adjMatrix[index1][index2];
            index1 = index2;
        }

        return result;
    }



    /**
     * Expands the graph's capacity by doubling the size of the arrays
     * representing the vertices and the adjacency matrix.
     *
     * @throws OutOfMemoryError If there is not enough memory to allocate the expanded arrays.
     */

    @Override
    protected void expandCapacity() {
        T[] largerVertices = (T[]) (new Object[vertices.length * 2]);
        double[][] largerAdjMatrix = new double[vertices.length * 2][vertices.length * 2];

        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                largerAdjMatrix[i][j] = adjMatrix[i][j];
            }
            largerVertices[i] = vertices[i];
        }

        vertices = largerVertices;
        adjMatrix = largerAdjMatrix;
    }


    /**
     * Adds an edge between two vertices identified by their values with a default weight of 0.
     *
     * @param vertex1 The first vertex.
     * @param vertex2 The second vertex.
     */
    @Override
    public void addEdge(T vertex1, T vertex2) {
        addEdge(getIndex(vertex1), getIndex(vertex2), 0);
    }

    /**
     * Adds an edge between two vertices identified by their values with a specified weight.
     *
     * @param vertex1 The first vertex.
     * @param vertex2 The second vertex.
     * @param weight The weight of the edge.
     */
    @Override
    public void addEdge(T vertex1, T vertex2, double weight) {
        addEdge(getIndex(vertex1), getIndex(vertex2), weight);
    }

    /**
     * Adds an edge between two vertices identified by their indices with a specified weight.
     *
     * @param index1 The index of the first vertex.
     * @param index2 The index of the second vertex.
     * @param weight The weight of the edge.
     */
    public void addEdge(int index1, int index2, double weight) {
        if (indexIsValid(index1) && indexIsValid(index2)) {
            adjMatrix[index1][index2] = weight;
            adjMatrix[index2][index1] = weight;
        }
    }

    /**
     * Returns the weight of the shortest path between two vertices identified by their values.
     *
     * @param startVertex The start vertex.
     * @param targetVertex The target vertex.
     * @return The weight of the shortest path between the two vertices.
     */
    @Override
    public double shortestPathWeight(T startVertex, T targetVertex) {
        return shortestPathWeight(getIndex(startVertex), getIndex(targetVertex));
    }

    /**
     * Adds a bi-directional edge between two vertices identified by their values with a specified weight.
     *
     * @param vertex1 The first vertex.
     * @param vertex2 The second vertex.
     * @param weight The weight of the edge.
     */
    public void addEdgeBi(T vertex1, T vertex2, double weight) {
        addEdgeBi(getIndex(vertex1), getIndex(vertex2), weight);
    }

    /**
     * Adds a bi-directional edge between two vertices identified by their indices with a specified weight.
     *
     * @param index1 The index of the first vertex.
     * @param index2 The index of the second vertex.
     * @param weight The weight of the edge.
     */
    public void addEdgeBi(int index1, int index2, double weight) {
        if (indexIsValid(index1) && indexIsValid(index2)) {
            adjMatrix[index1][index2] = weight;
            adjMatrix[index2][index1] = weight;
        }
    }

    /**
     * Removes an edge between two vertices identified by their values.
     *
     * @param vertex1 The first vertex.
     * @param vertex2 The second vertex.
     */
    @Override
    public void removeEdge(T vertex1, T vertex2) {
        removeEdge(getIndex(vertex1), getIndex(vertex2));
    }

    /**
     * Removes an edge between two vertices identified by their indices.
     *
     * @param index1 The index of the first vertex.
     * @param index2 The index of the second vertex.
     */
    @Override
    public void removeEdge(int index1, int index2) {
        if (indexIsValid(index1) && indexIsValid(index2)) {
            adjMatrix[index1][index2] = Double.POSITIVE_INFINITY;
            adjMatrix[index2][index1] = Double.POSITIVE_INFINITY;
        }
    }




    public String toString()
    {
        if (numVertices == 0)
            return "Graph is empty";

        String result = new String("");

        /** Print the adjacency Matrix */
        result += "Adjacency Matrix\n";
        result += "----------------\n";
        result += "index\t";

        for (int i = 0; i < numVertices; i++)
        {
            result += "" + i;
            if (i < 10)
                result += " ";
        }
        result += "\n\n";

        for (int i = 0; i < numVertices; i++)
        {
            result += "" + i + "\t";

            for (int j = 0; j < numVertices; j++)
            {
                if (adjMatrix[i][j] < Double.POSITIVE_INFINITY)
                    result += "1 ";
                else
                    result += "0 ";
            }
            result += "\n";
        }

        /** Print the vertex values */
        result += "\n\nVertex Values";
        result += "\n-------------\n";
        result += "index\tvalue\n\n";

        for (int i = 0; i < numVertices; i++)
        {
            result += "" + i + "\t";
            result += vertices[i].toString() + "\n";
        }

        /** Print the weights of the edges */
        result += "\n\nWeights of Edges";
        result += "\n----------------\n";
        result += "index\tweight\n\n";

        for (int i = 0; i < numVertices; i++)
        {
            for (int j = numVertices-1; j > i; j--)
            {
                if (adjMatrix[i][j] < Double.POSITIVE_INFINITY)
                {
                    result += i + " to " + j + "\t";
                    result += adjMatrix[i][j] + "\n";
                }
            }
        }

        result += "\n";
        return result;
    }
}
