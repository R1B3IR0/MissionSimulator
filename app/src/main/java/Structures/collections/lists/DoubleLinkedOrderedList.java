package Structures.collections.lists;

public class DoubleLinkedOrderedList<T> extends DoubleLinkedList<T> implements OrderedListADT<T> {

    public DoubleLinkedOrderedList() {
        super();
    }

    @Override
    public void add(T element) {
        if(!(element instanceof Comparable)) {
            throw new IllegalArgumentException("Elemento não é comparável");
        }

        Comparable<T> comparableElement = (Comparable<T>) element;

        DoubleLinearNode<T> newNode = new DoubleLinearNode<>(element);

        if(isEmpty()) {
            head = newNode;
            tail = newNode;
        } else if(comparableElement.compareTo(head.getElement()) <= 0) {
            // Adiciona na cabeça da lista
            newNode.setNext(head);
            head.setPrevious(newNode);
            head = newNode;
        } else if(comparableElement.compareTo(tail.getElement()) >= 0) {
            // Adiciona na cauda da lista
            tail.setNext(newNode);
            newNode.setPrevious(tail);
            tail = newNode;
        } else {
            // Caso não adicione nem à cabeça nem à cauda

            DoubleLinearNode<T> current = head;
            //
            while(current.getNext() != null && comparableElement.compareTo(current.getElement()) > 0) {
                current = current.getNext();
            }

            newNode.setNext(current);
            newNode.setPrevious(current.getPrevious());
            current.getPrevious().setNext(newNode);
            current.setPrevious(newNode);;
        }

        count++;
        modcount++;

    }
}
