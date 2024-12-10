package Structures.collections.lists;

import Structures.exceptions.ConcurrentModificationException;
import Structures.exceptions.EmptyCollectionException;
import Structures.exceptions.NoSuchElementFound;

import java.util.Iterator;

public abstract class DoubleLinkedList<T> implements ListADT<T> {
    public DoubleLinearNode<T> head;
    public DoubleLinearNode<T> tail;
    /** The number of elements */
    protected int count;
    /** The number of modifications */
    public int modcount;

    public DoubleLinkedList() {
        this.head = null;
        this.tail = null;
        this.count = 0;
        this.modcount = 0;
    }

    @Override
    public T removeFirst() throws EmptyCollectionException {
        if(isEmpty()) {
            throw new EmptyCollectionException(" A lista está vazia");
        }

        T removed = head.getElement();
        head = head.getNext();

        if(head == null) {
            tail = null;
        } else {
            head.setPrevious(null);
        }

        count--;

        modcount++;

        return removed;
    }

    @Override
    public T removeLast() throws EmptyCollectionException {
        if(isEmpty()) {
            throw new EmptyCollectionException("Array vazio");
        }

        T removed = tail.getElement();
        tail = tail.getPrevious();

        if(tail == null) {
            head = null;
        } else {
            tail.setNext(null);
        }

        count--;

        modcount++;

        return removed;
    }

    protected DoubleLinearNode<T> find(T element) {
        boolean found = false;
        DoubleLinearNode<T> current = head;


        for (int i = 0; i < count && !found; i++) {
            if (element.equals(current.getElement())) {
                found = true;
            } else {
                current = current.getNext();
            }
        }

        return current;
    }

    @Override
    public T remove(T element) throws NoSuchElementFound, EmptyCollectionException {
        if(isEmpty()) {
            throw new EmptyCollectionException("A lista está vazia");
        }

        DoubleLinearNode<T> current = find(element);

        if(current == null) {
            throw new NoSuchElementFound("Elemento não encontrado");
        }

        T removed = current.getElement();

        // 1º Verifica na cabeça na lista
        if (current == head) {
            return removeFirst();
        }
        // 2º Verifica na cauda da lista
        if (current == tail) {
            return removeLast();
        }

        // 3º Verifica os elementos intermediários
        current.getPrevious().setNext(current.getNext());//Define o next do nó anterior ao elemento encontrado para o próximo nó
        current.getNext().setPrevious(current.getPrevious());//Define o previous do nó seguinte ao elemento encontrado para o nó anterior

        count--;

        modcount++;

        return removed;
    }

    @Override
    public T first() throws EmptyCollectionException {
        if(isEmpty()) {
            throw new EmptyCollectionException("A lista está vazia");
        }

        return head.getElement();
    }

    @Override
    public T last() throws EmptyCollectionException {
        if(isEmpty()) {
            throw new EmptyCollectionException("A lista está vazia");
        }
        return tail.getElement();
    }

    @Override
    public boolean contains(T target) {
        return find(target) != null;
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
        return new DoubleLinkedIterator();
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        DoubleLinearNode<T> current = head;

        for (int i = 0; i < count; i++) {
            str.append(current.getElement()).append("\n");
            current = current.getNext();
        }

        return str.toString().trim();
    }

    private class DoubleLinkedIterator implements Iterator<T> {
        private DoubleLinearNode<T> current;
        private int expectedModCount;
        private boolean okToRemove;

        public DoubleLinkedIterator() {
            current = head;
            expectedModCount = modcount;
            okToRemove = false;
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public T next() {
            if(modcount != expectedModCount) {
                throw new ConcurrentModificationException("A lista foi modificada fora do iterador");
            }

            if(!hasNext()) {
                throw new NoSuchElementFound("O iterador não tem elementos");
            }

            okToRemove = true;
            T element = current.getElement();
            current = current.getNext(); // Avança para o próximo nó

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
            DoubleLinkedList.this.remove(current.getPrevious().getElement());
            expectedModCount++;

        }
    }
}
