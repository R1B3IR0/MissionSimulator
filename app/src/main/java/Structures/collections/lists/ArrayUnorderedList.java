package Structures.collections.lists;

import Structures.exceptions.NoSuchElementFound;

public class ArrayUnorderedList<T> extends ArrayList<T> implements UnorderedListADT<T> {

    public ArrayUnorderedList() {
        super();
    }

    public ArrayUnorderedList(int initialCapacity) {
        super(initialCapacity);
    }

    @Override
    public void addToFront(T element) {
        if(size() == array.length) {
            expandCapacity();
        }

        for (int index = count; index > 0; index--) {
            array[index] = array[index - 1];
        }

        array[0] = element;
        count++;
        modcount++;
    }

    @Override
    public void addToRear(T element) {
        if(size() == array.length) {
            expandCapacity();
        }

        array[count++] = element;

        modcount++;

    }

    @Override
    public void addAfter(T element, T target) throws NoSuchElementFound {
        if(size() == array.length) {
            expandCapacity();
        }

        int index = find(target);

        if(index == ArrayList.NOT_FOUND) {
            throw new NoSuchElementFound("Elemento não encontrado");
        }

        // Shift elements to make space for the new element
        for (int shift = count; shift > index + 1; shift--) {
            array[shift] = array[shift - 1];
        }

        array[index + 1] = element;
        count++;
        modcount++;

    }
}
