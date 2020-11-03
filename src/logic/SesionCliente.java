
package logic;

import ui.Reporte;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import logic.excepciones.SesionExpiradaException;
import password_hashing.PasswordStorage;
import ui.Mensaje;

public class SesionCliente extends Sesion {
    
    @Override
    protected void verificarTiempoSesion() {
        long diff = System.currentTimeMillis() - tiempoCreacion.getTime();
        if(viva && System.currentTimeMillis() - tiempoCreacion.getTime() > SistemaSeguridad.T_MAX_SESION_CLIENTE) {
            destruirSesion();
            viva = false;
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
    
    /**
     * Obtiene la lista de servicio que existen en la base de datos
     * @return 
     * @throws SesionExpiradaException 
     */
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
    public int pagarServicio(Servicio servicio, String pinTransferencia) throws SesionExpiradaException {
        if(!marcarActividad()){ //marcar actividad para que no se cierre la sesión por inactividad y verificar si no se cerró antes
            throw new SesionExpiradaException();
        }
        
        try {
            if(PasswordStorage.verifyPassword(pinTransferencia, ((CuentaCliente)getCuenta()).getTransHash())) {
                ((CuentaCliente)getCuenta()).setSaldo(obtenerSaldo()); //actualizar saldo disponible
                
                Transferencia transferencia = new Transferencia(((CuentaCliente)getCuenta()), new CuentaCliente("", null, servicio.getCuentaNr()), 
                        Transferencia.SERVICIO, servicio.getMonto());
                transferencia.setSesion(this);
                if(transferencia.efectuar() == 0) {
                    transferencia.guardar();
                    Object[] detalles = {new Date(), cuenta.getNroCuenta(), servicio.getNombre(), servicio.getMonto()};
                    Mensaje.crearMensajeConfirmacion("servicioPagadoTitulo", "servicioPagado", detalles);
                    return 0;
                }
                return -1;
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
                
                Transferencia transferencia = new Transferencia(((CuentaCliente)getCuenta()), new CuentaCliente("", null, nrCuenta), Transferencia.ENTRE_CUENTAS, monto);
                transferencia.setSesion(this);
                if(transferencia.efectuar() == 0) {
                    transferencia.guardar();
                    Object[] detalles = {new Date(), cuenta.getNroCuenta(), nrCuenta, monto};
                    Mensaje.crearMensajeConfirmacion("transferenciaRealizadaTitulo", "transferenciaRealizada", detalles);
                    return 0;
                }else return -1;
            } else {
                Mensaje.crearMensajeError("pinNoValidoTitulo", "pinTransferenciaNoValido");
                return -2;
            }
        } catch (PasswordStorage.CannotPerformOperationException | PasswordStorage.InvalidHashException ex) {
            Mensaje.crearMensajeError("dbErrorTitulo", "dbErrorMensaje");
            return -3;
        }
    }
    
    /**
     * Genera un reporte en formato de PDF
     * @param report el pdf a llenar con datos
     * @return 0 en caso de éxito, código error en otro caso
     * @throws SesionExpiradaException 
     */
    public int generarReporte(Reporte report) throws SesionExpiradaException {
        if(!marcarActividad()){ //marcar actividad para que no se cierre la sesión por inactividad y verificar si no se cerró antes
            throw new SesionExpiradaException();
        }
        
        report.addTituloPrincipal(Integer.toString(getCuenta().getNroCuenta()));
        report.addDatosCliente(getCuenta().getTitular().getNombreCompleto(), Integer.toString(getCuenta().getNroCuenta()), String.format("%.2f", obtenerSaldo()));
        
        LinkedList<LinkedList<String>> transferencias = new LinkedList<>();
        String[] titulos = {
            report.app.getLanguage().getString("id"),
            report.app.getLanguage().getString("cuenta"),
            report.app.getLanguage().getString("monto")
        };
        
        //traer transferencias de esta cuenta a otra de base de datos
        try(PreparedStatement stmt = getConexion().getConnection().prepareStatement("SELECT * FROM Transferencia WHERE envia = ? AND tipo = ?;")) {
            stmt.setInt(1, getCuenta().getNroCuenta());
            stmt.setInt(2, Transferencia.ENTRE_CUENTAS);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                LinkedList<String> linea = new LinkedList<>();
                linea.add(Integer.toString(rs.getInt("id")));
                linea.add(Integer.toString(rs.getInt("recibe")));
                linea.add(String.format("%.2f", rs.getDouble("monto")));
                transferencias.add(linea);
            }
        } catch(SQLException ex) {
            Mensaje.crearMensajeError("dbErrorTitulo", "dbErrorMensaje");
            report.cerrar();
            return -3;
        }
        if(transferencias.size() > 0)
            report.addTabla("transferenciasRealizadas", titulos, transferencias);
        
        
        //traer transferencias de otra cuenta a esta de base de datos
        transferencias = new LinkedList<>();
        try(PreparedStatement stmt = getConexion().getConnection().prepareStatement("SELECT * FROM Transferencia WHERE recibe = ? AND tipo = ?;")) {
            stmt.setInt(1, getCuenta().getNroCuenta());
            stmt.setInt(2, Transferencia.ENTRE_CUENTAS);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                LinkedList<String> linea = new LinkedList<>();
                linea.add(Integer.toString(rs.getInt("id")));
                linea.add(Integer.toString(rs.getInt("envia")));
                linea.add(String.format("%.2f", rs.getDouble("monto")));
                transferencias.add(linea);
            }
        } catch(SQLException ex) {
            Mensaje.crearMensajeError("dbErrorTitulo", "dbErrorMensaje");
            report.cerrar();
            return -3;
        }
        if(transferencias.size() > 0)
            report.addTabla("transferenciasRecibidas", titulos, transferencias);
        
        //traer pagos de servicio realizados con esta cuenta de base de datos
        //actualizar títulos de columnas
        titulos = new String[]{
            report.app.getLanguage().getString("id"),
            report.app.getLanguage().getString("monto")
        };
        transferencias = new LinkedList<>();
        try(PreparedStatement stmt = getConexion().getConnection().prepareStatement("SELECT * FROM Transferencia WHERE envia = ? AND tipo = ?;")) {
            stmt.setInt(1, getCuenta().getNroCuenta());
            stmt.setInt(2, Transferencia.SERVICIO);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                LinkedList<String> linea = new LinkedList<>();
                linea.add(Integer.toString(rs.getInt("id")));
                linea.add(String.format("%.2f", rs.getDouble("monto")));
                transferencias.add(linea);
            }
        } catch(SQLException ex) {
            Mensaje.crearMensajeError("dbErrorTitulo", "dbErrorMensaje");
            report.cerrar();
            return -3;
        }
        
        if(transferencias.size() > 0)
            report.addTabla("transferenciasAServicios", titulos, transferencias);
        
        //traer depósitos en esta cuenta
        transferencias = new LinkedList<>();
        try(PreparedStatement stmt = getConexion().getConnection().prepareStatement("SELECT * FROM Transferencia WHERE recibe = ? AND tipo = ?;")) {
            stmt.setInt(1, getCuenta().getNroCuenta());
            stmt.setInt(2, Transferencia.DEPOSITO);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                LinkedList<String> linea = new LinkedList<>();
                linea.add(Integer.toString(rs.getInt("id")));
                linea.add(String.format("%.2f", rs.getDouble("monto")));
                transferencias.add(linea);
            }
        } catch(SQLException ex) {
            Mensaje.crearMensajeError("dbErrorTitulo", "dbErrorMensaje");
            report.cerrar();
            return -3;
        }
        if(transferencias.size() > 0)
            report.addTabla("deposito", titulos, transferencias);
        
        report.cerrar();
        
        return 0;
    }
}
