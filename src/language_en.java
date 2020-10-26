
import java.util.ListResourceBundle;


/**
 *
 * @author Manuel René Pauls Toews
 */
public class language_en extends ListResourceBundle {
    private static final Object[][] CONTENTS = {
        //buttons
        {"iniciarSesion",              "Login"},
        {"nroCuenta",                       "Account"},
        {"pin",                             "PIN"},
        {"terminarSimulacion",              "End Simulation"}
    };
    @Override
    protected Object[][] getContents() {
        return CONTENTS;
    }
    
}
