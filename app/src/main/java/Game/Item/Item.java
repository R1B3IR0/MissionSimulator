package Game.Item;

import Game.Room;

public abstract class Item {
    private Room room;
    private String type;

    public Item(Room room, String type) {
        this.type = type;
        this.room = room;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public abstract void applyKit();

    @Override
    public String toString() {
        String text = "";

        text += "Divisão: " + room.getName() + "\n";
        text += "Tipo: " + type + "\n";

        return text;
    }

}
