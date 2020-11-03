
package ui;

import java.text.MessageFormat;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Mensaje {
    public static App app;
    
    public static int crearMensajeConfirmacion(String titulo, String mensaje, Object[] argumentos) {
        MessageFormat format = new MessageFormat(app.getLanguage().getString(mensaje));
        System.out.println(format.format(argumentos));
        JOptionPane.showMessageDialog(null, format.format(argumentos), app.getLanguage().getString(titulo), JOptionPane.INFORMATION_MESSAGE);
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
    public static String crearMensajeInput(String titulo, String mensaje) {   
        return JOptionPane.showInputDialog(new JFrame(app.getLanguage().getString(titulo)),app.getLanguage().getString(mensaje));  
    }
}
