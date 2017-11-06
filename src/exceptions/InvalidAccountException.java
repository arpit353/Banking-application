package exceptions;

public class InvalidAccountException extends Exception {
    public InvalidAccountException(int acnum){
        super("Account with account number: " + acnum + " does not exist");
    }
}
