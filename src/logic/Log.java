
package logic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import ui.Mensaje;

/**
 * Objeto usado para guardar un historial de acciones en base de datos.
 * @author pauls_1x1baaf
 */
public class Log extends DBObject {
    private String tipo, descripcion;
    private Cuenta cuenta;
    private Date hora;
    
    public Log(String tipo, Cuenta cuenta) {
        this(tipo, cuenta, "-");
    }
    public Log(String tipo, Cuenta cuenta, String descripcion) {
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.hora = new Date();
        this.cuenta = cuenta;
    }
    
    @Override
    public int guardar() {
        ConexionDB dbc = getSesion().getConexion();
        Connection conn = dbc.getConnection();
        String queryString = "INSERT INTO Log (tipo, descripcion, cuenta) VALUES (?, ?, ?);";
        try(PreparedStatement stmt = conn.prepareStatement(queryString)) {
            stmt.setString(1, tipo);
            stmt.setString(2, descripcion);
            stmt.setInt(3, cuenta.getNroCuenta());
            stmt.execute();
            return 0;
        } catch(SQLException e) {
            Mensaje.crearMensajeError("dbErrorTitulo", "dbErrorMensaje");
            return -1;
        }
    }

}
