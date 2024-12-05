package Structures.collections.lists;


import Structures.exceptions.ConcurrentModificationException;
import Structures.exceptions.EmptyCollectionException;
import Structures.exceptions.NoSuchElementFound;

import java.util.Iterator;

public abstract class ArrayList<T> implements ListADT<T> {
    protected static final int NOT_FOUND = -1;
    private static final int DEFAULT_CAPACITY = 100;
    /** Number of elements in this array and the index of the next available position in this array.**/
    protected int count; // Rear
    protected T[] array;
    /**The number of modifications in this array **/
    protected int modcount;

    public ArrayList(int initialCapacity) {
        this.count = 0;
        this.array = (T[])(new Object[initialCapacity]);
        this.modcount = 0;
    }

    public ArrayList() {
        this(DEFAULT_CAPACITY);
    }

    protected void expandCapacity() {
        final int TAM = 2;

        T[] newArray = (T[])(new Object[array.length * TAM]);

        System.arraycopy(array, 0, newArray, 0, array.length);

        array = newArray;
    }

    @Override
    public T removeFirst() throws EmptyCollectionException {
        if(isEmpty()) {
            throw new EmptyCollectionException("Array vazio");
        }

        T element = array[0]; // Primeiro elemento do array
        count--; //Decrementa o tamanho do meu array

        // Shift dos elementos no array para a esquerda
        for (int i = 0; i < count; i++) {
            /* Ao realizar o primeiro shift na posição zero o elemento no index[0] perde a referência e consequentemente
             * removido
             * */
            array[i] = array[i+1];
        }

        array[count] = null; // Definimos último index a null

        modcount++; // Dada a existencia de uma modificação no array o modcount incrementa

        return element;
    }

    @Override
    public T removeLast() throws EmptyCollectionException {
        if(isEmpty()) {
            throw new EmptyCollectionException("Array vazio");
        }

        T element = array[count-1]; // Último elemento da minha lista
        count--; // Decrementa no tamanho do meu array

        array[count] = null; // O elemento é removido ao definir-mos o último elemento a null;

        modcount++; // Atualiza sempre que existe uma modificação, no caso o remove

        return element;
    }

    protected int find(T element) {
        int result = NOT_FOUND;

        for(int index = 0 ; index < count; index++) {
            if(array[index].equals(element)) {
                result = index;
                break;
            }
        }

        return result;
    }

    @Override
    public T remove(T element) throws NoSuchElementFound {
        int index = find(element);

        if(isEmpty()) {
            throw new EmptyCollectionException("Array vazio");
        }
        /* Se o retorno do metodo find(element) retornar result = NOT_FOUND lança exceção */
        if (index == NOT_FOUND) {
            throw new NoSuchElementFound("The element is not in the list");
        }

        T removed = array[index];
        count--;

        // Shift dos elementos para a esquerda
        for(int i = index; i < count; i++) {
            /* Ao realizar o shift no index retornado pelo metodo(find) o elemento nesse index perde a referência e é
             * removido
             * */
            array[i] = array[i+1];
        }

        array[count] = null; // Definimos o último index a null

        modcount++; // Incrementa a modificação

        return removed;

    }

    @Override
    public T first() throws EmptyCollectionException {
        if(isEmpty()) {
            throw new EmptyCollectionException("Array vazio");
        }

        return array[0];
    }

    @Override
    public T last() throws EmptyCollectionException {
        if(isEmpty()) {
            throw new EmptyCollectionException("Array vazio");
        }

        return array[count-1];
    }

    @Override
    public boolean contains(T target) {
        return find(target) != NOT_FOUND;
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
        return new ArrayListIterator<T>();
    }

    private class ArrayListIterator<E> implements Iterator<E> {
        /** The current index in the list **/
        private int current;
        /** The expected modification count for this list **/
        private int expectedModCount;
        /** Flag to indicate whether it's okay to remove **/
        private boolean okToRemove;

        public ArrayListIterator() {
            this.current = 0;
            this.expectedModCount = modcount;
            this.okToRemove = false;
        }

        @Override
        public boolean hasNext() {
            return current < count;
        }

        @Override
        public E next() {
            if(!hasNext()) {
                throw new NoSuchElementFound("O iterador não tem elementos");
            }

            if(expectedModCount != modcount) {
                throw new ConcurrentModificationException("A lista foi modificada fora do iterador");
            }
            // Atualiza a flag para indicar que é seguro remover
            okToRemove = true;

            return (E) array[current++];
        }

        @Override
        public void remove() {
            // Verifica se houve modificações concorrentes fora do iterador
            if(expectedModCount != modcount) {
                throw new ConcurrentModificationException("A lista foi modificada fora do iterador");
            }
            // Verifica se next() foi executado antes do remove()
            if(!okToRemove) {
                throw new IllegalStateException("Remove called without a call to next");
            }

            try {
                // Remove o elemento usando o metodo da lista principal, que ajusta count(rear) e modCount automaticamente
                ArrayList.this.remove(array[current-1]);
                // Atualiza expectedModCount para refletir o novo valor de modCount após a remoção
                expectedModCount = modcount;
                // Reseta a flag para impedir múltiplas remoções até que next() seja executado novamente
                okToRemove = false;

            } catch (NoSuchElementFound e) {
                System.out.println("Elemento não encontrado: " + e.getMessage());
            }
        }
    }

    @Override
    public String toString() {
        String result = "[";

        for (int i = 0; i < this.size(); i++) {
            result += array[i];

            if (i < this.size() - 1) {
                result += ", ";
            }
        }
        result += "]";
        return result;
    }
}
