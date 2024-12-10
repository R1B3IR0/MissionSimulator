package Game.Player;

import Game.Room;


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
