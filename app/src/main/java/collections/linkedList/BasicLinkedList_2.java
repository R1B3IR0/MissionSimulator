package collections.linkedList;

import collections.queues.LinearNode;
import exceptions.EmptyCollectionException;

/**
 * LinkedList with sentinel nodes
 */
public class BasicLinkedList_2<T> {
    private int count;
    private LinearNode<T> head, tail;

    public BasicLinkedList_2() {
        this.count = 0;
        this.head = new LinearNode<>();
        this.tail = new LinearNode<>();
        head.setNext(tail);
    }

    public LinearNode<T> add(T element) {
        LinearNode<T> newnode = new LinearNode<>(element);
        newnode.setNext(head.getNext());
        head.setNext(newnode);
        count++;
        return newnode;
    }

    public LinearNode<T> removeFirst() {
        if (head.getNext() == tail) {
            throw new EmptyCollectionException("Empty list");
        }

        LinearNode<T> temp = head.getNext();
        head.setNext(temp.getNext());
        count--;

        return temp;
    }


    @Override
    public String toString() {
        String result = " ";
        LinearNode<T> newNode = head.getNext();

        while (newNode != tail) {
            result += newNode.getElement() + " ";
            newNode = newNode.getNext();
        }
        return result;
    }
}
