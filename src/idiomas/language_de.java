package idiomas;


import java.util.ListResourceBundle;


/**
 *
 * @author Manuel René Pauls Toews
 */
public class language_de extends ListResourceBundle {
    private static final Object[][] CONTENTS = {
        {"iniciarSesion",                   "Anmelden"},
        {"nroCuenta",                       "Konto"},
        {"pin",                             "PIN"},
        {"terminarSimulacion",              "Simulation Beenden"},
        {"agregar",                         "Hinzufügen"},
        {"documento",                       "Dokument"},
        {"apellido",                        "Nachname"},
        {"nombre",                          "Name"},
        {"correo",                          "E-Mail"},
        {"telefono",                        "Telefonnummer"},
        {"acceso",                          "Zugriff"},
        {"agregarCuenta",                   "Konto Hinzügen"},
        {"agregarServicio",                 "Dienstleistung Hinzufügen"},
        {"direccion",                       "Addresse"},
        {"monto",                           "Betrag"},
        {"nombreServicio",                  "Name"},
        {"iccono",                          "Symbol"},
        {"descripcion",                     "Beschreibung"},
        {"deposito",                        "Einzahlen"},
        {"hacerDeposito",                   "Einzahlen"},
        {"desbloquear",                     "Entsperren"},
        {"desbloquearCuenta",               "Konto Entsperren"},
        {"eliminar",                        "Löschen"},
        {"eliminarCuenta",                  "Konto Löschen"},
        {"eliminarServicio",                "Dienstleistung Löschen"},
        {"nombreServicio",                  "Name"},
        {"serviciosDisponibles",            "Verfügbare Dienstleistungen"},
        {"efectuarTransferencia",           "Überweisung tätigen"},
        {"pinTransferencia",                "Überweisungs PIN"},
        {"transferencia",                   "Überweisung"},
        {"saldoActual",                     "Aktuelles Guthaben"},
        {"informacionCuenta",               "Kontoübersicht"},
        {"titularNombre",                   "Eigentümer"},
        {"cerrarSesion",                    "Abmelden"},
        {"informe",                         "Kontoberricht"},
        {"ayuda",                           "Hilfe"},
        {"pagarServicio",                   "Dienstleistung bezahlen"},
        {"tituloVentana",                   "Simulation Web Banking"},
        {"bienvenidoNombre",                "Wilkommen {0}"},
        {"saldo",                           "Guthaben"},
        {"queDeseaHacer",                   "Was möchten sie tun?"}
    };
    @Override
    protected Object[][] getContents() {
        return CONTENTS;
    }
    
}
