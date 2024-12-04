package collections.linkedList;

import collections.queues.LinearNode;
import exceptions.EmptyCollectionException;


public class BasicLinkedList<T> {
    private int count;
    private LinearNode<T> head, tail;

    public BasicLinkedList() {
        this.count = 0;
        this.head = null;
        this.tail = null;
    }

    public LinearNode<T> add(T element) {
        LinearNode<T> newnode = new LinearNode<>(element);

        if (head == null) {
            head = newnode;
        } else {
            newnode.setNext(head);
            head = newnode;
        }
        count++;
        return head;
    }


    public LinearNode<T> remove() {
        if(head == null) {
            throw new EmptyCollectionException("Empty list");
        }
        head = head.getNext();

        return head;
    }

    @Override
    public String toString() {
        String result = " ";
        LinearNode<T> newNode = head;

        while (newNode != null) {
            result += newNode.getElement() + " ";
            newNode = newNode.getNext();
        }
        return result;
    }
}
