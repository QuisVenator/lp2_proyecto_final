package logic.excepciones;

/**
 *
 * @author Manuel René Pauls Toews
 */
public class InvalidCredentialsException extends AuthentificationException {

    public InvalidCredentialsException(String message) {
        super(message);
    }
    public InvalidCredentialsException() {
        super();
    }

}
