package Game;

public class Connection {
    private Room origin;
    private Room destination;

    public Connection(Room origin, Room destination) {
        this.origin = origin;
        this.destination = destination;
    }

    public Room getOrigin() {
        return origin;
    }

    public void setOrigin(Room origin) {
        this.origin = origin;
    }

    public Room getDestination() {
        return destination;
    }

    public void setDestination(Room destination) {
        this.destination = destination;
    }
}
