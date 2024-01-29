package exception;

public class CustomerDTOException extends RuntimeException {

    public CustomerDTOException(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomerDTOException(String message) {
        super(message);
    }
}
