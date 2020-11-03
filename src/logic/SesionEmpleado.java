
package logic;

import java.awt.event.ActionEvent;
import java.security.SecureRandom;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import logic.excepciones.SesionExpiradaException;
import password_hashing.PasswordStorage;
import password_hashing.PasswordStorage.CannotPerformOperationException;
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
        int cuentaRes;
        if((cuentaRes = getCuentaCliente(nrCuenta)) != 1) return cuentaRes;
        
        String queryString = "UPDATE Cuenta SET saldo = saldo + ? WHERE nrCuenta = ?;";
        try(PreparedStatement stmt = getConexion().getConnection().prepareStatement(queryString)) {
            stmt.setDouble(1, monto);
            stmt.setInt(2, nrCuenta);
            int afectado = stmt.executeUpdate();
            if(afectado == 1) {
                //primero guardamos evidencia de la transaccion en la base de datos
                Transferencia transferencia = new Transferencia(new CuentaCliente("", null, cuenta.getNroCuenta()),
                        new CuentaCliente("", null, nrCuenta), Transferencia.DEPOSITO);
                transferencia.setSesion(this);
                transferencia.guardar();
                
                Object[] detalles = {new Date(), nrCuenta, monto};
                Mensaje.crearMensajeConfirmacion("depositoConfirmacionTitulo", "depositoConfirmacion", detalles);
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
    
    public int desbloquearCuenta(int nrCuenta) throws SesionExpiradaException {
        if(!marcarActividad()){ //marcar actividad para que no se cierre la sesión por inactividad y verificar si no se cerró antes
            throw new SesionExpiradaException();
        }

        //verificar cuenta destino existe y es cuenta cliente
        int cuentaRes;
        if((cuentaRes = getCuentaCliente(nrCuenta)) != 1) return cuentaRes;
        
        String queryString = "UPDATE Cuenta SET estado =  0 WHERE nrCuenta = ?;";
        try(PreparedStatement stmt = getConexion().getConnection().prepareStatement(queryString)) {
            stmt.setInt(1, nrCuenta);
            int afectado = stmt.executeUpdate();
            if(afectado == 1) {
                Log log = new Log("acción admin", cuenta, "Desbloqueado cuenta "+nrCuenta);
                log.setSesion(this);
                log.guardar();
                
                Object[] detalles = {nrCuenta};
                Mensaje.crearMensajeConfirmacion("desbloqueoConfirmacionTitulo", "desbloqueoConfirmacion", detalles);
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
    
    public int agregarCuenta(int doc, int niv) throws SesionExpiradaException{
        if(!marcarActividad()){ //marcar actividad para que no se cierre la sesión por inactividad y verificar si no se cerró antes
            throw new SesionExpiradaException();
        }
        
        try {
            SecureRandom random = new SecureRandom();
            char[] pin = new char[4];
            for(int i = 0; i < pin.length; i++) {
                pin[i] = Integer.toString(random.nextInt(10)).charAt(0);
            }
            String passHash = PasswordStorage.createHash(pin);
            
            String query;
            String transHash = null;
            char[] pinTrans = null;
            if(niv == 0){
                pinTrans = new char[5];
                for(int i = 0; i < pinTrans.length; i++) {
                    pinTrans[i] = Integer.toString(random.nextInt(10)).charAt(0);
                }
                transHash = PasswordStorage.createHash(pinTrans);
                query = "INSERT INTO Cuenta (titular, contrasenha, pinTransferencia, saldo) VALUES (?, ?, ?, 0);";
            } else {
                query = "INSERT INTO Cuenta (titular, contrasenha, saldo) VALUES (?, ?, 0);";
            }
            
            PreparedStatement stmt = getConexion().getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            if(niv == 0) stmt.setString(3, transHash);
            stmt.setString(2, passHash);
            stmt.setInt(1, doc);
            
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            int nroCuenta = rs.getInt(1);
            stmt.close();
            
            Object[] informacion = {nroCuenta, new String(pin), pinTrans != null ? new String(pinTrans) : "No tiene"};
            Mensaje.crearMensajeConfirmacion("cuentaCreadaTitulo", "cuentaCreada", informacion);
            return 0;
        } catch (CannotPerformOperationException | SQLException ex) {
            Mensaje.crearMensajeError("dbErrorTitulo", "dbErrorMensaje");
            return -3;
        }
    }
    
    public int agregarServicio(Servicio servicio) throws SesionExpiradaException{
        if(!marcarActividad()){ //marcar actividad para que no se cierre la sesión por inactividad y verificar si no se cerró antes
            throw new SesionExpiradaException();
        }
        
        //verificar que servicio no existe todavía
        try(PreparedStatement stmt = getConexion().getConnection().prepareStatement("SELECT nombre FROM Servicio WHERE nombre = ?;")) {
            stmt.setString(1, servicio.getNombre());
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                Mensaje.crearMensajeError("servicioYaExisteTitulo", "servicioYaExiste");
                return -2;
            }
        } catch(SQLException ex) {
            Mensaje.crearMensajeError("dbErrorTitulo", "dbErrorMensaje");
            return -3;
        }
        servicio.setSesion(this);
        return servicio.guardar();
    }
    
    public int eliminarCuenta(Cuenta cuenta) throws SesionExpiradaException{
        if(!marcarActividad()){ //marcar actividad para que no se cierre la sesión por inactividad y verificar si no se cerró antes
            throw new SesionExpiradaException();
        }
        
        //no esta permitido borrar propia cuenta
        if(this.cuenta.getNroCuenta() == cuenta.getNroCuenta()) {
            Mensaje.crearMensajeError("borrarPropioErrorTitulo", "borrarPropioError");
            return -1;
        }
        
        //eliminar cuenta
        try(PreparedStatement stmt = getConexion().getConnection().prepareStatement("DELETE FROM Cuenta WHERE nrCuenta = ?;")) {
            stmt.setInt(1, cuenta.getNroCuenta());
            int cantBorrado = stmt.executeUpdate();
            
            if(cantBorrado == 0) { //cuenta nunca existía
                Mensaje.crearMensajeError("cuentaNoExisteTitulo", "cuentaNoExiste");
                return -2;
            }
            Log log = new Log("acción admin", this.cuenta, "borrado de cuenta");
            log.setSesion(this);
            log.guardar();
            
            Mensaje.crearMensajeConfirmacion("cuentaEliminadaTitulo", "cuentaEliminada", new Object[] {cuenta.getNroCuenta()});
        } catch(SQLException ex) {
            Mensaje.crearMensajeError("dbErrorTitulo", "dbErrorMensaje");
            return -3;
        }
        return 0;
    }
    
    public int eliminarServicio(Servicio servicio) throws SesionExpiradaException{
        if(!marcarActividad()){ //marcar actividad para que no se cierre la sesión por inactividad y verificar si no se cerró antes
            throw new SesionExpiradaException();
        }
        
        //eliminar servicio
        try(PreparedStatement stmt = getConexion().getConnection().prepareStatement("DELETE FROM Servicio WHERE nombre = ?;")) {
            stmt.setString(1, servicio.getNombre());
            int cantBorrado = stmt.executeUpdate();
            
            if(cantBorrado == 0) { //servicio nunca existía
                Mensaje.crearMensajeError("servicioNoExisteTitulo", "servicioNoExiste");
                return -2;
            }
            Log log = new Log("acción admin", cuenta, "borrado de servicio");
            log.setSesion(this);
            log.guardar();
            
            Mensaje.crearMensajeConfirmacion("servicioEliminadoTitulo", "servicioEliminado", new Object[]{servicio.getNombre()});
        } catch(SQLException ex) {
            Mensaje.crearMensajeError("dbErrorTitulo", "dbErrorMensaje");
            return -3;
        }
        return 0;
    }
    
    public int getCuentaCliente(int nrCuenta) {
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
                return -2;
            }
        } catch(SQLException ex) {
            Mensaje.crearMensajeError("dbErrorTitulo", "dbErrorMensaje");
            return -3;
        }
        return 1;
    }
    
    public int agregarPersona(int documento, String nombre, String apellido, String telefono, String direccion, String correo, int acceso) throws SesionExpiradaException {
        if(!marcarActividad()){ //marcar actividad para que no se cierre la sesión por inactividad y verificar si no se cerró antes
            throw new SesionExpiradaException();
        }
        
        //no agregamos si ya existe
        try(PreparedStatement stmt = getConexion().getConnection().prepareStatement("SELECT ci FROM Persona WHERE ci = ?;")) {
            stmt.setInt(1, documento);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                return 0;
            }
        } catch(SQLException ex) {
            Mensaje.crearMensajeError("dbErrorTitulo", "dbErrorMensaje");
            return -3;
        }
        
        String query = "INSERT INTO Persona (nombre, apellido, correo, numeroTel, direccion, ci, nivAcceso) VALUES "
                + "(?, ?, ?, ?, ?, ?, ?);";
        try(PreparedStatement stmt = getConexion().getConnection().prepareStatement(query)) {
            stmt.setString(1, nombre);
            stmt.setString(2, apellido);
            stmt.setString(3, correo);
            stmt.setString(4, telefono);
            stmt.setString(5, direccion);
            stmt.setInt(6, documento);
            stmt.setInt(7, acceso);
            stmt.executeQuery();
            return 0;
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
