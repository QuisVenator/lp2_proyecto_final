package logic.excepciones;

public class InvalidCredentialsException extends AuthentificationException {

    public InvalidCredentialsException(String message) {
        super(message);
    }
    public InvalidCredentialsException() {
        super();
    }

}
