package Structures.collections.trees.heaps;

public class PriorityQueueNode<T> implements Comparable<PriorityQueueNode<T>> {
    private static int nextorder = 0;
    private int priority;
    private int order;
    private T element;

    public PriorityQueueNode(T obj, int prio) {
        element = obj;
        priority = prio;
        order = nextorder;
        nextorder++;
    }

    public T getElement() {
        return element;
    }

    public int getPriority() {
        return priority;
    }

    public int getOrder() {
        return order;
    }

    public String toString() {
        String temp = (element.toString() + priority + order);
        return temp;
    }

    /**
     * Returns the 1 if the current node has higher priority than
     * the given node and -1 otherwise.
     *
     * @param obj  the node to compare to this node
     * @return     the integer result of the comparison of the obj
     *             node and this one
     */
    @Override
    public int compareTo(PriorityQueueNode<T> obj) {
        int result;
        PriorityQueueNode<T> temp = obj;

        if (priority > temp.getPriority()) {
            result = 1;
        } else if (priority < temp.getPriority()) {
            result = -1;
        } else if (order > temp.getOrder()) {
            result = 1;
        } else {
            result = -1;
        }
        return result;
    }
}
