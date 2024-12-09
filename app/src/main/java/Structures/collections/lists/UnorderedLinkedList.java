package Structures.collections.lists;

import Structures.collections.queues.LinearNode;
import Structures.exceptions.EmptyCollectionException;

public class UnorderedLinkedList<T> extends LinkedList<T> implements UnorderedListADT<T> {
    /**
     * Creates an empty list using the default capacity.
     */
    public UnorderedLinkedList() {
        super();
    }

    @Override
    public void addToFront(T element) {
        LinearNode<T> newNode = new LinearNode<>(element);

        if(isEmpty()){
            head = tail = newNode;
        } else {
            newNode.setNext(head);
            head = newNode;
        }
        count++;
        modcount++;
    }

    @Override
    public void addToRear(T element) {
        LinearNode<T> newNode = new LinearNode<>(element);

        if(isEmpty()){
            head = tail = newNode;
        } else {
            tail.setNext(newNode);
            tail = newNode;
        }
        count++;
        modcount++;
    }

    @Override
    public void addAfter(T element, T target) {
        if(isEmpty()){
            throw new EmptyCollectionException("list");
        }

        LinearNode<T> current = head;
        while(current != null && !current.getElement().equals(target)){
            current = current.getNext();
        }

        if(current == null){
            throw new EmptyCollectionException("target element not found");
        }

        LinearNode<T> newNode = new LinearNode<>(element);
        newNode.setNext(current.getNext());
        current.setNext(newNode);
        count++;
        modcount++;

    }

}
