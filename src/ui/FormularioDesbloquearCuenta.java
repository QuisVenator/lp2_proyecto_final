package ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;
import logic.Servicio;
import logic.SesionEmpleado;
import logic.excepciones.SesionExpiradaException;

/**
 *
 * @author Manuel René Pauls Toews
 */
public final class FormularioDesbloquearCuenta extends InnerGui {
    private final JButton desbloquearBtn;
    private final JTextField cuentaText;
    private final JLabel cuentaLabel, titulo;
    private final JPanel[] lineas = new JPanel[5];
    private App app;
    public FormularioDesbloquearCuenta(App app) {
        this.app = app;
        //crear elementos
        desbloquearBtn = new JButton(app.getLanguage().getString("desbloquear"));
        cuentaLabel = new JLabel(app.getLanguage().getString("nroCuenta"));
        cuentaLabel.setPreferredSize(new Dimension(100, 20));
        cuentaText = new JTextField(20);
        cuentaText.setPreferredSize(new Dimension(150, 20));
        titulo = new JLabel(app.getLanguage().getString("desbloquearCuenta"));
        titulo.setFont(new Font(titulo.getName(), Font.PLAIN, 20));
        
        desbloquearBtn.addActionListener((ActionEvent e) ->{
            try {
                ((SesionEmpleado)app.sesion).desbloquearCuenta(Integer.parseInt(cuentaText.getText()));
            } catch (NumberFormatException ex) {
                Mensaje.crearMensajeError("inputNoCorrectoTitulo", "inputNoCorrecto");
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
        lineas[2].add(cuentaLabel);
        lineas[2].add(cuentaText);
        lineas[3].add(desbloquearBtn);
        
        //agregar lineas a ventana
        for(int i = 0; i < lineas.length; i++) 
            this.add(lineas[i]);
    }

    @Override
    public void languageReload() {
        desbloquearBtn.setText(app.getLanguage().getString("desbloquear"));
        cuentaLabel.setText(app.getLanguage().getString("nroCuenta"));
        titulo.setText(app.getLanguage().getString("desbloquearCuenta"));
    }
}
