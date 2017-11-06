package exceptions;

public class InvalidArgumentException extends Exception{
    public InvalidArgumentException() {
        super("Invalid command line arguments entered");
    }
}
