
package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import logic.Sesion;

/**
 *
 * @author Manuel René Pauls Toews
 */
public class App implements ActionListener {
    public static final String FORMULARIO_INICIO_SESION = "incicio sesion";
    public static final String CERRAR_SESION = "cerrar sesion";
    
    private OuterGui outer;
    
    public App() {
        outer = new OuterGui(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("heeyyy");
        switch(e.getActionCommand()) {
            case FORMULARIO_INICIO_SESION:
            case CERRAR_SESION:
                outer.mostrar(new FormularioIniciarSesion(this));
                break;
        }
    }

    void intentarLogin(String cuenta, String password) {
        try {
            Sesion.iniciarSesion(password, Integer.parseInt(cuenta));
            System.out.println("NICE!");
        } catch(Exception e) {
            System.out.println("Hackerman not you are");
        }
    }
}
