package Game.Player;

import Game.Item.HealthKit;
import Game.Item.Item;
import Game.Room;
import Structures.collections.lists.ArrayUnorderedList;
import Structures.collections.stacks.ArrayStack;

import java.util.Scanner;

/**
 * Represents an agent in the game who navigates through rooms, has health and power,
 * and can carry health kits in their inventory. The agent can take damage, move between rooms,
 * and choose a starting point within a building.
 */
public class Agent {

    /** The name of the agent */
    private final String name;

    /** The health of the agent */
    private int health;

    /** The maximum health the agent can have */
    private final int MAXHEALTH = 100;

    /** The power of the agent */
    private int power;

    /** The agent's inventory (a stack to store health kits) */
    private ArrayStack<HealthKit> inventory;

    /** The room where the agent is currently located */
    private Room currentRoom;

    /**
     * Constructs a new Agent with default name, maximum health, and power.
     * Initializes an empty inventory to store health kits and sets initial health to the maximum value.
     */
    public Agent() {
        this.name = "Tó Cruz";
        this.health = MAXHEALTH;
        this.inventory = new ArrayStack<>(2);
        this.power = 50;
    }

    /**
     * Gets the name of the agent.
     *
     * @return The name of the agent
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the current health of the agent.
     *
     * @return The current health of the agent
     */
    public int getHealth() {
        return health;
    }

    /**
     * Gets the maximum health of the agent.
     *
     * @return The maximum health of the agent
     */
    public int getMaxHealth() {
        return MAXHEALTH;
    }

    /**
     * Gets the inventory of the agent, which contains health kits.
     *
     * @return The inventory stack of health kits
     */
    public ArrayStack<HealthKit> getInventory() {
        return inventory;
    }

    /**
     * Gets the room where the agent is currently located.
     *
     * @return The current room of the agent
     */
    public Room getCurrentRoom() {
        return currentRoom;
    }

    /**
     * Sets the health of the agent.
     *
     * @param health The new health value to set for the agent
     */
    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * Sets the inventory of the agent.
     *
     * @param inventory The inventory stack to set for the agent
     */
    public void setInventory(ArrayStack<HealthKit> inventory) {
        this.inventory = inventory;
    }

    /**
     * Sets the current room of the agent.
     *
     * @param currentRoom The room to set as the agent's current room
     */
    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }

    /**
     * Gets the power of the agent.
     *
     * @return The power of the agent
     */
    public int getPower() {
        return power;
    }

    /**
     * Sets the power of the agent.
     *
     * @param power The new power value to set for the agent
     */
    public void setPower(int power) {
        this.power = power;
    }

    /**
     * Reduces the agent's health by the specified damage amount.
     *
     * @param damage The amount of damage to subtract from the agent's health
     */
    public void takeDamage(int damage) {
        this.health -= damage;
    }

    /**
     * Checks if the agent is outside the building (i.e., not currently in any room).
     *
     * @return true if the agent is outside the building, false otherwise
     */
    public boolean isOutsideBuilding() {
        return currentRoom == null;
    }

    /**
     * Allows the agent to choose a room to start from among available entry/exit rooms.
     * Prompts the user to select one of the entry/exit rooms and sets it as the agent's current room.
     *
     * @param entryExitRooms The list of entry/exit rooms from which the agent can choose
     */
    public void chooseEntryExitRoom(ArrayUnorderedList<Room> entryExitRooms) {
        if (entryExitRooms.isEmpty()) {
            System.out.println("Nenhuma sala de entrada-saída disponível.");
            return;
        }

        // Displays the available entry/exit rooms to the user
        for (int i = 0; i < entryExitRooms.size(); i++) {
            System.out.println((i + 1) + ". " + entryExitRooms.get(i).getName());
        }

        System.out.println("Escolha uma sala de entrada-saída para começar:");
        Scanner scanner = new Scanner(System.in);
        int chosenRoomIndex = scanner.nextInt() - 1;  // Adjusts for zero-based indexing

        if (chosenRoomIndex < 0 || chosenRoomIndex >= entryExitRooms.size()) {
            System.out.println("Escolha inválida. O jogo será encerrado.");
            return;
        }

        // Sets the chosen room as the agent's current room
        Room chosenRoom = entryExitRooms.get(chosenRoomIndex);
        setCurrentRoom(chosenRoom);
        System.out.println("Agente começa na sala: " + chosenRoom.getName());
    }
}
