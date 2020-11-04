
package primer_final.logic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import primer_final.idiomas.ui.Mensaje;

public class Empleado extends Persona {
    private int nivAcceso; //actualmente se usa solo nivel 0 y 1, pero en un futuro podría ser utilizado para distinguir entre categorías de Empleados como Admin y Soporte
    
    public Empleado(int nivel, String nombre, String apellido, int ci) {
        super(nombre, apellido, ci);
    }
    
    public int getNivelAcceso() {
        return nivAcceso;
    }
    public void setNivelAcceso(int nivel) {
        if(nivel >= nivAcceso) return; //no se permite inrementar nivel de acceso, en estos casos se debe crear nueva cuenta
        if(nivel <= 0) return; //no existen niveles negativos y no se permite cambiar admin a nivel cliente
        nivAcceso = nivel;
    }
    
    
    @Override
    public int guardar() {
        ConexionDB dbc = getSesion().getConexion();
        Connection conn = dbc.getConnection();
        String queryString = "INSERT INTO Persona (nombre, apellido, correo, numeroTel, direccion, ci, nivAcceso) VALUES (?, ?, ?, ?, ?, ?, ?);";
        try(PreparedStatement stmt = conn.prepareStatement(queryString)) {
            stmt.setString(1, getNombre());
            stmt.setString(2, getApellido());
            stmt.setString(3, getCorreo());
            stmt.setString(4, getNumTel());
            stmt.setString(5, getDireccion());
            stmt.setInt(6, getCI());
            stmt.setInt(7, nivAcceso);
            stmt.execute();
            return 0;
        } catch(SQLException e) {
            Mensaje.crearMensajeError("dbErrorTitulo", "dbErrorMensaje");
            return -1;
        }
    }
}
