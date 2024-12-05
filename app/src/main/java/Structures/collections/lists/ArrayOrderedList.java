package Structures.collections.lists;

public class ArrayOrderedList<T> extends ArrayList<T> implements OrderedListADT<T> {

    public ArrayOrderedList() {
        super();
    }

    public ArrayOrderedList(int initialCapacity) {
        super(initialCapacity);
    }

    @Override
    public void add(T element) {
        if(size() == array.length) {
            expandCapacity();
        }

        Comparable<T> comparableElement = (Comparable<T>) element;

        int index = 0;
        while(index < count && comparableElement.compareTo(array[index]) > 0) {
            index++;
        }

        for (int shift = count; shift > index; shift--) {
            array[shift] = array[shift - 1];
        }

        array[index] = element;
        count++;
        modcount++;
    }
}
