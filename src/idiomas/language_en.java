package idiomas;


import java.util.ListResourceBundle;


/**
 *
 * @author Manuel René Pauls Toews
 */
public class language_en extends ListResourceBundle {
    private static final Object[][] CONTENTS = {
        {"iniciarSesion",                   "Login"},
        {"nroCuenta",                       "Account"},
        {"pin",                             "PIN"},
        {"terminarSimulacion",              "End Simulation"},
        {"agregar",                         "Add"},
        {"documento",                       "Document"},
        {"apellido",                        "Last Name"},
        {"nombre",                          "Name"},
        {"correo",                          "Email"},
        {"telefono",                        "Phone Number"},
        {"acceso",                          "Permission"},
        {"agregarCuenta",                   "Add Account"},
        {"agregarServicio",                 "Add Service"},
        {"direccion",                       "Address"},
        {"monto",                           "Amount"},
        {"nombreServicio",                  "Name"},
        {"iccono",                          "Icon"},
        {"descripcion",                     "Description"},
        {"deposito",                        "Deposit"},
        {"hacerDeposito",                   "Make Deposit"},
        {"desbloquear",                     "Unblock"},
        {"desbloquearCuenta",               "Unblock Account"},
        {"eliminar",                        "Delete"},
        {"eliminarCuenta",                  "Delete Account"},
        {"eliminarServicio",                "Delete Service"},
        {"nombreServicio",                  "Name"},
        {"serviciosDisponibles",            "Available Services"},
        {"efectuarTransferencia",           "Make Transaction"},
        {"pinTransferencia",                "Transaction PIN"},
        {"transferencia",                   "Transaction"},
        {"saldoActual",                     "Current Balancce"},
        {"informacionCuenta",               "Account Information"},
        {"titularNombre",                   "Name"},
        {"cerrarSesion",                    "Logout"},
        {"informe",                         "Report"},
        {"ayuda",                           "Help"},
        {"pagarServicio",                   "Pay Service"},
        {"tituloVentana",                   "Web Banking Simulation"},
        {"bienvenidoNombre",                "Welcome {0}"},
        {"saldo",                           "Balance"},
        {"queDeseaHacer",                   "What are you looking for?"}
    };
    @Override
    protected Object[][] getContents() {
        return CONTENTS;
    }
    
}
