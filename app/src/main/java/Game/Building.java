package Game;

import Game.Player.Enemy;
import Structures.collections.graphs.Network;
import Structures.collections.lists.ArrayUnorderedList;
import Structures.collections.lists.UnorderedLinkedList;
import Structures.collections.lists.UnorderedListADT;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

/**
 * Represents a building consisting of rooms and connections between them.
 * It handles the creation of a map (graph) for the building, the calculation of edge weights
 * based on the enemies in the rooms, and visualizes the building as a graph.
 */
import java.util.Iterator;


public class Building {

    private Network<Room> map;


    /**
     * Constructs a new Building with empty rooms, connections, and a map.
     */
    public Building() {
        this.map = new Network<>();
    }

    /**
     * Generates the map for the building by adding vertices and edges to the graph.
     * Vertices represent rooms, and edges represent connections between rooms with associated weights.
     */
    public void generateMap() {
        for (Room room : getRooms()) {
            map.addVertex(room);
        }

        for (Connection connection : getConnections()) {
            Room room1 = connection.getOrigin();
            Room room2 = connection.getDestination();
            double weight = calculateWeight(room1, room2);
            map.addEdge(room1, room2, weight);
        }

        System.out.println("Mapa gerado com sucesso!");
    }

    /**
     * Calculates the weight between two rooms based on the enemies in them.
     * The weight is the sum of the powers of the enemies in both rooms.
     *
     * @param room1 The first room
     * @param room2 The second room
     * @return The calculated weight between the two rooms
     */
    private double calculateWeight(Room room1, Room room2) {
        double weight = 0.0;

        for (Enemy enemy : room1.getEnemies()) {
            weight += enemy.getPower();
        }

        for (Enemy enemy : room2.getEnemies()) {
            weight += enemy.getPower();
        }

        return weight;
    }

    /**
     * Updates the weights of the edges in the graph based on the current enemies in the rooms.
     */
    public void updateWeights() {
        for (Connection connection : getConnections()) {
            Room room1 = connection.getOrigin();
            Room room2 = connection.getDestination();
            double weight = calculateWeight(room1, room2);

            if(map.hasEdge(room1, room2)){
                // Updates the weight of the edge
                map.setEdgeWeight(room1, room2, weight);
            }
        }
    }

    /**
     * Visualizes the building map as a graph using the GraphStream library.
     * Rooms are displayed as nodes, and connections are displayed as edges with weights.
     * Rooms with enemies are styled differently (red and larger) than those without (green).
     */
    public void visualizeGraph() {
        System.setProperty("org.graphstream.ui", "swing");

        // Creates the graph
        Graph graph = new SingleGraph("Building Map");

        // Adiciona os nós (salas)
        for (Room room : getRooms()) {
            Node node = graph.addNode(room.getName());
            String nodeLabel = room.getName();  // Start with the room name

            // Checks if there are enemies in the room and changes the node style
            if (!room.getEnemies().isEmpty()) {
                int numEnemies = room.getEnemies().size();  // Number of enemies in the room
                nodeLabel += " (" + numEnemies + " inimigos)";  // Adds number of enemies to the label
                node.addAttribute("ui.style", "shape: box; fill-color: red; size: 50px, 50px; text-alignment: center; text-size: 15px;");
            } else {
                node.addAttribute("ui.style", "shape: box; fill-color: green; size: 50px, 50px; text-alignment: center; text-size: 15px;");
            }

            // Sets the label for the node
            node.addAttribute("ui.label", nodeLabel);
        }

        // Adiciona as arestas (ligações) com pesos
        for (Room room1 : getRooms()) {
            for (Room room2 : getRooms()) {
                if (!room1.equals(room2) && map.hasEdge(room1, room2)) {
                    double weight = map.getWeight(room1, room2);
                    String edgeId = room1.getName() + "-" + room2.getName();
                    graph.addEdge(edgeId, room1.getName(), room2.getName(), true)
                            .addAttribute("ui.label", String.valueOf(weight));
                }
            }
        }

        // Sets the style for the graph
        graph.addAttribute("ui.stylesheet", "node { text-size: 15; shape: box; fill-color: #A0C4FF; } edge { fill-color: #666; size: 2px; }");

        // Adds layout properties
        graph.addAttribute("layout.force", 1.0);
        graph.addAttribute("layout.grid", true);

        // Displays the graph
        graph.display();
    }

    public Network<Room> getMap() {
        return map;
    }

    /**
     * Sets the map of the building.
     *
     * @param map The map to set
     */
    public void setMap(Network<Room> map) {
        this.map = map;
    }


    /**
     * Retorna todas as salas do edifício
     * @return
     */
    public UnorderedListADT<Room> getRooms() {
        UnorderedListADT<Room> allRooms = new ArrayUnorderedList<>();

        Iterator<Room> roomIterator = map.vertexIterator();
        while (roomIterator.hasNext()) {
            Room room = roomIterator.next();
            if(!allRooms.contains(room)){
                allRooms.addToRear(room);
            }
        }

        return allRooms;
    }

    /**
     * Retorna as conexões do edifício
     * @return
     */
    public UnorderedListADT<Connection> getConnections() {
        UnorderedListADT<Connection> connections = new ArrayUnorderedList<>();

        Iterator<Room> roomIterator = map.vertexIterator();

        while (roomIterator.hasNext()) {
            Room room = roomIterator.next();
            Iterator<Room> neighborIterator = map.findNeighbors(room);

            while (neighborIterator.hasNext()) {
                Room neighbor = neighborIterator.next();
                Connection connection = new Connection(room, neighbor);
                if (!connections.contains(connection)) {
                    connections.addToRear(connection);
                }
            }
        }

        return connections;
    }


    /**
     * Retorna as salas de entrada e saída do edifício
     *
     * @return
     */
    public ArrayUnorderedList<Room> getRoomsWithEntryExit() {
        UnorderedListADT<Room> entryExitRooms = new ArrayUnorderedList<>();

        Iterator<Room> allRooms = map.vertexIterator();

        while (allRooms.hasNext()) {
            Room room = allRooms.next();

            if (room.isEntryExit()) {
                if (!entryExitRooms.contains(room)) {
                    entryExitRooms.addToRear(room);
                }
            }
        }

        return (ArrayUnorderedList<Room>) entryExitRooms;
    }

    /**
     * Returns a string representation of the building, including the names of the rooms.
     *
     * @return A string representation of the building
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Edifício:\n");
        for (Room room : getRooms()) {
            sb.append(room.getName()).append("\n");
        }
        return sb.toString();
    }

    public void addRoom(Room room) {
        if (!map.containsVertex(room)) {
            map.addVertex(room);
        }
    }
}
