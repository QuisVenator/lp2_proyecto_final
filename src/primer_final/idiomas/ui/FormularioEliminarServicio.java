package primer_final.idiomas.ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;
import primer_final.logic.Servicio;
import primer_final.logic.SesionEmpleado;
import primer_final.logic.excepciones.SesionExpiradaException;

public final class FormularioEliminarServicio extends InnerGui {
    private final JButton eliminarBtn;
    private final JTextField codigoText;
    private final JLabel codigoLabel, titulo;
    private final JPanel[] lineas = new JPanel[5];
    private App app;
    public FormularioEliminarServicio(App app) {
        this.app = app;
        //crear elementos
        eliminarBtn = new JButton(app.getLanguage().getString("eliminarServicio"));
        codigoLabel = new JLabel(app.getLanguage().getString("nombreServicio"));
        codigoLabel.setPreferredSize(new Dimension(100, 20));
        codigoText = new JTextField(20);
        codigoText.setPreferredSize(new Dimension(150, 20));
        titulo = new JLabel(app.getLanguage().getString("eliminarServicio"));
        titulo.setFont(new Font(titulo.getName(), Font.PLAIN, 20));
        
        //agregar funcionalidad al boton
        eliminarBtn.addActionListener((ActionEvent e) ->{
            try {
                Servicio servicio = new Servicio(0, 0, codigoText.getText());
                ((SesionEmpleado)app.sesion).eliminarServicio(servicio);
            } catch (SesionExpiradaException ex) {
                app.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, App.CERRAR_SESION));
            }
        });
        
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

    @Override
    public void languageReload() {
        eliminarBtn.setText(app.getLanguage().getString("eliminarServicio"));
        codigoLabel.setText(app.getLanguage().getString("nombreServicio"));
        titulo.setText(app.getLanguage().getString("eliminarServicio"));
    }
}
