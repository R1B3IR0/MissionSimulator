package Game.Player;

import Game.Item.HealthKit;
import Game.Item.Item;
import Game.Room;
import Structures.collections.stacks.ArrayStack;

public class Agent{

    private final String name;
    private int health;
    private final int MAXHEALTH = 100;
    private int power;
    /** Mochila para armazenar os kits do agente */
    private ArrayStack<HealthKit> inventory;
    private Room currentRoom;


    public Agent() {
        this.name = "Tó Cruz";
        this.health = MAXHEALTH;
        this.inventory = new ArrayStack<>(2);
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

    public ArrayStack<HealthKit> getInventory() {
        return inventory;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setInventory(ArrayStack<HealthKit> inventory) {
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

    public void takeDamage(int damage) {
        this.health -= damage;
    }

    public boolean isOutsideBuilding() {
        return currentRoom == null;
    }


   /*
        @Override
        public String toString() {
        String text = "";

        text += "Nome: " + name + "\n";
        text += "Vida: " + health + "\n";
        text += "Ataque: " + power + "\n";
        text += "Mochila: " + inventory.toString() + "\n";
        text += "Divisão atual: " + currentRoom.toString() + "\n";

        return text;
    }*/
}
