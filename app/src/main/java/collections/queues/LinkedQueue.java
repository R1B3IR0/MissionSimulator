package collections.queues;

import exceptions.EmptyCollectionException;

public class LinkedQueue<T> implements QueueADT<T> {
    private LinearNode<T> front;
    private LinearNode<T> rear;
    private int count;

    public LinkedQueue() {
        front = null;
        rear = null;
        count = 0;
    }

    @Override
    public void enqueue(T element) {
        LinearNode<T> newNode = new LinearNode<>(element);

        if (isEmpty()) {
            front = newNode; // define o meu front para o novo nó
        } else {
            rear.setNext(newNode); // define o próximo nó do meu rear para o novo nó
        }

        rear = newNode; // redefine o meu rear para o novo nó
        count++;
    }

    @Override
    public T dequeue() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Queue is empty");
        }

        T element = front.getElement(); // element = Elemento do meu Front
        front = front.getNext();// Front é redefinido para o próximo nó perdendo assim a referência do nó(Front) anterior;
        count--;

        if (isEmpty()) {
            rear = null;
        }

        return element;
    }

    @Override
    public T first() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Queue is empty");
        }

        return front.getElement();
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
    public String toString() {
        String result = " ";
        LinearNode<T> current = front;

        while (current != null) {
            result += current.getElement() + " ";
            current = current.getNext();
        }

        return result;
    }
}
