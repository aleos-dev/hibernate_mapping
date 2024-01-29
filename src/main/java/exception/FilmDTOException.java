package exception;

public class FilmDTOException extends RuntimeException {

    public FilmDTOException(String message, Throwable cause) {
        super(message, cause);
    }

    public FilmDTOException(String message) {
        super(message);
    }
}
