
package ui;

import javax.swing.JOptionPane;

/**
 *
 * @author Manuel René Pauls Toews
 */
public class Mensaje {
    public static App app;
    
    public static int crearMensajeConfirmacion(String titulo, String mensaje) {
        JOptionPane.showMessageDialog(null, app.getLanguage().getString(mensaje), app.getLanguage().getString(titulo), JOptionPane.INFORMATION_MESSAGE);
        return 0;
    }
    public static int crearMensajeError(String titulo, String mensaje) {
        JOptionPane.showMessageDialog(null, app.getLanguage().getString(mensaje), app.getLanguage().getString(titulo), JOptionPane.ERROR_MESSAGE);
        return 0;
    }
    public static int crearMensajeSino(String titulo, String mensaje) {
        JOptionPane.showConfirmDialog(null, app.getLanguage().getString(mensaje), app.getLanguage().getString(titulo), JOptionPane.YES_NO_OPTION);
        return 0;
    }
}
