package Game;

/**
 * Represents a target in the mission that the agent must rescue.
 * The target has a type, is located in a specific room, and can be marked as rescued.
 */
public class Target {

    private String tipo;
    private Room room;
    private boolean rescued;

    /**
     * Constructs a new Target with the specified type and location.
     *
     * @param tipo The type or description of the target.
     * @param room The room where the target is located.
     */
    public Target(String tipo, Room room) {
        this.tipo = tipo;
        this.room = room;
        this.rescued = false; // Initially, the target is not rescued
    }

    /**
     * Checks whether the target has been rescued.
     *
     * @return {@code true} if the target has been rescued; {@code false} otherwise.
     */
    public boolean isRescued() {
        return rescued;
    }

    /**
     * Marks the target as rescued.
     */
    public void rescue() {
        this.rescued = true;
    }

    /**
     * Retrieves the type or description of the target.
     *
     * @return The type or description of the target.
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Sets the type or description of the target.
     *
     * @param tipo The new type or description of the target.
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * Retrieves the room where the target is located.
     *
     * @return The room where the target is located.
     */
    public Room getRoom() {
        return room;
    }

    /**
     * Sets the room where the target is located.
     *
     * @param room The new room for the target.
     */
    public void setRoom(Room room) {
        this.room = room;
    }

    /**
     * Provides a string representation of the target, including its type and location.
     *
     * @return A string describing the target.
     */
    @Override
    public String toString() {
        String text = "";

        text += "\n   Tipo: " + tipo + "\n";
        text += "   Divisao: " + room.getName() + "\n";

        return text;
    }
}
