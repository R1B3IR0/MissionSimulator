package Game.Player;

import Game.Building;
import Game.Connection;
import Game.Room;
import Structures.collections.graphs.Network;
import Structures.collections.lists.ArrayUnorderedList;

import java.util.Iterator;
import java.util.Random;

/**
 * Represents an enemy in the game that can move between rooms and take damage.
 * The enemy has a name, power, health, and the room it currently occupies.
 * Enemies can move randomly within a building and interact with the environment.
 */
public class Enemy {

    /** The name of the enemy */
    private String name;

    /** The health of the enemy */
    private int heatlh;

    /** The power of the enemy */
    private int power;

    /** The room where the enemy is currently located */
    private Room room;

    /**
     * Constructs a new Enemy with the specified name, power, and initial room.
     * The enemy's health is set to 100 by default.
     *
     * @param name The name of the enemy
     * @param power The power of the enemy
     * @param room The room where the enemy is initially located
     */
    public Enemy(String name, int power, Room room) {
        this.name = name;
        this.power = power;
        this.room = room;
        this.heatlh = 100;
    }

    /**
     * Gets the name of the enemy.
     *
     * @return The name of the enemy
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the enemy.
     *
     * @param nome The name to set for the enemy
     */
    public void setName(String nome) {
        this.name = nome;
    }

    /**
     * Gets the power of the enemy.
     *
     * @return The power of the enemy
     */
    public int getPower() {
        return power;
    }

    /**
     * Sets the power of the enemy.
     *
     * @param power The power to set for the enemy
     */
    public void setPower(int power) {
        this.power = power;
    }

    /**
     * Gets the room where the enemy is currently located.
     *
     * @return The room where the enemy is located
     */
    public Room getRoom() {
        return room;
    }

    /**
     * Sets the room where the enemy is located.
     *
     * @param room The room to set for the enemy
     */
    public void setRoom(Room room) {
        this.room = room;
    }

    /**
     * Gets the current health of the enemy.
     *
     * @return The current health of the enemy
     */
    public int getHeatlh() {
        return heatlh;
    }

    /**
     * Sets the health of the enemy.
     *
     * @param heatlh The health to set for the enemy
     */
    public void setHeatlh(int heatlh) {
        this.heatlh = heatlh;
    }

    /**
     * Reduces the enemy's health by a given damage amount.
     *
     * @param damage The amount of damage to subtract from the enemy's health
     */
    public void takeDamage(int damage) {
        heatlh -= damage;
    }

    /**
     * Moves the enemy randomly up to two rooms away from its current position.
     * The movement is based on the connectivity of the rooms in the building.
     * The enemy will move to a randomly chosen room from the available rooms
     * within two steps of its current position.
     *
     * @param network The network of rooms in the building
     * @param building The building where the enemy resides, used to update room weights
     */
    public void moveRandomly(Network<Room> network, Building building) {
        Room currentRoom = this.getRoom();

        // Finds neighbors of the current room
        Iterator<Room> iteratorConnections = network.findNeighbors(currentRoom);

        // Stores adjacent rooms
        ArrayUnorderedList<Room> adjacentRooms = new ArrayUnorderedList<>();
        while (iteratorConnections.hasNext()) {
            var room = iteratorConnections.next();
            if (adjacentRooms.contains(room) == false) {
                adjacentRooms.addToRear(room);
            }
        }

        // Stores rooms up to two steps away
        ArrayUnorderedList<Room> twoStepRooms = new ArrayUnorderedList<>();

        // Adds adjacent rooms from adjacent rooms
        for (Room adjacentRoom : adjacentRooms) {
            twoStepRooms.addToRear(adjacentRoom);  // Adds the adjacent room

            iteratorConnections = network.findNeighbors(adjacentRoom);

            while (iteratorConnections.hasNext()) {
                room = iteratorConnections.next();
                if (twoStepRooms.contains(room) == false) {
                    twoStepRooms.addToRear(room);
                }
            }
        }

        // If no rooms are found within two steps, the enemy cannot move
        if (twoStepRooms.isEmpty()) {
            System.out.println(name + " não pode se mover. Nenhuma sala vizinha encontrada. Sala atual:" + currentRoom.getName());
            return; // No movement if no neighboring rooms are found
        }

        // Randomly selects a room from the rooms within two steps
        Random rand = new Random();
        Room newRoom = twoStepRooms.get(rand.nextInt(twoStepRooms.size()));

        // Sets the new room for the enemy
        this.setRoom(newRoom);
        System.out.println(name + " moveu-se para " + newRoom.getName());

        building.updateWeights();  // Updates the building's room weights
    }
}
