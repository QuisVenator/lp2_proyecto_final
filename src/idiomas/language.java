
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
        {"queDeseaHacer",                   "Qué desea hacer?"},
        {"idiomaNombre",                    "Español"},
        
        {"loginNoExitoso",                  "Iniciar sesión no posible"},
        {"loginErrorMessage",               "Cuenta no encontrada. Por favor verificar datos e intentar nuevamente."},
        {"dbErrorTitulo",                   "Error en base de datos"}, 
        {"dbErrorMensaje",                  "Acción no pudo ser realizada. Verifique sus datos y pruebe de nuevo!"},
        {"pinTransferenciaNoValido",        "Pin de transferencia no se pudo confirmar! Por favor reintentar."},
        {"pinNoValidoTitulo",               "Pin incorrecto"},
        {"saldoInsuficienteTitulo",         "Saldo Insuficiente"},
        {"saldoInsuficiente",               "Saldo insuficiente para realizar la transaccion"},
        {"servicioPagadoTitulo",            "Servicio Pagado"},
        {"servicioPagado",                  "El servicio se ha pagado exitosamente!"},
        {"transferenciaRealizadaTitulo",    "Transferencia Realizada"},
        {"transferenciaRealizada",          "La transferencia fue realizada exitosamente!"},
        {"pdfOpenErrorTitulo",              "Error al abrid PDF"},
        {"pdfOpenError",                    "No se pudo abrir el tutorial. Por favor asegurese que tenga una aplicacion para leer .pdf instalado."},
        {"inputNoCorrectoTitulo",           "Entrada erronea"},
        {"inputNoCorrecto",                 "Nuestro sistema no pudo interpretar una de sus entradas. Porfavor verifique todos los campos para intentar de nuevo."},
        {"cuentaNoEncontradaTitulo",        "Cuenta no encontrada"},
        {"cuentaNoEncontrada",              "No pudimos encontrar esta cuenta, por favor verificar todos los campos."}
    };
    @Override
    protected Object[][] getContents() {
        return CONTENTS;
    }
    
}