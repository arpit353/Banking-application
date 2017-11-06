package exceptions;

public class InvalidLoginException extends Exception {
    public InvalidLoginException() {
        super("Your Login Details are Invalid");
    }
}
