package exception;

public class DaoOperationException extends RuntimeException {

    public DaoOperationException(String message, Throwable cause) {
        super(message, cause);
    }

    public DaoOperationException(Throwable cause) {
        super(cause);
    }
}
