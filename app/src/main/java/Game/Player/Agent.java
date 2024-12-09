package Game.Player;

import Game.Item.Item;
import Game.Room;
import Structures.collections.stacks.ArrayStack;
import Structures.collections.stacks.LinkedStack;

public class Agent{

    private final String name;
    private int health;
    private final int MAXHEALTH = 100;
    private int power;
    /** Mochila para armazenar os kits do agente */
    private ArrayStack<Item> inventory;
    private Room currentRoom;


    public Agent() {
        this.name = "Tó Cruz";
        this.health = MAXHEALTH;
        this.inventory = new ArrayStack<>(2);
        this.currentRoom = null;
        this.power = 50;
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

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    @Override
    public String toString() {
        String text = "";

        text += "Nome: " + name + "\n";
        text += "Vida: " + health + "\n";
        text += "Ataque: " + power + "\n";
        text += "Mochila: " + inventory.toString() + "\n";
        text += "Divisão atual: " + currentRoom.toString() + "\n";

        return text;
    }
}
