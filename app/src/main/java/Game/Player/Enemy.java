package Game.Player;

import Game.Room;


public class Enemy {
    private String name;
    private int power;
    private Room room;


    public Enemy(String name, int power, Room room) {
        this.name = name;
        this.power = power;
        this.room = room;
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

    public Room getDivisao() {
        return room;
    }

    public void setDivisao(Room room) {
        this.room = room;
    }

    @Override
    public String toString() {
        String text = "";

        text += "Nome: " + name + "\n";
        text += "Poder: " + power + "\n";
        text += "Divisão: " + room.toString() + "\n";

        return text;
    }
}
