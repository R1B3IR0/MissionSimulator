package Game.Player;

import Game.Item.Item;
import Game.Room;
import Structures.collections.stacks.LinkedStack;

public class Agent{

    private final String name;
    private int health;
    private final int maxHealth;
    private LinkedStack<Item> inventory;
    private Room currentRoom;
    private int bulletProofVest;


    public Agent(String name, int maxHealth) {
        this.name = "Tó Cruz";
        this.maxHealth = 100;
        this.health = maxHealth;
        this.bulletProofVest = 0;
        this.inventory = new LinkedStack<>();
        this.currentRoom = null;
    }


    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public LinkedStack<Item> getInventory() {
        return inventory;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public int getBulletProofVest() {
        return bulletProofVest;
    }


    public void setHealth(int health) {
        this.health = Math.min(health, maxHealth); // Garante que a health não ultrapasse o máximo
    }

    public void setInventory(LinkedStack<Item> inventory) {
        this.inventory = inventory;
    }

    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }

    public void setBulletProofVest(int bulletProofVest) {
        this.bulletProofVest = bulletProofVest;
    }

}
