package Game;

import Game.Player.Enemy;
import Structures.collections.graphs.Network;
import Structures.collections.lists.ArrayUnorderedList;
import Structures.collections.lists.UnorderedLinkedList;
import Structures.collections.lists.UnorderedListADT;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

import java.util.Iterator;


public class Building {
    //private UnorderedListADT<Room> rooms;
    //private UnorderedListADT<Connection> connections;
    private Network<Room> map;
    //private ArrayUnorderedList<Room> entryExitRooms;


    public Building() {
        this.map = new Network<>();
    }



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

    /**
     * Atualiza os pesos das arestas do grafo
     */
    public void updateWeights() {
        for (Connection connection : getConnections()) {
            Room room1 = connection.getOrigin();
            Room room2 = connection.getDestination();
            double weight = calculateWeight(room1, room2);

            if(map.hasEdge(room1, room2)){
                // Atualiza o peso da aresta
                map.setEdgeWeight(room1, room2, weight);
            }
        }
    }

    public void visualizeGraph() {
        System.setProperty("org.graphstream.ui", "swing");

        // Cria o grafo
        Graph graph = new SingleGraph("Building Map");

        // Adiciona os nós (salas)
        for (Room room : getRooms()) {
            Node node = graph.addNode(room.getName());
            String nodeLabel = room.getName();  // Começa com o nome da sala

            // Verifica se há inimigos e altera o estilo do nó (cor e formato)
            if (!room.getEnemies().isEmpty()) {
                int numEnemies = room.getEnemies().size();  // Número de inimigos na sala
                nodeLabel += " (" + numEnemies + " inimigos)";  // Adiciona o número de inimigos ao rótulo
                node.addAttribute("ui.style", "shape: box; fill-color: red; size: 50px, 50px; text-alignment: center; text-size: 15px;");
            } else {
                node.addAttribute("ui.style", "shape: box; fill-color: green; size: 50px, 50px; text-alignment: center; text-size: 15px;");
            }

            // Define o rótulo do nó com o nome da sala e o número de inimigos, se aplicável
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

        // Definindo o layout
        graph.addAttribute("ui.stylesheet", "node { text-size: 15; shape: box; fill-color: #A0C4FF; } edge { fill-color: #666; size: 2px; }");

        // Layout de grid para as salas se posicionarem como uma planta
        graph.addAttribute("layout.force", 1.0);
        graph.addAttribute("layout.grid", true);

        // Exibe o gráfico
        graph.display();
    }

    public Network<Room> getMap() {
        return map;
    }

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




    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Edifício:\n");
        for (Room room : getRooms()) {
            sb.append(room.getName()).append("\n");
        }
        //sb.append("Mapa:\n");
        //sb.append(map.toString());
        return sb.toString();
    }

    public void addRoom(Room room) {
        if (!map.containsVertex(room)) {
            map.addVertex(room);
        }
    }
}

