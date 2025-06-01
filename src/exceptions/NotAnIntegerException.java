package exceptions;

public class NotAnIntegerException extends RuntimeException {
    public NotAnIntegerException(String message) {
        super(message);
    }
}
