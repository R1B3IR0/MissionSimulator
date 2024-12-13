package Game.Player;

import Game.Item.HealthKit;
import Game.Item.Item;
import Game.Room;
import Structures.collections.lists.ArrayUnorderedList;
import Structures.collections.stacks.ArrayStack;

import java.util.Scanner;

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

    public void chooseEntryExitRoom(ArrayUnorderedList<Room> entryExitRooms) {
        if (entryExitRooms.isEmpty()) {
            System.out.println("Nenhuma sala de entrada-saída disponível.");
            return;
        }


        for (int i = 0; i < entryExitRooms.size(); i++) {
            System.out.println((i + 1) + ". " + entryExitRooms.get(i).getName());
        }
        System.out.println("Escolha uma sala de entrada-saída para começar:");
        Scanner scanner = new Scanner(System.in);
        int chosenRoomIndex = scanner.nextInt() - 1;  // Ajusta para índice zero
        if (chosenRoomIndex < 0 || chosenRoomIndex >= entryExitRooms.size()) {
            System.out.println("Escolha inválida. O jogo será encerrado.");
            return;
        }

        Room chosenRoom = entryExitRooms.get(chosenRoomIndex);
        setCurrentRoom(chosenRoom);
        System.out.println("Agente começa na sala: " + chosenRoom.getName());
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
