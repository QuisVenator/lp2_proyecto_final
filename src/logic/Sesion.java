package logic;

import java.awt.event.ActionListener;
import java.sql.SQLException;
import password_hashing.PasswordStorage;
import password_hashing.PasswordStorage.*;
import java.sql.*;
import java.util.HashMap;
import logic.excepciones.*;

/**
 *
 * @author Manuel René Pauls Toews
 */
public abstract class Sesion {
    
    private ConexionDB conexionDB;
    boolean viva;
    protected java.util.Date tiempoCreacion;
    protected Cuenta cuenta;
    private static final HashMap<Integer, Integer> INTENTOS_FALLIDOS = new HashMap<Integer, Integer>();
    
    public static Sesion iniciarSesion(String pin, int nroCuenta) throws AuthentificationException {
        boolean error = false;
        Sesion sesion;
        Statement stmt = null;
        try{
            ConexionDB conexionDB = new ConexionDB();
            stmt = conexionDB.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery("SELECT estado, contrasenha, nivAcceso, ci, nombre, apellido, pinTransferencia, saldo FROM Cuenta"
                    + " INNER JOIN Persona ON titular = ci WHERE nrCuenta = "+nroCuenta+";");
            
            //si existe esta cuenta en la base de datos
            if(rs.next()) {
                if(rs.getInt("estado") != 0) throw new BlockedAccountException();
                else if(PasswordStorage.verifyPassword(pin, rs.getString("contrasenha"))) {
                    Cuenta cuenta;
                    
                    //cuentas empleados no tienen pin transferencia
                    rs.getString("pinTransferencia");
                    if(!rs.wasNull()) {
                        //TODO configurar numCliente
                        sesion = new SesionCliente();
                        Cliente titular = new Cliente(rs.getString("nombre"), rs.getString("apellido"), rs.getInt("ci"));
                        cuenta = new CuentaCliente(rs.getString("pinTransferencia"), titular, nroCuenta);
                    }
                    else {
                        sesion = new SesionEmpleado();
                        Empleado titular = new Empleado(rs.getInt("nivAcceso"), rs.getString("nombre"), rs.getString("apellido"), rs.getInt("ci"));
                        cuenta = new CuentaEmpleado(titular, nroCuenta);
                    }
                    cuenta.setEstado(rs.getInt("estado"));
                    sesion.conexionDB = conexionDB;
                    sesion.cuenta = cuenta;
                    sesion.viva = true;
                    sesion.tiempoCreacion = new java.util.Date();
                    return sesion;
                }
                else {
                    //bloquear si se hicieron más de 5 intentos
                    if(INTENTOS_FALLIDOS.containsKey(nroCuenta))
                        INTENTOS_FALLIDOS.put(nroCuenta, INTENTOS_FALLIDOS.get(nroCuenta) + 1);
                    else
                        INTENTOS_FALLIDOS.put(nroCuenta, 1);
                    if(INTENTOS_FALLIDOS.get(nroCuenta) >= 5) {
                        bloquearCuenta(nroCuenta, conexionDB);
                    }
                    
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
        } catch (BlockedAccountException | InvalidCredentialsException e) {
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
    
    protected abstract void verificarTiempoSesion();
    
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
        verificarTiempoSesion();
        return viva;
    }
    
    public boolean marcarActividad() {
        if(esViva()) tiempoCreacion.setTime(System.currentTimeMillis());
        return esViva();
    }
    
    public ConexionDB getConexion() {
        return conexionDB;
    }
    
    public Cuenta getCuenta() {
        return cuenta;
    }
    
    private static void bloquearCuenta(int nrCuenta, ConexionDB conexionDB) throws SQLException {
        String query = "UPDATE Cuenta SET estado = 1 WHERE nrCuenta = ?";
        PreparedStatement stmt = conexionDB.getConnection().prepareStatement(query);
        stmt.setInt(1, nrCuenta);
        stmt.executeUpdate();
    }
    
    @Override
    public String toString() {
        return "Cuenta correspondiente: "+ (cuenta != null ? cuenta.getNroCuenta() : null);
    }
}