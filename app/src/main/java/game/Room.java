package game;

public class Room {
    private String name;


    public Room(String name) {
        this.name = name;

    }


    public String getName() {
        return name;
    }


    @Override
    public String toString() {
        return "Room [name=" + name + "]";
    }
}
