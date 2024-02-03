package exception;

public class ActorDTOException extends RuntimeException {
    public ActorDTOException(String message) {
        super(message);
    }

    public ActorDTOException(String message, Exception e) {
        super(message, e);
    }
}
