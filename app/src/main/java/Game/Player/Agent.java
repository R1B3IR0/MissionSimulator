package Game.Player;

import Game.Item.Item;
import Game.Room;
import Structures.collections.stacks.ArrayStack;
import Structures.collections.stacks.LinkedStack;

public class Agent{

    private final String name;
    private int health;
    private final int MAXHEALTH = 100;
    /** Mochila para armazenar os kits do agente */
    private ArrayStack<Item> inventory;
    private Room currentRoom;


    public Agent() {
        this.name = "Tó Cruz";
        this.health = MAXHEALTH;
        this.inventory = new ArrayStack<>();
        this.currentRoom = null;
    }


    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return MAXHEALTH;
    }

    public ArrayStack<Item> getInventory() {
        return inventory;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setInventory(ArrayStack<Item> inventory) {
        this.inventory = inventory;
    }

    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }

}
