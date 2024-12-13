package Game;

/**
 * Represents a connection between two rooms in the game.
 * A connection links an origin room and a destination room, allowing movement between them.
 */
public class Connection {

    /** The room from which the connection originates */
    private Room origin;

    /** The room to which the connection leads */
    private Room destination;

    /**
     * Constructs a new Connection with specified origin and destination rooms.
     *
     * @param origin The room from which the connection originates
     * @param destination The room to which the connection leads
     */
    public Connection(Room origin, Room destination) {
        this.origin = origin;
        this.destination = destination;
    }

    /**
     * Gets the origin room of this connection.
     *
     * @return The origin room of this connection
     */
    public Room getOrigin() {
        return origin;
    }

    /**
     * Sets the origin room of this connection.
     *
     * @param origin The room to set as the origin
     */
    public void setOrigin(Room origin) {
        this.origin = origin;
    }

    /**
     * Gets the destination room of this connection.
     *
     * @return The destination room of this connection
     */
    public Room getDestination() {
        return destination;
    }

    /**
     * Sets the destination room of this connection.
     *
     * @param destination The room to set as the destination
     */
    public void setDestination(Room destination) {
        this.destination = destination;
    }
}
