package primer_final.logic.excepciones;

public class BlockedAccountException extends AuthentificationException {

    public BlockedAccountException(String message) {
        super(message);
    }
    public BlockedAccountException() {
        super();
    }

}
