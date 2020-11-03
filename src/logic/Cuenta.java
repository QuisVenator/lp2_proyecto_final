
package logic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import ui.Mensaje;

/**
 * Clase abstracta con implementaciónes comunes en CuentaCliente y CuentaEmpleado
 */
public abstract class Cuenta extends DBObject {
    private int nrCuenta;
    private Persona titular;
    private String contrasenhaHash;
    private int estado;
    
    public Cuenta(Persona titular, String contrasenha, int nroCuenta) {
        this.nrCuenta = nroCuenta;
        this.contrasenhaHash = contrasenha;
        this.titular = titular;
    }
    public Cuenta(Persona titular, String contrasenha) {
        this.contrasenhaHash = contrasenha;
        this.titular = titular;
    }
    public String getAccHash() {
        return contrasenhaHash;
    }
    public Persona getTitular() {
        return titular;
    }
    public int getEstado() {
        return estado;
    }
    public int setEstado(int nuevoEstado) {
        estado = nuevoEstado;
        return estado;
    }
    public int getNroCuenta() {
        return nrCuenta;
    }
    
    @Override
    public int guardar() {
        ConexionDB dbc = getSesion().getConexion();
        Connection conn = dbc.getConnection();
        String queryString = "INSERT INTO Cuenta (titular, contrasenha) VALUES (?, ?);";
        try(PreparedStatement stmt = conn.prepareStatement(queryString)) {
            stmt.setString(2, contrasenhaHash);
            stmt.setInt(1, titular.getCI());
            stmt.execute();
            return 0;
        } catch(SQLException e) {
            Mensaje.crearMensajeError("dbErrorTitulo", "dbErrorMensaje");
            return -1;
        }
    }
}
