package ui;

import java.awt.*;
import javax.swing.*;

/**
 *
 * @author Manuel René Pauls Toews
 */
public final class FormularioEliminarCuenta extends InnerGui {
    private final JButton eliminarBtn;
    private final JTextField cuentaText;
    private final JLabel cuentaLabel, titulo;
    private final JPanel[] lineas = new JPanel[5];
    private App app;
    public FormularioEliminarCuenta(App app) {
        this.app = app;
        //crear elementos
        eliminarBtn = new JButton(app.getLanguage().getString("eliminar"));
        cuentaLabel = new JLabel(app.getLanguage().getString("nroCuenta"));
        cuentaLabel.setPreferredSize(new Dimension(100, 20));
        cuentaText = new JTextField(20);
        cuentaText.setPreferredSize(new Dimension(150, 20));
        titulo = new JLabel(app.getLanguage().getString("eliminarCuenta"));
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
        lineas[3].add(eliminarBtn);
        
        //agregar lineas a ventana
        for(int i = 0; i < lineas.length; i++) 
            this.add(lineas[i]);
    }

    @Override
    public void languageReload() {
        eliminarBtn.setText(app.getLanguage().getString("eliminar"));
        cuentaLabel.setText(app.getLanguage().getString("nroCuenta"));
        titulo.setText(app.getLanguage().getString("eliminarCuenta"));
    }
}
