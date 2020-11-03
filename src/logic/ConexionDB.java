package logic;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import password_hashing.PasswordStorage;
import password_hashing.PasswordStorage.CannotPerformOperationException;

/**
 * Mantienen una conexion a base de datos.<br>
 * Como es una simulación con Base de Datos en archivo, hemos decidido por no cerrar la conexión mientras esta corriendo el programa
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
    
    /**
     * Crea la conexión si no existe o reutiliza una que ya fue creada
     * @return conexión a base de datos
     */
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
    
    /**
     * Conecta a base de datos si existe.<br>
     * Si no existe la crea con datos de prueba.
     * @throws SQLException 
     */
    private void connect() throws SQLException {
        boolean dbNueva;
        File f = new File("simulacion_web_banking.db");
        dbNueva = !f.exists();

        String url = "jdbc:sqlite:"+f.getAbsolutePath();
        conn = DriverManager.getConnection(url);
        if(dbNueva) crearDbPrueba();
    }
    
    /**
     * Crea una nueva base de datos con datos prueba.
     */
    private void crearDbPrueba(){
        System.out.println("Creando base de datos para pruebas...");
        String[] stmtStrings = {
            "CREATE TABLE IF NOT EXISTS Cuenta("
                + "nrCuenta integer PRIMARY KEY,"
                + "titular integer,"
                + "contrasenha text NOT NULL,"
                + "estado integer DEFAULT 0,"
                + "pinTransferencia text,"
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
            "CREATE TABLE IF NOT EXISTS Transferencia("
                + "id integer PRIMARY KEY,"
                + "monto saldo real CHECK(monto >= 0),"
                + "envia integer,"
                + "recibe integer,"
                + "tipo integer NOT NULL,"
                + "FOREIGN KEY(envia) REFERENCES Cuenta(nrCuenta),"
                + "FOREIGN KEY(recibe) REFERENCES Cuenta(nrCuenta)"
                + ");",
            "CREATE TABLE IF NOT EXISTS Servicio("
                + "nombre text PRIMARY KEY,"
                + "descripcion text,"
                + "iconoPath text,"
                + "cuenta integer NOT NULL,"
                + "monto real CHECK(monto > 0),"
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
            }
        } catch (SQLException e) {
            errors = true;
            System.err.println("Error creando base de datos prueba: " + e.getMessage());
            return;
        }
        if(!errors)
            System.out.println("Base de datos prueba creada exitosamente!");
        
        String[] contrasenhas;
        try {
            contrasenhas = new String[]{
                PasswordStorage.createHash("mi_contrasenha_segura"),
                PasswordStorage.createHash("1337"),
                PasswordStorage.createHash("4200"),
                PasswordStorage.createHash("este_trabajo_se_merece_calificacion_5"),
                PasswordStorage.createHash("1102"),
                PasswordStorage.createHash("5555"),
                PasswordStorage.createHash("12345"),
                PasswordStorage.createHash("54321"),
                PasswordStorage.createHash("54455"),
                PasswordStorage.createHash("32123")
                
            };
            
        } catch (CannotPerformOperationException e) {
            System.err.println("Error generando contrasenhas para bases de datos prueba: "+e.getMessage());
            return;
        }
        
        System.out.println("Insertando datos prueba...");
        System.out.println("\tCrear personas");
        stmtStrings  = new String[]{
            "INSERT INTO Persona (nombre, apellido, correo, numeroTel, direccion, ci, nivAcceso) VALUES "
                + "('Manuel', 'Pauls', 'admin@renepauls.com', '0983 738 040', 'Dr Andres Gubetich 1362', 5708234, 2), "
                + "('Guillermo', 'Pamplona', 'alguien@dominio.com', '0998123456', 'Paraguay', 2345678, 0), "
                + "('Arturo', 'Jara', 'otraPersona@noexiste.com', '123 45 67 89', 'Planeta Tierra', 9876543, 1), "
                + "('Administrador', 'BNDE', 'noRespondemos@bnde.com.py', '0493 240 438', 'Asuncion', 1234567, 0);",
            "INSERT INTO Cuenta (nrCuenta, titular, contrasenha, pinTransferencia, saldo) VALUES "
                + "(584648, 5708234, '"+contrasenhas[0]+"', NULL,0), "
                + "(456821, 5708234, '"+contrasenhas[1]+"', '"+contrasenhas[6]+"',1000000), "
                + "(548224, 2345678, '"+contrasenhas[2]+"', '"+contrasenhas[7]+"',47000000), "
                + "(457236, 9876543, '"+contrasenhas[3]+"', NULL,0), "
                + "(878321, 1234567, '"+contrasenhas[4]+"', '"+contrasenhas[8]+"',5000000), "
                + "(785124, 9876543, '"+contrasenhas[5]+"', '"+contrasenhas[9]+"',500000);",
            "INSERT INTO Servicio (nombre, descripcion, iconoPath, monto, cuenta) VALUES "
                + "('Servicio 1', '', 'servicio_ejemplo1_32.png', 10000, 5), "
                + "('Servicio 2', '', 'servicio_ejemplo2_32.png', 50000, 6),"
                + "('Otro Servicio', '', 'servicio_ejemplo3_32.png', 50000, 6);",
            "INSERT INTO Seguridad (T_MAX_SESION_CLIENTE, T_MAX_SESION_ADMIN) VALUES (2, 60);"
        };
        
        try (Statement stmt = conn.createStatement()) {
            for(String stmtString : stmtStrings) {
                System.out.println(stmtString);
                stmt.execute(stmtString);
            }
        } catch (SQLException e) {
            errors = true;
            System.err.println("Error insertando datos prueba: " + e.getMessage());
        }
        if(!errors)
            System.out.println("Base de datos ahora contiene datos prueba!");
    }
}