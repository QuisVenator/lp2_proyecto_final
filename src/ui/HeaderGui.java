
package ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;

/**
 *
 * @author Manuel René Pauls Toews
 */
public final class HeaderGui extends JPanel {
    private String name;
    private JLabel nameLabel;
    private JButton cerrarSesion;
    private JPanel sesion, menu;
    private App app;
    public HeaderGui(App app) {
        this.app = app;
        sesion = new JPanel();
        menu = new JPanel();
        this.setLayout(new BorderLayout());
        
        menu.setLayout(new FlowLayout(FlowLayout.LEADING));
        JLabel test = new JLabel("WEB BANKING");
        test.setFont(new Font(test.getName(), Font.PLAIN, 20));
        menu.add(test);
        menu.add(new JButton("Informe"));
        menu.add(new JButton("Pagar Servicio"));
        menu.add(new JButton("Saldo"));
        menu.add(new JButton("Transferencia"));
        //menu.setBackground(Color.decode("#295fa6"));
                
        //sesion.setBackground(Color.decode("#295fa6"));
        nameLabel = new JLabel();
        nameLabel.setText("NR CUENTA");
        cerrarSesion = new JButton("Iniciar Sesión");
        cerrarSesion.setActionCommand(App.FORMULARIO_INICIO_SESION);
        cerrarSesion.addActionListener(app);
        sesion.setLayout(new FlowLayout(FlowLayout.TRAILING));
        sesion.add(nameLabel);
        sesion.add(new JButton("Tutorial"));
        
        sesion.add(cerrarSesion);
        this.add(menu, BorderLayout.LINE_START);
        this.add(sesion, BorderLayout.LINE_END);
    }
    
    public void toggleSesionBtn() {
        if(cerrarSesion.getActionCommand().equals(App.FORMULARIO_INICIO_SESION)) {
            cerrarSesion.setText("Cerrar Sesion");
            cerrarSesion.setActionCommand(App.CERRAR_SESION);
        } else {
            cerrarSesion.setText("Iniciar Sesion");
            cerrarSesion.setActionCommand(App.CERRAR_SESION);
        }
    }
}
