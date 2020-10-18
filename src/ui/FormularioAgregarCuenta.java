package ui;

import java.awt.*;
import javax.swing.*;

/**
 *
 * @author Manuel René Pauls Toews
 */
public final class FormularioAgregarCuenta extends JPanel {
    private final JButton agregarBtn;
    private final JTextField ciText, apellidoText, correoText, telText, direccionText, accesoText, nombreText;
    private final JLabel ciLabel, apellidoLabel, titulo, correoLabel, telLabel, direccionLabel, accesoLabel, nombreLabel;
    private final JPanel[] lineas = new JPanel[10];
    private App app;
    public FormularioAgregarCuenta(App app) {
        this.app = app;
        //crear elementos
        agregarBtn = new JButton("Agregar");
        ciLabel = new JLabel("Documento: ");
        ciLabel.setPreferredSize(new Dimension(100, 20));
        apellidoLabel = new JLabel("Apellido: ");
        apellidoLabel.setPreferredSize(new Dimension(100, 20));
        telLabel = new JLabel("Teléfono: ");
        telLabel.setPreferredSize(new Dimension(100, 20));
        correoLabel = new JLabel("Correo: ");
        correoLabel.setPreferredSize(new Dimension(100, 20));
        direccionLabel = new JLabel("Dirección: ");
        direccionLabel.setPreferredSize(new Dimension(100, 20));
        accesoLabel = new JLabel("Acceso: ");
        accesoLabel.setPreferredSize(new Dimension(100, 20));
        nombreLabel = new JLabel("Nombre: ");
        nombreLabel.setPreferredSize(new Dimension(100, 20));
        ciText = new JTextField(20);
        ciText.setPreferredSize(new Dimension(150, 20));
        apellidoText = new JTextField(20);
        apellidoText.setPreferredSize(new Dimension(150, 20));
        telText = new JTextField(20);
        telText.setPreferredSize(new Dimension(150, 20));
        correoText = new JTextField(20);
        correoText.setPreferredSize(new Dimension(150, 20));
        direccionText = new JTextField(20);
        direccionText.setPreferredSize(new Dimension(150, 20));
        accesoText = new JTextField(20);
        accesoText.setPreferredSize(new Dimension(150, 20));
        nombreText = new JTextField(20);
        nombreText.setPreferredSize(new Dimension(150, 20));
        titulo = new JLabel("Agregar Cuenta");
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
        lineas[2].add(ciLabel);
        lineas[2].add(ciText);
        lineas[3].add(apellidoLabel);
        lineas[3].add(apellidoText);
        lineas[4].add(nombreLabel);
        lineas[4].add(nombreText);
        lineas[5].add(telLabel);
        lineas[5].add(telText);
        lineas[6].add(correoLabel);
        lineas[6].add(correoText);
        lineas[7].add(direccionLabel);
        lineas[7].add(direccionText);
        lineas[8].add(accesoLabel);
        lineas[8].add(accesoText);
        lineas[9].add(agregarBtn);
        
        //agregar lineas a ventana
        for(int i = 0; i < lineas.length; i++) 
            this.add(lineas[i]);
    }
}
