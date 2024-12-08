package Game.Item;

import Game.Room;

public abstract class Item {
   private Room room;
    private String tipo;

    public Item (Room room, String tipo) {
        this.tipo = tipo;
        this.room = room;


    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public abstract void aplicarEfeito();

    @Override
    public String toString() {
        return "Item [room=" + room + ", tipo=" + tipo + "]";
    }

}
