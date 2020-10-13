package logic.excepciones;

/**
 *
 * @author Manuel René Pauls Toews
 */
public class AuthentificationException extends Exception {
    public AuthentificationException(String message) {
        super(message);
    }
    public AuthentificationException() {
        super();
    }
}
