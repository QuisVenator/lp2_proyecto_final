
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
        {"icono",                           "Icono"},
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
        {"queDeseaHacer",                   "¿Qué desea hacer?"},
        {"idiomaNombre",                    "Español"},
        
        {"loginNoExitoso",                  "Iniciar sesión no posible"},
        {"loginErrorMessage",               "No se pudo iniciar sesión. Por favor verificar datos e intentar nuevamente."},
        {"dbErrorTitulo",                   "Error en base de datos"}, 
        {"dbErrorMensaje",                  "Acción no pudo ser realizada. Verifique sus datos y pruebe de nuevo"},
        {"pinTransferenciaNoValido",        "Pin de transferencia no se pudo confirmar! Por favor reintentar."},
        {"pinNoValidoTitulo",               "Pin incorrecto"},
        {"saldoInsuficienteTitulo",         "Saldo Insuficiente"},
        {"saldoInsuficiente",               "Saldo insuficiente para realizar la transaccion"},
        {"servicioPagadoTitulo",            "Servicio Pagado"},
        {"servicioPagado",                  "Usted ha pagado el servicio \"{2}\" con su cuenta número {1} por el monto de {3}.\nFecha y hora: {0}"},
        {"transferenciaRealizadaTitulo",    "Transferencia Realizada"},
        {"transferenciaRealizada",          "Usted ha transferido {3}\nDe: {1}\nA: {2}\nFecha y hora: {0}"},
        {"pdfOpenErrorTitulo",              "Error al abrid PDF"},
        {"pdfOpenError",                    "No se pudo abrir el tutorial. Por favor asegurese que tenga instalada una aplicacion para leer archivos .pdf."},
        {"inputNoCorrectoTitulo",           "Entrada erronea"},
        {"inputNoCorrecto",                 "Nuestro sistema no pudo interpretar una de sus entradas. Porfavor verifique todos los campos para intentar de nuevo."},
        {"cuentaNoEncontradaTitulo",        "Cuenta no encontrada"},
        {"cuentaNoEncontrada",              "No pudimos encontrar esta cuenta, por favor verificar todos los campos."},
        {"tutorialArchivo",                 "FAQ_Lp2_ES.pdf"},
        {"depositoConfirmacionTitulo",      "Depósito realizado"},
        {"depositoConfirmacion",            "Usted ha depositado {2}\nEn cuenta: {1}\nFecha y hora: {0}"},
        {"desbloqueoConfirmacionTitulo",    "Cuenta desbloqueada"},
        {"desbloqueoConfirmacion",          "Se ha desbloqueado la cuenta {0}"},
        {"cuentaYaExisteTitulo",            "Cuenta ya existe"},
        {"cuentaYaExiste",                  "La cuenta no se agregó, porque ya existe!"},
        {"servicioYaExisteTitulo",          "Servicio ya existe"},
        {"servicioYaExiste",                "El servicio no se agregó, porque ya existe!"},
        {"servicioNoExisteTitulo",          "Servicio no existe"},
        {"servicioNoExiste",                "No pudimos encontrar este servicio"},
        {"cuentaNoExisteTitulo",            "Cuenta no existe"},
        {"cuentaNoExiste",                  "No pudimos encontrar esta cuenta"},
        {"borrarPropioErrorTitulo",         "Borrando propia cuenta"},
        {"borrarPropioError",               "No esta permitido borrar su propia cuenta!"},
        {"cuentaCreadaTitulo",              "Cuenta creada"},
        {"cuentaCreada",                    "Se creó exitosamente la cuenta.\nNro. Cuenta: {0}\nPIN: {1}\nPin Transferencia: {2}"},
        {"servicioCreadoTitulo",            "Servicio creado"},
        {"servicioCreado",                  "Se creó exitosamente el servicio.\nNro. Cuenta: {0}\nNombre: {1}\nMonto: {2}"},
        {"cuentaEliminadaTitulo",           "Cuenta eliminada"},
        {"cuentaEliminada",                 "Se ha eliminada la cuenta {0}"},
        {"servicioEliminadoTitulo",         "Serviciio eliminado"},
        {"servicioEliminado",               "Se ha eliminado el servicio {0}"},
        {"cuentaBloqueadaError",            "Su cuenta se encuentra bloqueada"}
    };
    @Override
    protected Object[][] getContents() {
        return CONTENTS;
    }
    
}