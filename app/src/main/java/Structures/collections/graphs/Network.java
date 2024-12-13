package Structures.collections.graphs;

import Game.PathWithWeight;
import Structures.collections.graphs.Graph;
import Structures.collections.graphs.NetworkADT;
import Structures.collections.lists.ArrayUnorderedList;


import java.util.Iterator;

/**
 * The Network class represents a network data structure, extending the functionality of a graph.
 * It manages a set of vertices connected by edges, where each edge has an associated weight.
 * This class also supports bidirectional edges and operations to find the shortest path and
 * shortest path weight in the network.
 *
 * @param <T> the type of elements held in this network
 */
public class Network<T> extends Graph<T> implements NetworkADT<T> {

    /**
     * Matrix to store the weights of the edges.
     */
    private double[][] weightMatrix;

    /**
     * Default constructor that initializes the network with a default capacity.
     */
    public Network() {
        super();
        weightMatrix = new double[DEFAULT_CAPACITY][DEFAULT_CAPACITY];
        initializeWeightMatrix();
    }

    /**
     * Find neighbors of a vertex.
     */
    public Iterator<T> findNeighbors(T vertex) {
        ArrayUnorderedList<T> neighbors = new ArrayUnorderedList<>();
        int index = getIndex(vertex);

        if (indexIsValid(index)) {
            for (int i = 0; i < numVertices; i++) {
                if (adjMatrix[index][i]) {
                    neighbors.addToRear(vertices[i]);
                }
            }
        }

        return neighbors.iterator();
    }


    /**
     * Initializes the weight matrix with default values.
     * The weight for an edge from a vertex to itself is set to 0, and to Double.POSITIVE_INFINITY for all other edges.
     */
    private void initializeWeightMatrix() {
        for (int i = 0; i < DEFAULT_CAPACITY; i++) {
            for (int j = 0; j < DEFAULT_CAPACITY; j++) {
                if (i == j) {
                    weightMatrix[i][j] = 0;
                } else {
                    weightMatrix[i][j] = Double.POSITIVE_INFINITY;
                }
            }
        }
    }

    /**
     * Expands the capacity of the network when necessary.
     */
    @Override
    protected void expandCapacity() {
        super.expandCapacity();

        double[][] largerWeightMatrix = new double[vertices.length][vertices.length];
        for (int i = 0; i < numVertices; i++) {
            System.arraycopy(weightMatrix[i], 0, largerWeightMatrix[i], 0, numVertices);
        }
        weightMatrix = largerWeightMatrix;

        for (int i = numVertices; i < weightMatrix.length; i++) {
            for (int j = numVertices; j < weightMatrix.length; j++) {
                weightMatrix[i][j] = Double.POSITIVE_INFINITY;
            }
        }
    }

    /**
     * Checks whether the network contains a specific vertex.
     *
     * @param vertex the vertex to check for
     * @return true if the vertex is present in the network, false otherwise
     */
    public boolean containsVertex(T vertex) {
        for (int i = 0; i < numVertices; i++) {
            if (vertex.equals(vertices[i])) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retrieves the vertex at a given index.
     *
     * @param index the index of the vertex
     * @return the vertex at the specified index
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    public T getVertex(int index) {
        if (indexIsValid(index)) {
            return vertices[index];
        } else {
            throw new IndexOutOfBoundsException("Indice fora dos limites: " + index);
        }
    }

    /**
     * Retrieves the weight of the edge between two vertices.
     *
     * @param vertex1 the first vertex
     * @param vertex2 the second vertex
     * @return the weight of the edge
     * @throws IllegalArgumentException if either vertex is not found
     */
    public double getWeight(T vertex1, T vertex2) {
        int index1 = getIndex(vertex1);
        int index2 = getIndex(vertex2);

        if (indexIsValid(index1) && indexIsValid(index2)) {
            return weightMatrix[index1][index2];
        } else {
            throw new IllegalArgumentException("Vértice não encontrado");
        }
    }

    /**
     * Adds an edge between two vertices with a specified weight.
     *
     * @param vertex1 the first vertex
     * @param vertex2 the second vertex
     * @param weight  the weight of the edge
     */
    @Override
    public void addEdge(T vertex1, T vertex2, double weight) {
        int index1 = getIndex(vertex1);
        int index2 = getIndex(vertex2);

        if (indexIsValid(index1) && indexIsValid(index2)) {
            adjMatrix[index1][index2] = true;
            weightMatrix[index1][index2] = weight;

            // If the network is bidirectional, add the reverse edge as well
            adjMatrix[index2][index1] = true;
            weightMatrix[index2][index1] = weight;
        }
    }

    /**
     * Finds the shortest path from a start vertex to an end vertex, avoiding certain locations.
     *
     * @param startVertex       the index of the start vertex
     * @param endVertex         the index of the end vertex
     * @return an iterator over the indices of the vertices in the shortest path
     */
    public Iterator<T> findShortestPath(T startVertex, T endVertex) {
        int numVertices = this.size();
        double[] distances = new double[numVertices];
        boolean[] visited = new boolean[numVertices];
        int[] previous = new int[numVertices];

        for (int i = 0; i < numVertices; i++) {
            distances[i] = Double.MAX_VALUE;
            previous[i] = -1;
        }

        // Set the distance for the start vertex
        distances[getIndex(startVertex)] = 0;

        for (int i = 0; i < numVertices; i++) {
            int closestVertex = -1;
            double shortestDistance = Double.MAX_VALUE;

            // Find the closest unvisited vertex
            for (int j = 0; j < numVertices; j++) {
                if (!visited[j] && distances[j] < shortestDistance) {
                    closestVertex = j;
                    shortestDistance = distances[j];
                }
            }

            // If no vertex is found, exit the loop
            if (closestVertex == -1) {
                break;
            }

            visited[closestVertex] = true;

            // Update distances for neighboring vertices
            for (int j = 0; j < numVertices; j++) {
                if (!visited[j] && adjMatrix[closestVertex][j]) {  // Checking if an edge exists
                    double edgeDistance = weightMatrix[closestVertex][j];
                    if (distances[closestVertex] + edgeDistance < distances[j]) {
                        distances[j] = distances[closestVertex] + edgeDistance;
                        previous[j] = closestVertex;
                    }
                }
            }
        }

        // Construct the shortest path by backtracking through the `previous` array
        ArrayUnorderedList<T> path = new ArrayUnorderedList<>();
        int endIndex = getIndex(endVertex);
        int startIndex = getIndex(startVertex);

        if (previous[endIndex] != -1 || startVertex.equals(endVertex)) {
            for (int vertex = endIndex; vertex != -1; vertex = previous[vertex]) {
                path.addToFront(getVertex(vertex));
            }
        }

        // Ensure the path starts with startVertex
        if (!path.isEmpty() && path.first().equals(endVertex)) {
            path.addToFront(startVertex);
        }

        return path.iterator();
    }

    public Iterator<PathWithWeight<T>> findShortestPathWithWeights(T startVertex, T endVertex) {
        int numVertices = this.size();
        double[] distances = new double[numVertices];
        boolean[] visited = new boolean[numVertices];
        int[] previous = new int[numVertices];

        for (int i = 0; i < numVertices; i++) {
            distances[i] = Double.MAX_VALUE;
            previous[i] = -1;
        }

        // Set the distance for the start vertex
        distances[getIndex(startVertex)] = 0;

        for (int i = 0; i < numVertices; i++) {
            int closestVertex = -1;
            double shortestDistance = Double.MAX_VALUE;

            // Find the closest unvisited vertex
            for (int j = 0; j < numVertices; j++) {
                if (!visited[j] && distances[j] < shortestDistance) {
                    closestVertex = j;
                    shortestDistance = distances[j];
                }
            }

            // If no vertex is found, exit the loop
            if (closestVertex == -1) {
                break;
            }

            visited[closestVertex] = true;

            // Update distances for neighboring vertices
            for (int j = 0; j < numVertices; j++) {
                if (!visited[j] && adjMatrix[closestVertex][j]) {  // Checking if an edge exists
                    double edgeDistance = weightMatrix[closestVertex][j];
                    if (distances[closestVertex] + edgeDistance < distances[j]) {
                        distances[j] = distances[closestVertex] + edgeDistance;
                        previous[j] = closestVertex;
                    }
                }
            }
        }

        // Construct the shortest path by backtracking through the `previous` array
        ArrayUnorderedList<PathWithWeight<T>> pathWithWeights = new ArrayUnorderedList<>();
        int endIndex = getIndex(endVertex);
        int startIndex = getIndex(startVertex);

        if (previous[endIndex] != -1 || startVertex.equals(endVertex)) {
            for (int vertex = endIndex; vertex != -1; vertex = previous[vertex]) {
                pathWithWeights.addToRear( new PathWithWeight<>(getVertex(vertex), distances[vertex]));
            }
        }

        return pathWithWeights.iterator();
    }

    /**
     * Calculates the weight of the shortest path between two vertices.
     *
     * @param startVertex the starting vertex
     * @param targetVertex the target vertex
     * @return the weight of the shortest path
     */
    @Override
    public double shortestPathWeight(T startVertex, T targetVertex) {
        int startIndex = getIndex(startVertex);
        int targetIndex = getIndex(targetVertex);
        if (!indexIsValid(startIndex) || !indexIsValid(targetIndex)) {
            return Double.POSITIVE_INFINITY;
        }
        double[] distances = new double[numVertices];
        boolean[] visited = new boolean[numVertices];

        for (int i = 0; i < numVertices; i++) {
            distances[i] = Double.POSITIVE_INFINITY;
            visited[i] = false;
        }

        distances[startIndex] = 0;

        for (int i = 0; i < numVertices - 1; i++) {
            int u = minDistance(distances, visited);
            visited[u] = true;

            for (int v = 0; v < numVertices; v++) {

                if (!visited[v] && adjMatrix[u][v] && distances[u] != Double.POSITIVE_INFINITY
                        && distances[u] + weightMatrix[u][v] < distances[v]) {
                    distances[v] = distances[u] + weightMatrix[u][v];
                }
            }
        }
        return distances[targetIndex];
    }

    /**
     * Returns an iterator over the vertices in the network.
     *
     * @return an iterator over the vertices in the network
     */
    public Iterator<T> vertexIterator() {
        return new Iterator<T>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < numVertices;  // Verifica se há mais vértices
            }

            @Override
            public T next() {
                return hasNext() ? vertices[index++] : null;// Retorna o próximo vértice
            }
        };
    }

    /**
     * Finds the vertex with the minimum distance that has not been visited.
     *
     * @param distances an array of distances to each vertex
     * @param visited   an array indicating whether each vertex has been visited
     * @return the index of the vertex with the minimum distance
     */
    private int minDistance(double[] distances, boolean[] visited) {
        double min = Double.POSITIVE_INFINITY;
        int minIndex = -1;

        for (int v = 0; v < numVertices; v++) {
            if (!visited[v] && distances[v] <= min) {
                min = distances[v];
                minIndex = v;
            }
        }
        return minIndex;
    }

    /**
     * Sets the weight of the edge between two vertices.
     *
     * @param vertex1 the first vertex
     * @param vertex2 the second vertex
     * @param weight  the weight of the edge
     */
    public void setEdgeWeight(T vertex1, T vertex2, double weight) {
        int index1 = getIndex(vertex1);
        int index2 = getIndex(vertex2);

        if (indexIsValid(index1) && indexIsValid(index2)) {
            weightMatrix[index1][index2] = weight;
            weightMatrix[index2][index1] = weight;
        }
    }

}