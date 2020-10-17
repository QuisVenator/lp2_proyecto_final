
package ui;

import javax.swing.JOptionPane;

/**
 *
 * @author Manuel René Pauls Toews
 */
public class Mensaje {
    
    
    public static int crearMensajeConfirmacion() {
        JOptionPane.showMessageDialog(null, "Por favor confirmar.", "Mensaje Confirmación", JOptionPane.INFORMATION_MESSAGE);
        return 0;
    }
    public static int crearMensajeError() {
        JOptionPane.showMessageDialog(null, "Algo salió mal", "Mensaje Error", JOptionPane.ERROR_MESSAGE);
        return 0;
    }
    public static int crearMensajeSino() {
        JOptionPane.showConfirmDialog(null, "Esta seguro?", "Mensaje Si-No", JOptionPane.YES_NO_OPTION);
        return 0;
    }
}
