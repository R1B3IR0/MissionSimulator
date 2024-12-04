package collections.graphs;

import collections.lists.ArrayUnorderedList;
import collections.queues.LinkedQueue;
import collections.stacks.LinkedStack;

import java.util.Iterator;


/**
 * Graph represents an adjacency matrix implementation of a graph.
 */
public class Graph<T> implements GraphADT<T> {
    protected final int DEFAULT_CAPACITY = 10;
    protected int numVertices;
    protected boolean[][] adjMatrix;
    protected T[] vertices;

    public Graph() {
        numVertices = 0;
        this.adjMatrix = new boolean[DEFAULT_CAPACITY][DEFAULT_CAPACITY];
        this.vertices = (T[]) (new Object[DEFAULT_CAPACITY]);
    }

    protected void expandCapacity() {
        T[] largerVertices = (T[]) (new Object[vertices.length * 2]);
        boolean[][] largerAdjMatrix = new boolean[vertices.length * 2][vertices.length * 2];

        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                largerAdjMatrix[i][j] = adjMatrix[i][j];
            }
            largerVertices[i] = vertices[i];
        }

        vertices = largerVertices;
        adjMatrix = largerAdjMatrix;
    }

    @Override
    public void addVertex(T vertex) {
        if (numVertices == vertices.length) {
            expandCapacity();
        }
        vertices[numVertices] = vertex;
        for (int i = 0; i < numVertices; i++) {
            adjMatrix[numVertices][i] = false;
            adjMatrix[i][numVertices] = false;
        }
        numVertices++;
    }

    @Override
    public void removeVertex(T vertex) {
        for (int i = 0; i < numVertices; i++) {
            if (vertex.equals(vertices[i])) {
                removeVertex(i);
                return;
            }
        }
    }

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
     * Inserts an edge between two vertices of the graph.
     *
     * @param vertex1 the first vertex
     * @param vertex2 the second vertex
     */
    @Override
    public void addEdge(T vertex1, T vertex2) {

        addEdge(getIndex(vertex1), getIndex(vertex2));
    }

    /**
     * Adds an undirected edge between the vertices at the specified indices in the graph.
     * If the indices are valid, an edge is added between the vertices in both directions.
     *
     * @param index1 The index of the first vertex.
     * @param index2 The index of the second vertex.
     */

    public void addEdge(int index1, int index2) {
        if (indexIsValid(index1) && indexIsValid(index2)) {
            adjMatrix[index1][index2] = true;
            adjMatrix[index2][index1] = true;
        }
    }

    /**
     * Retrieves the index of the vertex with the specified value in the graph.
     *
     * @param vertex The value of the vertex whose index is to be retrieved.
     * @return The index of the vertex if found, or -1 if the vertex is not present in the graph.
     */

    public int getIndex(T vertex) {
        for (int i = 0; i < numVertices; i++) {
            if (vertices[i].equals(vertex)) {
                return i;
            }
        }
        return -1;
    }


    /**
     * Removes the undirected edge between the vertices with the specified values in the graph.
     * If the vertices are found, the edge is removed in both directions.
     *
     * @param vertex1 The value of the first vertex.
     * @param vertex2 The value of the second vertex.
     */


    @Override
    public void removeEdge(T vertex1, T vertex2) {

        removeEdge(getIndex(vertex1), getIndex(vertex2));

    }

    /**
     * Removes the undirected edge between the vertices at the specified indices in the graph.
     * If the indices are valid, the edge is removed in both directions in the adjacency matrix.
     *
     * @param index1 The index of the first vertex.
     * @param index2 The index of the second vertex.
     */

    public void removeEdge(int index1, int index2) {
        if (indexIsValid(index1) && indexIsValid(index2)) {
            adjMatrix[index1][index2] = false;
            adjMatrix[index2][index1] = false;
        }
    }


    /**
     * Returns an iterator that performs a breadth-first traversal (BFS) starting
     * from the vertex with the specified value in the graph.
     *
     * @param startVertex The value of the vertex to start the BFS traversal.
     * @return An iterator over the vertices visited during the BFS traversal.
     */

    @Override
    public Iterator<T> iteratorBFS(T startVertex) {
        return iteratorBFS(getIndex(startVertex));
    }

    /**
     * Returns an iterator that performs a breadth-first traversal (BFS) starting
     * from the vertex at the specified index in the graph.
     *
     * @param startIndex The index of the vertex to start the BFS traversal.
     * @return An iterator over the vertices visited during the BFS traversal.
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

        // First BFS traversal
        while (!traversalQueue.isEmpty()) {
            x = traversalQueue.dequeue();
            resultList.addToRear(vertices[x]);

            for (int i = 0; i < numVertices; i++) {
                if (adjMatrix[x][i] && !visited[i]) {
                    traversalQueue.enqueue(i);
                    visited[i] = true;
                }
            }
        }


        boolean[] reverseVisited = new boolean[numVertices];
        for (int i = 0; i < numVertices; i++) {
            reverseVisited[i] = false;
        }

        traversalQueue.enqueue(startIndex);
        reverseVisited[startIndex] = true;

        while (!traversalQueue.isEmpty()) {
            x = traversalQueue.dequeue();

            for (int i = 0; i < numVertices; i++) {
                if (adjMatrix[i][x] && !reverseVisited[i]) {
                    traversalQueue.enqueue(i);
                    reverseVisited[i] = true;
                }
            }
        }

        for (int i = 0; i < numVertices; i++) {
            if (!visited[i] && !reverseVisited[i]) {
                System.out.println("The graph is not strongly connected.");
                return resultList.iterator();
            }
        }

        boolean connected = false;
        for (int i = 0; i < numVertices; i++) {
            if (!adjMatrix[i][0]) {
                connected = true;
            }
        }
        if (!connected) {
            return resultList.iterator();
        }

        return resultList.iterator();
    }

    /**
     * Returns an iterator that performs a depth-first traversal (DFS) starting
     * from the vertex with the specified value in the graph.
     *
     * @param startVertex The value of the vertex to start the DFS traversal.
     * @return An iterator over the vertices visited during the DFS traversal.
     */
    public Iterator<T> iteratorDFS(T startVertex) {

        return iteratorDFS(getIndex(startVertex));
    }

    /**
     * Returns an iterator that performs a depth-first traversal (DFS) starting
     * from the vertex at the specified index in the graph.
     *
     * @param startIndex The index of the vertex to start the DFS traversal.
     * @return An iterator over the vertices visited during the DFS traversal.
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
                if (adjMatrix[x][i] && !visited[i]) {
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
     * Returns an iterator representing the shortest path from the start vertex
     * with the specified value to the target vertex with the specified value.
     *
     * @param startVertex  The value of the start vertex.
     * @param targetVertex The value of the target vertex.
     * @return An iterator over the vertices in the shortest path.
     */

    public Iterator<T> iteratorShortestPath(T startVertex, T targetVertex) {
        return iteratorShortestPath(getIndex(startVertex), getIndex(targetVertex));
    }

    /**
     * Returns an iterator representing the shortest path from the start vertex
     * at the specified index to the target vertex at the specified index.
     *
     * @param startIndex  The index of the start vertex.
     * @param targetIndex The index of the target vertex.
     * @return An iterator over the vertices in the shortest path.
     */

    public Iterator<T> iteratorShortestPath(int startIndex, int targetIndex) {
        ArrayUnorderedList<T> resultList = new ArrayUnorderedList<T>();
        if (!indexIsValid(startIndex) || !indexIsValid(targetIndex)) {
            return resultList.iterator();
        }

        Iterator<Integer> it = iteratorShortestPathIndices(startIndex,
                targetIndex);
        while (it.hasNext()) {
            resultList.addToRear(vertices[it.next()]);
        }
        return resultList.iterator();
    }

    /**
     * Returns an iterator over the indices representing the shortest path
     * from the start vertex at the specified index to the target vertex
     * at the specified index.
     *
     * @param startIndex  The index of the start vertex.
     * @param targetIndex The index of the target vertex.
     * @return An iterator over the indices in the shortest path.
     */

    protected Iterator<Integer> iteratorShortestPathIndices(int startIndex, int targetIndex) {
        int index = startIndex;
        int[] pathLength = new int[numVertices];
        int[] predecessor = new int[numVertices];
        LinkedQueue<Integer> traversalQueue = new LinkedQueue<>();
        ArrayUnorderedList<Integer> resultList = new ArrayUnorderedList<>();

        if (!indexIsValid(startIndex) || !indexIsValid(targetIndex) || (startIndex == targetIndex)) {
            return resultList.iterator();
        }

        boolean[] visited = new boolean[numVertices];
        for (int i = 0; i < numVertices; i++) {
            visited[i] = false;
        }

        traversalQueue.enqueue(startIndex);
        visited[startIndex] = true;
        pathLength[startIndex] = 0;
        predecessor[startIndex] = -1;

        while (!traversalQueue.isEmpty() && (index != targetIndex)) {
            index = traversalQueue.dequeue();


            for (int i = 0; i < numVertices; i++) {
                if (adjMatrix[index][i] && !visited[i]) {
                    pathLength[i] = pathLength[index] + 1;
                    predecessor[i] = index;
                    traversalQueue.enqueue(i);
                    visited[i] = true;
                }
            }
        }
        if (index != targetIndex) {
            return resultList.iterator();
        }

        LinkedStack<Integer> stack = new LinkedStack<>();
        index = targetIndex;
        stack.push(index);
        do {
            index = predecessor[index];
            stack.push(index);
        } while (index != startIndex);

        while (!stack.isEmpty()) {
            resultList.addToRear(stack.pop());
        }

        return resultList.iterator();
    }

    /**
     * Returns an iterator representing the shortest path from the start vertex
     * with the specified value to the target vertex with the specified value.
     *
     * @param startVertex  The value of the start vertex.
     * @param targetVertex The value of the target vertex.
     * @return An iterator over the vertices in the shortest path.
     */


    public int shortestPathLength(T startVertex, T targetVertex) {
        return shortestPathLength(getIndex(startVertex), getIndex(targetVertex));
    }

    /**
     * Retorna o comprimento do caminho mais curto do vértice de início no índice
     * especificado para o vértice de destino no índice especificado.
     *
     * @param startIndex  O índice do vértice de início.
     * @param targetIndex O índice do vértice de destino.
     * @return O comprimento do caminho mais curto.
     */

    public int shortestPathLength(int startIndex, int targetIndex) {
        int result = 0;
        if (!indexIsValid(startIndex) || !indexIsValid(targetIndex)) {
            return 0;
        }

        int index1, index2;
        Iterator<Integer> it = iteratorShortestPathIndices(startIndex, targetIndex);

        if (it.hasNext()) {
            index1 = it.next();
        } else {
            return 0;
        }

        while (it.hasNext()) {
            result++;
            it.next();
        }

        return result;
    }

    public boolean isEmpty() {
        return (numVertices == 0);
    }

    public boolean isConnected() {
        if (isEmpty()) {
            return false;
        }

        Iterator<T> it = iteratorBFS(0);
        int count = 0;

        while (it.hasNext()) {
            it.next();
            count++;
        }
        return (count == numVertices);
    }

    public int size() {

        return numVertices;
    }

    public boolean indexIsValid(int index) {
        return ((index < numVertices) && (index >= 0));
    }

    public void clear() {

        numVertices = 0;

        for (int i = 0; i < vertices.length; i++) {
            vertices[i] = null;
        }

        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                adjMatrix[i][j] = false;
            }
        }


    }
    public String toString() {
        if (numVertices == 0) {
            return "Graph is empty";
        }

        String result = "";

        result += "Adjacency Matrix\n";
        result += "----------------\n";
        result += "index\t";

        for (int i = 0; i < numVertices; i++) {
            result += "" + i;
            if (i < 10) {
                result += " ";
            }
        }
        result += "\n\n";

        for (int i = 0; i < numVertices; i++) {
            result += "" + i + "\t";

            for (int j = 0; j < numVertices; j++) {
                if (adjMatrix[i][j]) {
                    result += "1 ";
                } else {
                    result += "0 ";
                }
            }
            result += "\n";
        }

        result += "\n\nVertex Values";
        result += "\n-------------\n";
        result += "index\tvalue\n\n";

        for (int i = 0; i < numVertices; i++) {
            result += "" + i + "\t";
            result += vertices[i].toString() + "\n";
        }
        result += "\n";
        return result;
    }

    public boolean hasEdge(T vertex1, T vertex2) {
        int index1 = getIndex(vertex1);
        int index2 = getIndex(vertex2);
        if (index1 == -1 || index2 == -1) {
            return false;
        }
        return adjMatrix[index1][index2];
    }



}
