
package lp2_proyecto_final;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/*
 * @author Manuel René Pauls Toews
 */
public class LP2_Proyecto_Final {

    public static void connect() {
        Connection conn = null;
        try {
            // db parameters
            File f = new File("chinook.db");
            if(!f.exists()) System.out.println("Creando nueva base de datos!");
            String url = "jdbc:sqlite:"+f.getAbsolutePath();
            // create a connection to the database
            conn = DriverManager.getConnection(url);
            
            System.out.println("Connection to SQLite has been established.");
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        connect();
    }

}
