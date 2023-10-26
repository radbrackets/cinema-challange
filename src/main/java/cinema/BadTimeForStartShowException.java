package cinema;

public class BadTimeForStartShowException extends RuntimeException {
    public BadTimeForStartShowException() {
        super("Bad planed start of show");
    }
}
