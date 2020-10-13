package logic;

import javax.swing.UIManager;
import password_hashing.PasswordStorage;
import ui.*;

/*
 * @author Manuel René Pauls Toews
 */
public class LP2_Proyecto_Final {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws PasswordStorage.CannotPerformOperationException {
        ConexionDB conexionPrueba = new ConexionDB();
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println("No se pudo cambiar el diseño, volviendo a default");
        }
        new App();
    }

}
