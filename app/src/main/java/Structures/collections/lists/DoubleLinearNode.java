package Structures.collections.lists;

public class DoubleLinearNode<E> {
    private DoubleLinearNode<E> next;
    private DoubleLinearNode<E> previous;
    private E element;

    public DoubleLinearNode(E element) {
        this.next = null;
        this.previous = null;
        this.element = element;
    }

    public DoubleLinearNode<E> getNext() {
        return next;
    }

    public void setNext(DoubleLinearNode<E> next) {
        this.next = next;
    }

    public DoubleLinearNode<E> getPrevious() {
        return previous;
    }

    public void setPrevious(DoubleLinearNode<E> previous) {
        this.previous = previous;
    }

    public E getElement() {
        return element;
    }

    public void setElement(E element) {
        this.element = element;
    }

    @Override
    public String toString() {
        String str =  " ";

        str += str + element.toString();

        return str;
    }
}
