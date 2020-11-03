package logic;

import javax.swing.UIManager;
import ui.*;

public class LP2_Proyecto_Final {

    public static void main(String[] args) {
        try {
            //intentamos usar un layout similar al usado por el sistema operativo
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println("No se pudo cambiar el diseño, volviendo a default");
        }
        Mensaje.app = new App();
    }

}
