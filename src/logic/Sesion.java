package logic;

import java.sql.SQLException;
import password_hashing.PasswordStorage;
import password_hashing.PasswordStorage.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import logic.excepciones.*;

/**
 *
 * @author Manuel René Pauls Toews
 */
public class Sesion {
    
    private ConexionDB conexion;
    
    public static Sesion iniciarSesion(String pin, int nrCuenta) throws AuthentificationException {
        boolean error = false;
        Sesion sesion = new Sesion();
        Statement stmt = null;
        try{
            sesion.conexion = new ConexionDB();
            stmt = sesion.conexion.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery("SELECT estado, contrasenha FROM Cuenta WHERE nrCuenta = "+nrCuenta+";");
            if(rs.next()) {
                if(rs.getInt("estado") != 0) throw new BlockedAccountException();
                else if(PasswordStorage.verifyPassword(pin, rs.getString("contrasenha")))
                    return sesion;
                else throw new InvalidCredentialsException();
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
            if(error) {
                sesion.cerrar();
            }
            try{
                if(stmt!=null)stmt.close();
            } catch (SQLException ex) {
                System.out.println("No se pudo cerrar Statement: "+ ex.getMessage());
            }
        }
        return null;
    }
    
    public void cerrar() {
        try {
            if(conexion != null && conexion.getConnection() != null)
                conexion.getConnection().close();
        } catch (SQLException e) {
            System.out.println("Error cerrando ConexionDB: "+e.getMessage());
        }
    }
}
