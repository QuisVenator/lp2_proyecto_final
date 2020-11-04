
package primer_final.logic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import primer_final.idiomas.ui.Mensaje;

public class Persona extends DBObject {
    private String nombre, apellido, correo, direccion, numeroTel;
    private int ci;
    
    public Persona(String nombre, String apellido, int ci) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.ci = ci;
    } 
    
    public String getNombre() {
        return nombre;
    }
    public String getApellido() {
        return apellido;
    }
    public String getNombreCompleto() {
        return nombre + " " + apellido;
    }
    public int getCI() {
        return ci;
    }
    public String getCorreo() {
        return correo;
    }
    public String getNumTel() {
        return numeroTel;
    }
    public String getDireccion() {
        return direccion;
    }
    public void setCorreo(String correo) {
        this.correo = correo;
    }
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    public void setNumTel(String numTel) {
        this.numeroTel = numTel;
    }
    
    @Override
    public int guardar() {
        ConexionDB dbc = getSesion().getConexion();
        Connection conn = dbc.getConnection();
        String queryString = "INSERT INTO Persona (nombre, apellido, correo, numeroTel, direccion, ci, nivAcceso) VALUES (?, ?, ?, ?, ?, ?, ?);";
        try(PreparedStatement stmt = conn.prepareStatement(queryString)) {
            stmt.setString(1, nombre);
            stmt.setString(2, apellido);
            stmt.setString(3, correo);
            stmt.setString(4, numeroTel);
            stmt.setString(5, direccion);
            stmt.setInt(6, ci);
            stmt.setInt(7, 0);
            stmt.execute();
            return 0;
        } catch(SQLException e) {
            Mensaje.crearMensajeError("dbErrorTitulo", "dbErrorMensaje");
            return -1;
        }
    }
}