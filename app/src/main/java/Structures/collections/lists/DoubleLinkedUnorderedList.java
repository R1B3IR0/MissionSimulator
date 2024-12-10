package Structures.collections.lists;

import Structures.exceptions.NoSuchElementFound;

public class DoubleLinkedUnorderedList<T> extends DoubleLinkedList<T> implements UnorderedListADT<T> {

    public DoubleLinkedUnorderedList() {
        super();
    }

    @Override
    public void addToFront(T element) {
        DoubleLinearNode<T> newNode = new DoubleLinearNode<>(element);

        if(isEmpty()) {
            head = newNode;
            tail = newNode;
        } else {
            newNode.setNext(head);
            head.setPrevious(newNode);
            head = newNode;
        }

        count++;
        modcount++;

    }

    @Override
    public void addToRear(T element) {
        DoubleLinearNode<T> newNode = new DoubleLinearNode<>(element);

        if(isEmpty()) {
            head = newNode;
            tail = newNode;
        } else {
            tail.setNext(newNode);
            newNode.setPrevious(tail);
            tail = newNode;
        }

        count++;
        modcount++;

    }

    @Override
    public void addAfter(T element, T target) throws NoSuchElementFound {
        DoubleLinearNode<T> current = head;
        boolean found = false;

        while (current != null && !found) {
            if (current.getElement().equals(target)) {
                found = true;
            } else {
                current = current.getNext();
            }
        }

        if (!found) {
            throw new NoSuchElementFound("Element not found");
        }

        DoubleLinearNode<T> newNode = new DoubleLinearNode<>(element);

        if (current == tail) {
            tail.setNext(newNode);
            newNode.setPrevious(tail);
            tail = newNode;
        } else {
            newNode.setNext(current.getNext());
            newNode.setPrevious(current);
            current.getNext().setPrevious(newNode);
            current.setNext(newNode);
        }

        count++;
        modcount++;
    }


}
