package Game;

import Game.Player.Enemy;
import Structures.collections.graphs.Network;
import Structures.collections.lists.UnorderedLinkedList;
import Structures.collections.lists.UnorderedListADT;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;


public class Building {
    private UnorderedListADT<Room> rooms;
    private UnorderedListADT<Connection> connections;
    private Network<Room> map;


    public Building() {
        this.rooms = new UnorderedLinkedList<>();
        this.connections = new UnorderedLinkedList<>();
        this.map = new Network<>();
    }



    public void generateMap() {
        for (Room room : rooms) {
            map.addVertex(room);
        }

        for (Connection connection : connections) {
            Room room1 = connection.getOrigin();
            Room room2 = connection.getDestination();
            double weight = calculateWeight(room1, room2);
            map.addEdge(room1, room2, weight);
        }

        System.out.println("Mapa gerado com sucesso!");
        //System.out.println(map.getClass());
    }

    /**
     * Calcula o peso entre duas salas (ligações)
     * @param room1
     * @param room2
     * @return
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

    public void visualizeGraph() {
        System.setProperty("org.graphstream.ui", "swing");

        Graph graph = new SingleGraph("Building Map");

        // Adiciona os nós (salas)
        for (Room room : rooms) {
            Node node = graph.addNode(room.getName());
            node.addAttribute("ui.label", room.getName());
        }

        // Adiciona as arestas (ligações) com pesos
        for (Room room1 : rooms) {
            for (Room room2 : rooms) {
                if (!room1.equals(room2) && map.hasEdge(room1, room2)) {
                    double weight = map.getWeight(room1, room2);
                    String edgeId = room1.getName() + "-" + room2.getName();
                    graph.addEdge(edgeId, room1.getName(), room2.getName(), true)
                            .addAttribute("ui.label", String.valueOf(weight));
                }
            }
        }

        // Configurações de visualização
        graph.addAttribute("ui.stylesheet", "node { text-size: 20; } edge { text-size: 15; }");
        graph.display();
    }

    public UnorderedListADT<Connection> getConnections() {
        return connections;
    }

    public void setConnections(UnorderedListADT<Connection> connections) {
        this.connections = connections;
    }

    public UnorderedListADT<Room> getRooms() {
        return rooms;
    }

    public void setRooms(UnorderedListADT<Room> rooms) {
        this.rooms = rooms;
    }

    public Network<Room> getMap() {
        return map;
    }

    public void setMap(Network<Room> map) {
        this.map = map;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Edifício:\n");
        for (Room room : rooms) {
            sb.append(room.getName()).append("\n");
        }
        //sb.append("Mapa:\n");
        //sb.append(map.toString());
        return sb.toString();
    }
}

