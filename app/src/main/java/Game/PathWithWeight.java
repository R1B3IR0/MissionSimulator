package Game;

public class PathWithWeight<T> implements Comparable<T> {
    private T room;
    private double weight;

    public PathWithWeight(T room, double weight) {
        this.room = room;
        this.weight = weight;
    }

    public T getRoom() {
        return room;
    }

    public double getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return "Room: " + room + ", Weight: " + weight;
    }

    @Override
    public int compareTo(T t) {
        PathWithWeight data = (PathWithWeight) t;
        if (data.weight < weight) {
            return 1;
        } else if (data.weight > weight) {
            return -1;
        }
        return 0;
    }
}
