package Structures.exceptions;

public class NoSuchElementFound extends RuntimeException {
  public NoSuchElementFound() {
    super();
  }

  public NoSuchElementFound(String message) {
        super(message);
    }
}
