package ui;

import java.awt.*;
import javax.swing.*;

/**
 *
 * @author Manuel René Pauls Toews
 */
public final class FormularioDeposito extends JPanel {
    private final JButton depositar;
    private final JTextField cuentaText, montoText;
    private final JLabel cuentaLabel, montoLabel, titulo;
    private final JPanel[] lineas = new JPanel[6];
    private App app;
    public FormularioDeposito(App app) {
        this.app = app;
        //crear elementos
        depositar = new JButton(app.getLanguage().getString("deposito"));
        cuentaLabel = new JLabel(app.getLanguage().getString("nroCuenta"));
        cuentaLabel.setPreferredSize(new Dimension(100, 20));
        montoLabel = new JLabel(app.getLanguage().getString("monto"));
        montoLabel.setPreferredSize(new Dimension(100, 20));
        cuentaText = new JTextField(20);
        cuentaText.setPreferredSize(new Dimension(150, 20));
        montoText = new JTextField(20);
        montoText.setPreferredSize(new Dimension(150, 20));
        titulo = new JLabel(app.getLanguage().getString("hacerDeposito"));
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
        lineas[3].add(montoLabel);
        lineas[3].add(montoText);
        lineas[4].add(depositar);
        
        //agregar lineas a ventana
        for(int i = 0; i < lineas.length; i++) 
            this.add(lineas[i]);
    }
}
