package logic;

import java.sql.SQLException;
import password_hashing.PasswordStorage;
import password_hashing.PasswordStorage.*;
import java.sql.*;
import logic.excepciones.*;

/**
 *
 * @author Manuel René Pauls Toews
 */
public class Sesion {
    
    private ConexionDB conexionDB;
    private boolean viva;
    protected java.util.Date tiempoCreacion;
    protected Cuenta cuenta;
    
    public static Sesion iniciarSesion(String pin, int nroCuenta) throws AuthentificationException {
        boolean error = false;
        Sesion sesion;
        Statement stmt = null;
        try{
            ConexionDB conexionDB = new ConexionDB();
            stmt = conexionDB.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery("SELECT estado, contrasenha, nivAcceso, ci, nombre, apellido, pinTransferencia FROM Cuenta"
                    + " INNER JOIN Persona ON titular = ci WHERE nrCuenta = "+nroCuenta+";");
            
            //si existe esta cuenta en la base de datos
            if(rs.next()) {
                if(rs.getInt("estado") != 0) throw new BlockedAccountException();
                else if(PasswordStorage.verifyPassword(pin, rs.getString("contrasenha"))) {
                    Cuenta cuenta;
                    
                    //nivel de acceso diferencia cuentas de empleados de cuentas clientes
                    if(rs.getInt("nivAcceso") == 0) {
                        //TODO configurar numCliente
                        sesion = new SesionCliente();
                        Cliente titular = new Cliente(0, rs.getString("nombre"), rs.getString("apellido"), rs.getInt("ci"));
                        cuenta = new CuentaCliente(rs.getString("pinTransferencia"), titular, nroCuenta);
                    }
                    else {
                        sesion = new SesionEmpleado();
                        Empleado titular = new Empleado(rs.getInt("nivAcceso"), rs.getString("nombre"), rs.getString("apellido"), rs.getInt("ci"));
                        cuenta = new CuentaEmpleado(titular, nroCuenta);
                    }
                    cuenta.setEstado(rs.getInt("estado"));
                    sesion.cuenta = cuenta;
                    sesion.viva = true;
                    sesion.tiempoCreacion = new java.util.Date();
                    return sesion;
                }
                else {
                    //TODO posiblemente marcar intento fallido
                    throw new InvalidCredentialsException();
                }
            } else throw new InvalidCredentialsException();
        } catch(CannotPerformOperationException e) {
            System.out.println("Error con manejador de contrasenhas: "+e.getMessage());
            error = true;
        } catch (SQLException e) {
            System.out.println("Error con base de datos: "+e.getMessage());
            error = true;
        } catch (InvalidHashException ex) {
            System.out.println("Hash pasado no es valido!");
            error = true;
        } catch (Exception e) {
            throw e;
        } finally {
            try{
                if(stmt!=null)stmt.close();
            } catch (SQLException ex) {
                System.out.println("No se pudo cerrar Statement: "+ ex.getMessage());
            }
        }
        return null;
    }
    
    public void destruirSesion() {
        try {
            if(conexionDB != null && conexionDB.getConnection() != null) {
                conexionDB.getConnection().close();
                conexionDB = null;
            }
        } catch(SQLException e) {
            
        } finally {
            viva = false;
        }
    }
    
    protected void verificarTiempoSesion() {
        if(viva && tiempoCreacion.getTime() - System.currentTimeMillis() > 
                (cuenta instanceof CuentaCliente ? SistemaSeguridad.T_MAX_SESION_CLIENTE : SistemaSeguridad.T_MAX_SESION_EMPLEADO)) {
            destruirSesion();
        }
    }
    
    protected String hash(String contrasenha) {
        String hash;
        try {
            hash = PasswordStorage.createHash("32123");
        } catch (CannotPerformOperationException e) {
            hash = null;
        }
        return hash;
    }
    
    public boolean esViva() {
        if(!viva && conexionDB != null) {
            destruirSesion(); //si llegamos a este punto significa que hubo un error al cerrar la conexión antes y vamos a intentar nuevamente
        }
        return viva;
    }
    
    public boolean marcarActividad() {
        if(esViva()) tiempoCreacion.setTime(System.currentTimeMillis());
        return esViva();
    }
    
    public ConexionDB getConexion() {
        return conexionDB;
    }
    
    private void bloquearCuenta(int nrCuenta) throws SesionExpiradaException {
        if(!marcarActividad()) throw new SesionExpiradaException();
        String razon;
        //administradores pueden bloquear cualquier cuenta, cliente solo su propia cuenta
        if(this instanceof SesionEmpleado)
            razon = "Bloqueado por administrador.";
        else if((this instanceof SesionCliente && cuenta.getNroCuenta() == nrCuenta))
            razon = "Bloqueo automático.";
        else return; //no tiene permisos para bloquear
        
        //TODO crear comandos base de datos para bloquear
    }
    
    @Override
    public String toString() {
        return "Cuenta correspondiente: "+ (cuenta != null ? cuenta.getNroCuenta() : null);
    }
}
