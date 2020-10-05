package lp2_proyecto_final;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Manuel René Pauls Toews
 */
public class ConexionDB {
    private Connection conn;
    
    public ConexionDB() {
        try {
            connect();
        } catch(SQLException e) {
            System.err.println("Error conectando a base de datos: "+e.getMessage());
        }
    }
    
    public Connection getConnection() {
        if(conn != null) return conn;
        try {
            connect();
            return conn;
        } catch(SQLException e) {
            System.err.println("Error conectando a base de datos: "+e.getMessage());
            return null;
        }
    }
    
    private void connect() throws SQLException {
        boolean dbNueva;
        File f = new File("simulacion_web_banking.db");
        dbNueva = !f.exists();

        String url = "jdbc:sqlite:"+f.getAbsolutePath();
        conn = DriverManager.getConnection(url);
        if(dbNueva) crearDbPrueba();
    }
    
    private void crearDbPrueba() {
        System.out.println("Creando base de datos para pruebas...");
        String[] stmtStrings = {
            "CREATE TABLE IF NOT EXISTS Cuenta("
                + "nrCuenta integer PRIMARY KEY,"
                + "titular integer,"
                + "contrasenha text NOT NULL,"
                + "estado integer DEFAULT 0,"
                + "pinTransferencia text NOT NULL,"
                + "saldo real CHECK((saldo IS NULL) or (saldo >= 0)),"
                + "FOREIGN KEY(titular) REFERENCES Persona(ci)"
                + ");",
            "CREATE TABLE IF NOT EXISTS Persona("
                + "nombre text NOT NULL,"
                + "apellido text NOT NULL,"
                + "correo text,"
                + "numeroTel text NOT NULL,"
                + "direccion text NOT NULL,"
                + "ci integer PRIMARY KEY,"
                + "numCliente integer,"
                + "nivAcceso integer"
                + ");",
            "CREATE TABLE IF NOT EXISTS Servicio("
                + "nombre text PRIMARY KEY,"
                + "descripcion text,"
                + "iconoPath text,"
                + "cuenta integer NOT NULL,"
                + "FOREIGN KEY(cuenta) REFERENCES Cuenta(nrCuenta)"
                + ");",
            "CREATE TABLE IF NOT EXISTS Log("
                + "tipo text NOT NULL,"
                + "descripcion text,"
                + "hora text DEFAULT (date('now')),"
                + "cuenta integer NOT NULL,"
                + "FOREIGN KEY(cuenta) REFERENCES Cuenta(nrCuenta)"
                + ");",
            "CREATE TABLE IF NOT EXISTS Seguridad("
                + "T_MAX_SESION_CLIENTE integer NN,"
                + "T_MAX_SESION_ADMIN integer NN,"
                + "identificacion text DEFAULT 'actual' PRIMARY KEY"
                + ");"
        };
        boolean errors = false;
        try (Statement stmt = conn.createStatement()) {
            for(String stmtString : stmtStrings) {
                stmt.execute(stmtString);
                System.out.println(stmtString);
            }
        } catch (SQLException e) {
            errors = true;
            System.err.println("Error creando base de datos prueba: " + e.getMessage());
        }
        
        if(!errors)
            System.out.println("Base de datos prueba creada exitosamente!");
    }
}
