package Game;

import Structures.collections.graphs.Network;
import Structures.collections.lists.ArrayUnorderedList;

public class Building {

    private Network<Room> roomNetwork;

    public Building() {
        this.roomNetwork = new Network<>();
    }

    /**
     * Adiciona uma sala ao prédio.
     *
     * @param room Sala a ser adicionada.
     */
    public void addRoom(Room room) {
        if (!roomNetwork.containsVertex(room)) {
            roomNetwork.addVertex(room);
        }
        //add vertex
    }

    /**
     * Conecta duas salas com um peso padrão.
     *
     * @param room1 Primeira sala.
     * @param room2 Segunda sala.
     */
    public void connectRooms(Room room1, Room room2) {
        if (room1 != null && room2 != null) {
            roomNetwork.addEdge(room1, room2, 1); // Conexão com peso padrão 1
        }
    } //addEdge

    /**
     * Conecta duas salas com um peso específico.
     *
     * @param room1 Primeira sala.
     * @param room2 Segunda sala.
     * @param weight Peso da conexão.
     */
    public void connectRooms(Room room1, Room room2, double weight) {
        if (room1 != null && room2 != null) {
            roomNetwork.addEdge(room1, room2, weight);
        }
    }

    /**
     * Adiciona salas e conexões com base nos dados do JSON.
     *
     * @param rooms Lista de salas.
     * @param connections Matriz de conexões (pares de nomes de salas).
     */
    public void loadFromJson(ArrayUnorderedList<Room> rooms, String[][] connections) {
        for (Room room : rooms) {
            addRoom(room);
        }

        for (String[] connection : connections) {
            Room room1 = findRoomByName(rooms, connection[0]);
            Room room2 = findRoomByName(rooms, connection[1]);

            if (room1 != null && room2 != null) {
                connectRooms(room1, room2);
            }
        }
    }


    private Room findRoomByName(ArrayUnorderedList<Room> rooms, String name) {
        for (Room room : rooms) {
            if (room.getName().equals(name)) {
                return room;
            }
        }
        return null;
    }


    public Network<Room> getRoomNetwork() {
        return roomNetwork;
    }


    public Room getRoomByName(String name) {
        for (Room room : roomNetwork.getVertices()) {
            if (room.getName().equals(name)) {
                return room;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return roomNetwork.toString();
    }


}
