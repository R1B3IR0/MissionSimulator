package Structures.exceptions;

public class ConcurrentModificationException extends RuntimeException {

    public ConcurrentModificationException() {
        super();
    }

    public ConcurrentModificationException(String message) {
        super(message);
    }
}
