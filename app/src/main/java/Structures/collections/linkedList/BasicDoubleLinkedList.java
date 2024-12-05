package Structures.collections.linkedList;

import Structures.collections.lists.DoubleLinearNode;
import Structures.exceptions.EmptyCollectionException;

public class BasicDoubleLinkedList<T> {
    private int count;
    private DoubleLinearNode<T> head, tail;

    public BasicDoubleLinkedList() {
        count = 0;
        head = tail = null;
    }


    public void addToFront(T element) {
        DoubleLinearNode<T> newNode = new DoubleLinearNode<>(element);

        if (head == null) {
            head = tail = newNode;
        } else {
            newNode.setNext(head);
            head.setPrevious(newNode);
            head = newNode;
        }

        count++;
    }

    public DoubleLinearNode<T> removeFirst() {
        if (head == null) {
            throw new EmptyCollectionException("Empty list");
        }

        DoubleLinearNode<T> temp = head;
        head = head.getNext(); // Redefine a cabeça e perde a referência do nó que está antes da nova cabeça.

        if (head != null) {
            head.setPrevious(null);
        } else {
            tail = null;
        }

        count--;

        return temp;
    }

    public DoubleLinearNode<T> removeLast() {
        if (head == null) {
            throw new EmptyCollectionException("Empty list");
        }

        DoubleLinearNode<T> temp = tail;
        tail = tail.getPrevious();

        if (tail != null) {
            tail.setNext(null);
        } else {
            head = null;
        }

        count--;

        return temp;
    }

    public boolean isEmpty() {
        return count == 0;
    }

    public int size() {
        return count;
    }

    @Override
    public String toString() {
        String result = " ";
        DoubleLinearNode<T> newNode = head;

        while (newNode != null) {
            result += newNode.getElement() + " ";
            newNode = newNode.getNext();
        }
        return result;
    }

}
