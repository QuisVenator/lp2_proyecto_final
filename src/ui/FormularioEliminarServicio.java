package ui;

import java.awt.*;
import javax.swing.*;

/**
 *
 * @author Manuel René Pauls Toews
 */
public final class FormularioEliminarServicio extends JPanel {
    private final JButton eliminarBtn;
    private final JTextField codigoText;
    private final JLabel codigoLabel, titulo;
    private final JPanel[] lineas = new JPanel[5];
    private App app;
    public FormularioEliminarServicio(App app) {
        this.app = app;
        //crear elementos
        eliminarBtn = new JButton("Eliminar Servicio");
        codigoLabel = new JLabel("Nombre Servicio: ");
        codigoLabel.setPreferredSize(new Dimension(100, 20));
        codigoText = new JTextField(20);
        codigoText.setPreferredSize(new Dimension(150, 20));
        titulo = new JLabel("Eliminar Servicio");
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
        lineas[2].add(codigoLabel);
        lineas[2].add(codigoText);
        lineas[3].add(eliminarBtn);
        
        //agregar lineas a ventana
        for(int i = 0; i < lineas.length; i++) 
            this.add(lineas[i]);
    }
}
