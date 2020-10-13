package logic.excepciones;

/**
 *
 * @author Manuel René Pauls Toews
 */
public class BlockedAccountException extends AuthentificationException {

    public BlockedAccountException(String message) {
        super(message);
    }
    public BlockedAccountException() {
        super();
    }

}
