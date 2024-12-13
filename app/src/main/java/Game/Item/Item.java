package Game.Item;

import Game.Player.Agent;
import Game.Room;

/**
 * Represents a generic item in the game that can be applied to an agent.
 * Items are located in rooms and can provide various effects when used by an agent.
 */
public abstract class Item {
    /** The room in which the item is located */
    private Room room;

    /** The type or name of the item */
    private String type;

    /**
     * Constructs a new Item with a specified room and type.
     *
     * @param room The room where the item is located
     * @param type The type or name of the item
     */
    public Item(Room room, String type) {
        this.type = type;
        this.room = room;
    }

    /**
     * Gets the room where the item is located.
     *
     * @return The room where the item is located
     */
    public Room getRoom() {
        return room;
    }

    /**
     * Sets the room where the item is located.
     *
     * @param room The new room for the item
     */
    public void setRoom(Room room) {
        this.room = room;
    }

    /**
     * Gets the type or name of the item.
     *
     * @return The type or name of the item
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type or name of the item.
     *
     * @param type The new type or name for the item
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Abstract method for applying the item to an agent.
     * The specific behavior of the item when applied to an agent will be defined in subclasses.
     *
     * @param agent The agent to which the item is applied
     */
    public abstract void applyKit(Agent agent);

    /**
     * Returns a string representation of the item, which is its type.
     *
     * @return The type or name of the item
     */
    @Override
    public String toString() {
        return type;
    }
}
