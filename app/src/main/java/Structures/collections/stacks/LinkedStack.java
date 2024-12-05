package Structures.collections.stacks;
import Structures.collections.queues.LinearNode;
import Structures.exceptions.EmptyCollectionException;

public class LinkedStack<T> implements StackADT<T> {
    private int count;
    private LinearNode<T> top;

    public LinkedStack() {
        this.count = 0;
        this.top = null;
    }

    @Override
    public void push(T element) {
        if(top == null) {
            isEmpty();
        }

        LinearNode<T> newnode = new LinearNode<>(element);
        newnode.setNext(top);
        top = newnode;

        count++;
    }

    @Override
    public T pop() throws EmptyCollectionException {
        if(isEmpty()) {
            throw new EmptyCollectionException("Stack");
        }

        T result = top.getElement();
        top = top.getNext();
        count--;

        return result;
    }

    @Override
    public T peek() throws EmptyCollectionException {
        if(isEmpty()) {
            throw new EmptyCollectionException("Stack");
        }

        return top.getElement();
    }
    /**
     * Returns true if this stack is empty and false otherwise.
     * @return boolean whether this stack is empty
     */
    @Override
    public boolean isEmpty() {
        return count == 0;
    }

    @Override
    public int size() {
        return count;
    }

    @Override
    public String toString() {
        String result = " ";
        LinearNode<T> newNode = top;

        while (newNode != null) {
            result += newNode.getElement() + " ";
            newNode = newNode.getNext();
        }
        return result;
    }
}
