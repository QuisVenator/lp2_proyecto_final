package primer_final.logic;

import primer_final.ui.Mensaje;
import primer_final.ui.App;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.UIManager;

public class LP2_Proyecto_Final {

    public static void main(String[] args) {
        try {
            //intentamos usar un layout similar al usado por el sistema operativo
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println("No se pudo cambiar el diseño, volviendo a default");
        }
        //configurar limites de tiempo sesion
        ConexionDB conexion = new ConexionDB();
        try(PreparedStatement stmt =conexion.getConnection().prepareStatement("SELECT * FROM Seguridad WHERE identificacion = 'actual'")){
            ResultSet rs = stmt.executeQuery();
            if(!rs.next()) {
                SistemaSeguridad.T_MAX_SESION_CLIENTE = 60000;
                SistemaSeguridad.T_MAX_SESION_EMPLEADO = 300000;
            } else {
                SistemaSeguridad.T_MAX_SESION_CLIENTE = rs.getInt("T_MAX_SESION_CLIENTE");
                SistemaSeguridad.T_MAX_SESION_EMPLEADO = rs.getInt("T_MAX_SESION_ADMIN");
            }
        } catch (SQLException ex) {
            SistemaSeguridad.T_MAX_SESION_CLIENTE = 60000;
            SistemaSeguridad.T_MAX_SESION_EMPLEADO = 300000;
        } finally {
            try {
                conexion.getConnection().close();
            } catch (SQLException ex) {
                System.out.println("Error cerrando conexion de configuracion");
            }
        }
        Mensaje.app = new App();
    }

}
