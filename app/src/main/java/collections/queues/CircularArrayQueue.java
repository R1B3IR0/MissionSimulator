package collections.queues;

import exceptions.EmptyCollectionException;


public class CircularArrayQueue<T> implements QueueADT<T> {
    private static final int DEFAULT_CAPACITY = 100;
    private int front;
    private int rear;
    private int count;
    private T[] array;


    public CircularArrayQueue() {
        this.front = 0;
        this.rear = 0;
        this.count = 0;
        this.array = (T[]) new Object[DEFAULT_CAPACITY];
    }


    public CircularArrayQueue(int initialCapacity) {
        this.front = 0;
        this.rear = 0;
        this.count = 0;
        this.array = (T[]) new Object[initialCapacity];
    }

    private void expandCapacity() {
        if (array.length == count) {
            // Cria novo array com o dobro do tamanho
            T[] temp = (T[])(new Object[array.length * 2]);
            // Percorre os elementos do array e copia para temp
            for (int i = 0; i < array.length; i++) {
                temp[i] = array[i];
            }
            // Atribui o novo array ao array(queue)
            array = temp;
        }
        // Podemos utilizar o arraycopy?
    }

    @Override
    public void enqueue(T element) {

        if (count == array.length) {
            expandCapacity();
        }

        array[rear] = element; // Adiciona o elemento no final da fila
        rear = (rear + 1) % array.length; // Volta à posição 0 se chegar ao final do array
        count++;

        
    }

    @Override
    public T dequeue() throws EmptyCollectionException {
        if(isEmpty()) {
            throw new EmptyCollectionException("A fila está vazia");
        }

        T result = array[front];  // Guarda o elemento a ser removido
        array[front] = null;      // O meu Front passa a ser null e consequentemente é removido

        front = (front + 1) % array.length;

        count--;

        return result;
    }

    @Override
    public T first() throws EmptyCollectionException {
        if(isEmpty()) {
            throw new EmptyCollectionException("A fila está vazia");
        }

        return array[front];
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
        String str = " ";
        int current = front;
        int elementsCount = 0;

        while (elementsCount < count) {
            System.out.print(array[current] + str);
            current = (current + 1) % array.length;
            elementsCount++;
        }
        return str;
    }
}
