package Game.Player;

import Game.Room;
import Structures.collections.graphs.Network;
import Structures.collections.lists.ArrayOrderedList;
import Structures.collections.lists.ArrayUnorderedList;
import Structures.collections.lists.ListADT;

import java.util.Iterator;
import java.util.LinkedList;
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

    public void moveRandomly(Network<Room> network) {
        Room currentRoom = this.getRoom();

        // Obtém um iterador para os vértices (salas) na rede
        Iterator<Room> iterator = network.vertexIterator();

        // Armazena as salas vizinhas
        ArrayUnorderedList<Room> adjacentRooms = new ArrayUnorderedList<>();

        // Percorre os vértices adjacentes
        while (iterator.hasNext()) {
            Room adjacentRoom = iterator.next();

            // Verifica se existe uma aresta entre as duas salas
            if (network.getWeight(currentRoom, adjacentRoom) != Double.POSITIVE_INFINITY) {
                adjacentRooms.addToRear(adjacentRoom);  // Adiciona a sala vizinha
            }
        }

        // Limita a lista de salas vizinhas a no máximo 2
        if (adjacentRooms.size() > 2) {
            ArrayUnorderedList<Room> limitedAdjacentRooms = new ArrayUnorderedList<>();
            for (int i = 0; i < 2; i++) {
                limitedAdjacentRooms.addToRear(adjacentRooms.get(i));
            }
            adjacentRooms = limitedAdjacentRooms;
        }

        // Verifica se há salas vizinhas
        if (adjacentRooms.isEmpty()) {
            System.out.println(name + " não pode se mover. Nenhuma sala vizinha encontrada.");
            return; // Não move se não houver salas vizinhas
        }

        // Escolhe aleatoriamente uma sala vizinha usando Random
        Random rand = new Random();
        Room newRoom = adjacentRooms.get(rand.nextInt(adjacentRooms.size()));

        // Move o inimigo para a nova sala
        this.setRoom(newRoom);
        System.out.println(name + " se moveu para " + newRoom.getName());
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
