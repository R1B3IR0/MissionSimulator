package Game.Player;

import Game.Building;
import Game.Room;
import Structures.collections.graphs.Network;
import Structures.collections.lists.ArrayUnorderedList;

import java.util.Iterator;
import java.util.Random;


public class Enemy {
    private String name;
    private int heatlh;
    private int power;
    private Room room;


    public Enemy(String name, int power, Room room) {
        this.name = name;
        this.power = power;
        this.room = room;
        this.heatlh = 100;
    }


    public String getName() {
        return name;
    }

    public void setName(String nome) {
        this.name = nome;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public int getHeatlh() {
        return heatlh;
    }

    public void setHeatlh(int heatlh) {
        this.heatlh = heatlh;
    }

    public void takeDamage(int damage) {
        heatlh -= damage;
    }

    /**
     * Enemies move randomly up to two divisions from your position.
     *
     * @param network
     */
    public void moveRandomly(Network<Room> network, Building building) {
        Room currentRoom = this.getRoom();

        // Obtém um iterador para os vértices (salas) na rede
        Iterator<Room> iterator = network.vertexIterator();

        // Armazena as salas vizinhas
        ArrayUnorderedList<Room> adjacentRooms = new ArrayUnorderedList<>();

        // Percorre os vértices adjacentes
        while (iterator.hasNext()) {
            Room adjacentRoom = iterator.next();

            // Verifica se existe uma aresta entre as duas salas

            if ( adjacentRoom != null && network.getWeight(currentRoom, adjacentRoom) != Double.POSITIVE_INFINITY) {
                adjacentRooms.addToRear(adjacentRoom);  // Adiciona a sala vizinha
            }
        }

        // Armazena as salas a até duas divisões de distância
        ArrayUnorderedList<Room> twoStepRooms = new ArrayUnorderedList<>();

        // Adiciona as salas adjacentes das salas adjacentes
        for (Room adjacentRoom : adjacentRooms) {
            twoStepRooms.addToRear(adjacentRoom);  // Adiciona a sala adjacente

            Iterator<Room> iterator2 = network.vertexIterator();

            while (iterator2.hasNext()) {
                Room twoStepRoom = iterator.next();
                if (twoStepRoom != null && network.getWeight(adjacentRoom, twoStepRoom) != Double.POSITIVE_INFINITY && !twoStepRooms.contains(twoStepRoom)) {
                    twoStepRooms.addToRear(twoStepRoom);
                }
            }
        }

        // Verifica se há salas a até duas divisões de distância
        if (twoStepRooms.isEmpty()) {
            System.out.println(name + " não pode se mover. Nenhuma sala vizinha encontrada.");
            return; // Não move se não houver salas vizinhas
        }

        // Escolhe aleatoriamente uma sala a até duas divisões de distância usando Random
        Random rand = new Random();
        Room newRoom = twoStepRooms.get(rand.nextInt(twoStepRooms.size()));

        // Define a nova sala do inimigo
        this.setRoom(newRoom);
        System.out.println(name + " se moveu para " + newRoom.getName());

        building.updateWeights();
        System.out.println("Pesos atualizados.");
    }




/*
    @Override
    public String toString() {
        String text = "";

        text += "Nome: " + name + "\n";
        text += "Vida: " + heatlh + "\n";
        text += "Ataque: " + power + "\n";
        text += "Divisão: " + room.toString() + "\n";

        return text;
    }
 */

}
