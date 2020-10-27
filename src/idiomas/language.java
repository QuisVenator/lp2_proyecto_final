package idiomas;


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
        {"terminarSimulacion",              "Terminar Simulación"},
        {"agregar",                         "Agregar"},
        {"documento",                       "Documento"},
        {"apellido",                        "Apellido"},
        {"nombre",                          "Nombre"},
        {"correo",                          "Correo"},
        {"telefono",                        "Teléfono"},
        {"acceso",                          "Acceso"},
        {"agregarCuenta",                   "Agregar Cuenta"},
        {"agregarServicio",                 "Agregar Servicio"},
        {"direccion",                       "Dirección"},
        {"monto",                           "Monto"},
        {"nombreServicio",                  "Nombre"},
        {"icono",                          "Icono"},
        {"descripcion",                     "Descripción"},
        {"deposito",                        "Depósito"},
        {"hacerDeposito",                   "Hacer Depósito"},
        {"desbloquear",                     "Desbloquear"},
        {"desbloquearCuenta",               "Desbloquear Cuenta"},
        {"eliminar",                        "Eliminar"},
        {"eliminarCuenta",                  "Eliminar Cuenta"},
        {"eliminarServicio",                "Eliminar Servicio"},
        {"nombreServicio",                  "Nombre Servicio"},
        {"serviciosDisponibles",            "Servicios Disponibles"},
        {"efectuarTransferencia",           "Efectuar Transferencia"},
        {"pinTransferencia",                "PIN Transferencia"},
        {"transferencia",                   "Transferencia"},
        {"saldoActual",                     "Saldo Actual"},
        {"informacionCuenta",               "Información Cuenta"},
        {"titularNombre",                   "Nombre"},
        {"cerrarSesion",                    "Cerrar Sesión"},
        {"informe",                         "Informe"},
        {"ayuda",                           "Ayuda"},
        {"pagarServicio",                   "Pagar Servicio"},
        {"tituloVentana",                   "Simulación Web Banking"},
        {"bienvenidoNombre",                "Bienvenido {0}"},
        {"saldo",                           "Saldo"},
        {"queDeseaHacer",                   "Qué desea hacer?"}
    };
    @Override
    protected Object[][] getContents() {
        return CONTENTS;
    }
    
}
