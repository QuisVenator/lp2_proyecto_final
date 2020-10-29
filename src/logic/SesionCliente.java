
package logic;

import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import logic.excepciones.SesionExpiradaException;
import password_hashing.PasswordStorage;
import ui.Mensaje;

/**
 *
 * @author Manuel René Pauls Toews
 */
public class SesionCliente extends Sesion {
    
    @Override
    protected void verificarTiempoSesion() {
        if(viva && tiempoCreacion.getTime() - System.currentTimeMillis() > SistemaSeguridad.T_MAX_SESION_CLIENTE) {
            destruirSesion();
        }
    }

    public double obtenerSaldo() throws SesionExpiradaException {
        if(!marcarActividad()){ //marcar actividad para que no se cierre la sesión por inactividad y verificar si no se cerró antes
            throw new SesionExpiradaException();
        }
        
        //actualizar de base de datos
        Connection conn = getConexion().getConnection();
        String queryString = "SELECT saldo FROM Cuenta WHERE nrCuenta = ?";
        try(PreparedStatement stmt = conn.prepareStatement(queryString)) {
            stmt.setInt(1, getCuenta().getNroCuenta());
            ResultSet rs = stmt.executeQuery();
            
            //guardar actividad en log
            Log log = new Log("lectura", getCuenta(), "saldo");
            log.setSesion(this);
            log.guardar();
            
            CuentaCliente cuentaActual = ((CuentaCliente)getCuenta());
            cuentaActual.setSaldo(rs.getDouble("saldo"));
            return cuentaActual.getSaldo();
            
        } catch(SQLException e) {
            Mensaje.crearMensajeError("dbErrorTitulo", "dbErrorMensaje");
            return 0;
        }
    }
    
    public List<Servicio> obtenerListaServicio() throws SesionExpiradaException {
        if(!marcarActividad()){ //marcar actividad para que no se cierre la sesión por inactividad y verificar si no se cerró antes
            throw new SesionExpiradaException();
        }
        
        Connection conn = getConexion().getConnection();
        String queryString = "SELECT nombre, descripcion, iconoPath, monto, cuenta FROM Servicio;";
        List<Servicio> servicios = new ArrayList<>();
        try(PreparedStatement stmt = conn.prepareStatement(queryString)) {
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()) {
                Servicio servicio = new Servicio(rs.getInt("cuenta"), rs.getInt("monto"), rs.getString("nombre"), rs.getString("descripcion"), rs.getString("iconoPath"));
                servicios.add(servicio);
            }
            
            //guardar actividad en log
            Log log = new Log("lectura", getCuenta(), "servicios");
            log.setSesion(this);
            log.guardar();
            
            return servicios;
            
        } catch(SQLException e) {
            Mensaje.crearMensajeError("dbErrorTitulo", "dbErrorMensaje");
            return servicios;
        }
    }
    public int pagarServicio(Servicio servicio, double monto, String pinTransferencia) throws SesionExpiradaException {
        if(!marcarActividad()){ //marcar actividad para que no se cierre la sesión por inactividad y verificar si no se cerró antes
            throw new SesionExpiradaException();
        }
        
        try {
            if(PasswordStorage.verifyPassword(pinTransferencia, ((CuentaCliente)getCuenta()).getTransHash())) {
                ((CuentaCliente)getCuenta()).setSaldo(obtenerSaldo()); //actualizar saldo disponible
                
                Transferencia transferencia = new Transferencia(((CuentaCliente)getCuenta()), new CuentaCliente("", null, servicio.getCuentaNr()), 
                        Transferencia.SERVICIO, servicio.getMonto());
                transferencia.setSesion(this);
                transferencia.efectuar();
                transferencia.guardar();
                Mensaje.crearMensajeConfirmacion("servicioPagadoTitulo", "servicioPagado");
                return 0;
            } else {
                Mensaje.crearMensajeError("pinNoValidoTitulo", "pinTransferenciaNoValido");
                return -2;
            }
        } catch (PasswordStorage.CannotPerformOperationException | PasswordStorage.InvalidHashException ex) {
            Mensaje.crearMensajeError("dbErrorTitulo", "dbErrorMensaje");
            return -3;
        }
    }
    
    public int generarTransferencia(int nrCuenta, double monto, String pin) throws SesionExpiradaException {
        if(!marcarActividad()){ //marcar actividad para que no se cierre la sesión por inactividad y verificar si no se cerró antes
            throw new SesionExpiradaException();
        }
        
        try {
            if(PasswordStorage.verifyPassword(pin, ((CuentaCliente)getCuenta()).getTransHash())) {
                ((CuentaCliente)getCuenta()).setSaldo(obtenerSaldo()); //actualizar saldo disponible
                
                Transferencia transferencia = new Transferencia(((CuentaCliente)getCuenta()), new CuentaCliente("", null, nrCuenta), Transferencia.ENTRE_CUENTAS, monto);
                transferencia.setSesion(this);
                transferencia.efectuar();
                transferencia.guardar();
                return 0;
            } else {
                Mensaje.crearMensajeError("pinNoValidoTitulo", "pinTransferenciaNoValido");
                return -2;
            }
        } catch (PasswordStorage.CannotPerformOperationException | PasswordStorage.InvalidHashException ex) {
            Mensaje.crearMensajeError("dbErrorTitulo", "dbErrorMensaje");
            return -3;
        }
    }
}
