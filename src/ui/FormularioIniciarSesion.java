package ui;

import java.awt.*;
import javax.swing.*;

/**
 *
 * @author Manuel René Pauls Toews
 */
public final class FormularioIniciarSesion extends JPanel {
    private final JButton terminarSimulacion;
    private final JTextField cuentaText;
    private final JPasswordField pinText;
    private final JLabel cuentaLabel, pinLabel, titulo;
    private final JPanel[] lineas = new JPanel[6];
    private App app;
    public FormularioIniciarSesion(App app) {
        this.app = app;
        //crear elementos
        terminarSimulacion = new JButton("Iniciar Sesión");
        terminarSimulacion.addActionListener(e->intentarLogin());
        cuentaLabel = new JLabel("Nro. Cuenta: ");
        cuentaLabel.setPreferredSize(new Dimension(100, 20));
        pinLabel = new JLabel("PIN: ");
        pinLabel.setPreferredSize(new Dimension(100, 20));
        cuentaText = new JTextField(20);
        cuentaText.setPreferredSize(new Dimension(150, 20));
        pinText = new JPasswordField(20);
        pinText.setPreferredSize(new Dimension(150, 20));
        titulo = new JLabel("Iniciar Sesion");
        titulo.setFont(new Font(titulo.getName(), Font.PLAIN, 20));
        
        //preparar lineas
        for(int i = 0; i < lineas.length; i++) {
            lineas[i] = new JPanel();
            lineas[i].setBackground(Color.decode("#f8f8ff"));
        }
        for(int i = 1; i < lineas.length-1; i++) 
            lineas[i].setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        
        //a cada linea agregar elementos necesarios
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        lineas[1].add(titulo);
        lineas[2].add(cuentaLabel);
        lineas[2].add(cuentaText);
        lineas[3].add(pinLabel);
        lineas[3].add(pinText);
        lineas[4].add(terminarSimulacion);
        
        //agregar lineas a ventana
        for(int i = 0; i < lineas.length; i++) 
            this.add(lineas[i]);
    }
    
    private void intentarLogin() {
        app.intentarLogin(cuentaText.getText(), new String(pinText.getPassword()));
    }
}
