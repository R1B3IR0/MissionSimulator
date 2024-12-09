package Structures.collections.lists;

import Structures.collections.queues.LinearNode;
import Structures.exceptions.ConcurrentModificationException;
import Structures.exceptions.EmptyCollectionException;
import Structures.exceptions.NoSuchElementFound;

import java.util.Iterator;

public abstract class LinkedList<T> implements ListADT<T> {
    protected LinearNode<T> head;
    protected LinearNode<T> tail;
    protected int count;
    protected int modcount;

    public LinkedList() {
        this.head = null;
        this.tail = null;
        this.count = 0;
        this.modcount = 0;
    }

    @Override
    public T removeFirst() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("A lista está vazia");
        }

        T removed = head.getElement();
        head = head.getNext();

        count--;

        modcount++;

        return removed;
    }

    @Override
    public T removeLast() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("A lista está vazia");
        }

        LinearNode<T> previous = null;
        LinearNode<T> current = head;

        while(current.getNext() != null) {
            previous = current;
            current = current.getNext();
        }

        LinearNode<T> removed = tail;
        tail = previous;

        if(tail == null) {
            head = null;
        } else {
            tail.setNext(null);
        }

        count--;
        modcount++;

        return removed.getElement();
    }

    @Override
    public T remove(T element) throws NoSuchElementFound {
        if(isEmpty()) {
            throw new EmptyCollectionException();
        }

        boolean found = false;

        LinearNode<T> previous = null;
        LinearNode<T> current = head;
        // Ciclo para encontrar o elemento.
        while (current != null && !found) {
            if(element.equals(current.getElement())) {
                found = true;
            } else {
                previous = current;
                current = current.getNext();
            }
        }

        if(!found) {
            throw new NoSuchElementFound("O elemento não foi encontrado na lista");
        }

        if(size() == 1) {
            head = tail = null;
        } else if (current.equals(head)) {
            head = current.getNext();
        } else if (current.equals(tail)) {
            tail = previous;
            tail.setNext(null);
        } else {
            // Remover o elemento a meio da lista
            previous.setNext(current.getNext());
        }

        count--;
        modcount++;

        return current.getElement();
    }

    @Override
    public T first() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("A lista está vazia");
        }

        return head.getElement();
    }

    @Override
    public T last() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("A lista está vazia");
        }

        return tail.getElement();
    }

    @Override
    public boolean contains(T target) {

        boolean found = false;

        LinearNode<T> current = head;

        while (current != null && !found) {
            if (target.equals(current.getElement())) {
                found = true;
            } else {
                current = current.getNext();
            }
        }

        return false;
    }

    @Override
    public boolean isEmpty() {
        return count == 0;
    }

    @Override
    public int size() {
        return count;
    }

    @Override
    public Iterator<T> iterator() {
        return new LinkedListIterator<>();
    }

    private class LinkedListIterator<E> implements Iterator<E> {
        private LinearNode<E> previous;
        private LinearNode<E> current;
        private int expectedModCount;
        private boolean okToRemove;

        public LinkedListIterator() {
            previous = null;
            current = (LinearNode<E>) head;
            expectedModCount = modcount;
            okToRemove = false;
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public E next() {
            if(modcount != expectedModCount) {
                throw new ConcurrentModificationException("A lista foi modificada fora do iterador");
            }

            if(!hasNext()) {
                throw new NoSuchElementFound("O iterador não tem elementos");
            }

            okToRemove = true;
            E element = current.getElement();
            previous = current;
            current = current.getNext();

            return element;
        }

        @Override
        public void remove() {
            if(modcount != expectedModCount) {
                throw new ConcurrentModificationException("A lista foi modificada fora do iterador");
            }

            if(!okToRemove) {
                throw new IllegalStateException("Não é possível remover");
            }

            okToRemove = false;
            LinkedList.this.remove((T) previous.getElement());
            expectedModCount++;
        }
    }
    @Override
    public String toString() {
        String result = " ";
        LinearNode<T> current = head;
        while (current != null) {
            result += current.getElement() + " ";
            current = current.getNext();
        }
        return result;
    }
}