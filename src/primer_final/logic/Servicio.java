
package primer_final.logic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;
import primer_final.ui.Mensaje;

public class Servicio extends DBObject {
    private String nombre, descripcion, iconoPath;
    private int cuentaNr; //cada servicio se relaciona a una cuenta en la cual se depositan los pagos
    private double monto;
    
    public Servicio(int cuentaNr, double monto) {
        this(cuentaNr, monto, "Servicio sin nombre "+(new Random()).nextLong());
    }
    public Servicio(int cuentaNr, double monto, String nombre) {
        this(cuentaNr, monto, nombre, "-");
    }
    public Servicio(int cuentaNr, double monto, String nombre, String descripcion) {
        this(cuentaNr, monto, nombre, descripcion, "default");
    }
    public Servicio(int cuentaNr, double monto, String nombre, String descripcion, String iconoPath) {
        if(monto < 0) throw new IllegalArgumentException("Monto negativo no permitido!");
        this.cuentaNr = cuentaNr;
        this.monto = monto;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.iconoPath = iconoPath;
    }
    
    public String getNombre() {
        return nombre;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public int getCuentaNr() {
        return cuentaNr;
    }
    public void setIconoLoc(String location) {
        iconoPath = location;
    }
    public String getIconoPath() {
        return iconoPath;
    }
    public double getMonto() {
        return monto;
    }
    
    @Override
    public int guardar() {
        ConexionDB dbc = getSesion().getConexion();
        Connection conn = dbc.getConnection();
        String queryString = "INSERT INTO Servicio (nombre, descripcion, iconoPath, monto, cuenta) VALUES (?,?,?,?,?);";
        try(PreparedStatement stmt = conn.prepareStatement(queryString)) {
            stmt.setString(1, nombre);
            stmt.setString(2, descripcion);
            stmt.setString(3, iconoPath);
            stmt.setDouble(4, monto);
            stmt.setDouble(5, cuentaNr);
            stmt.execute();
            return 0;
        } catch(SQLException e) {
            Mensaje.crearMensajeError("dbErrorTitulo", "dbErrorMensaje");
            return -1;
        }
    }

}
