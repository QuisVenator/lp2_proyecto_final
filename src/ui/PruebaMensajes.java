
package ui;

import javax.swing.UIManager;

/**
 *
 * @author Manuel René Pauls Toews
 */
public class PruebaMensajes {
    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println("No se pudo cambiar el diseño, volviendo a default");
        }
        Mensaje.crearMensajeConfirmacion();
        Mensaje.crearMensajeError();
        Mensaje.crearMensajeSino();
    }
}
