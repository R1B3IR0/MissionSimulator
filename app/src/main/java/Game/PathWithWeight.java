package Game;

/**
 * Represents a path between rooms with an associated weight, typically used in graph-based pathfinding.
 *
 * @param <T> The type representing a room or node in the graph.
 */
public class PathWithWeight<T> implements Comparable<T> {
    private T room; // The room or node associated with this path.
    private double weight; // The weight of the path to this room.

    /**
     * Constructs a new PathWithWeight object.
     *
     * @param room   The room or node associated with this path.
     * @param weight The weight or cost of this path.
     */
    public PathWithWeight(T room, double weight) {
        this.room = room;
        this.weight = weight;
    }

    /**
     * Retrieves the room associated with this path.
     *
     * @return The room or node.
     */
    public T getRoom() {
        return room;
    }

    /**
     * Retrieves the weight of this path.
     *
     * @return The weight or cost of the path.
     */
    public double getWeight() {
        return weight;
    }

    /**
     * Provides a string representation of the PathWithWeight object.
     *
     * @return A string containing the room and its associated weight.
     */
    @Override
    public String toString() {
        return "Room: " + room + ", Weight: " + weight;
    }

    /**
     * Compares this PathWithWeight object to another based on their weights.
     *
     * @param t The object to compare against. It must be another PathWithWeight instance.
     * @return A positive value if this path's weight is greater,
     *         a negative value if this path's weight is smaller,
     *         or 0 if the weights are equal.
     * @throws ClassCastException if the provided object is not of type PathWithWeight.
     */
    @Override
    public int compareTo(T t) {
        PathWithWeight<?> data = (PathWithWeight<?>) t;
        if (data.weight < weight) {
            return 1;
        } else if (data.weight > weight) {
            return -1;
        }
        return 0;
    }
}
