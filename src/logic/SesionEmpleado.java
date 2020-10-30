
package logic;

import java.awt.event.ActionEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import logic.excepciones.SesionExpiradaException;
import password_hashing.PasswordStorage;
import ui.Mensaje;

/**
 *
 * @author Manuel René Pauls Toews
 */
public class SesionEmpleado extends Sesion {
    
    public int deposito(double monto, int nrCuenta) throws SesionExpiradaException {
        if(!marcarActividad()){ //marcar actividad para que no se cierre la sesión por inactividad y verificar si no se cerró antes
            throw new SesionExpiradaException();
        }

        //verificar cuenta destino existe y es cuenta cliente
        try(PreparedStatement stmt = getConexion().getConnection().prepareStatement("SELECT pinTransferencia FROM Cuenta WHERE nrCuenta = ?;")) {
            stmt.setInt(1, nrCuenta);
            ResultSet rs = stmt.executeQuery();
            if(!rs.next()) {
                Mensaje.crearMensajeError("cuentaNoEncontradaTitulo", "cuentaNoEncontrada");
                return -1;
            }
            //verificar cuenta cliente
            rs.getString("pinTransferencia");
            if(rs.wasNull()) {
                Mensaje.crearMensajeError("cuentaNoEncontradaTitulo", "cuentaNoEncontrada");
                return -1;
            }
        } catch(SQLException ex) {
            Mensaje.crearMensajeError("dbErrorTitulo", "dbErrorMensaje");
            return -3;
        }
        
        String queryString = "UPDATE Cuenta SET saldo = saldo + ? WHERE nrCuenta = ?;";
        try(PreparedStatement stmt = getConexion().getConnection().prepareStatement(queryString)) {
            stmt.setDouble(1, monto);
            stmt.setInt(2, nrCuenta);
            int afectado = stmt.executeUpdate();
            if(afectado == 1) {
                Mensaje.crearMensajeConfirmacion("depositoConfirmacionTitulo", "depositoConfirmacion");
                return 0;
            } else {
                Mensaje.crearMensajeError("dbErrorTitulo", "dbErrorMensaje");
                return -3;
            }
        } catch(SQLException ex) {
            Mensaje.crearMensajeError("dbErrorTitulo", "dbErrorMensaje");
            return -3;
        }
    }
    
    @Override
    protected void verificarTiempoSesion() {
        if(viva && tiempoCreacion.getTime() - System.currentTimeMillis() > SistemaSeguridad.T_MAX_SESION_CLIENTE) {
            destruirSesion();
        }
    }
}
