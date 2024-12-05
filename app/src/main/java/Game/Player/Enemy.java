package Game.Player;

import Game.Room;


public class Enemy {
    private String nomeEnemy;
    private int poder;
    private Room room;


    public Enemy(String nome, int poder, Room room) {
        this.nomeEnemy = nome;
        this.poder = poder;
        this.room = room;
    }


    public String getNomeEnemy() {
        return nomeEnemy;
    }


    public void setNomeEnemy(String nome) {
        this.nomeEnemy = nome;
    }


    public int getPoder() {
        return poder;
    }


    public void setPoder(int poder) {
        this.poder = poder;
    }


    public Room getDivisao() {
        return room;
    }

    public void setDivisao(Room room) {
        this.room = room;
    }

    @Override
    public String toString() {
        return "Inimigo [nome=" + nomeEnemy + ", poder=" + poder + ", divisao=" + room.getName() + "]";
    }
}
