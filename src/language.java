
import java.util.ListResourceBundle;


/**
 *
 * @author Manuel René Pauls Toews
 */
public class language extends ListResourceBundle {
    private static final Object[][] CONTENTS = {
        {"iniciarSesion",                   "Iniciar Sesión"},
        {"nroCuenta",                       "Nro. Cuenta"},
        {"pin",                             "PIN"},
        {"terminarSimulacion",              "Terminar Simulación"}
    };
    @Override
    protected Object[][] getContents() {
        return CONTENTS;
    }
    
}
