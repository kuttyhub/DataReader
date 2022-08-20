package customException;

public class UnknownFileTypeException extends Exception {
    public UnknownFileTypeException(String message) {
        super(message);
    }
}
