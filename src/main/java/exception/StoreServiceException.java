package exception;

public class StoreServiceException extends RuntimeException {

    public StoreServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public StoreServiceException(String message) {
        super(message);
    }
}

