package primer_final.idiomas.ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;
import primer_final.logic.CuentaCliente;
import primer_final.logic.SesionEmpleado;
import primer_final.logic.excepciones.SesionExpiradaException;

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
        
        //agregar funcionalidad al boton
        eliminarBtn.addActionListener((ActionEvent e) -> {
            try{
                ((SesionEmpleado)app.sesion).eliminarCuenta(new CuentaCliente(null, null, Integer.parseInt(cuentaText.getText())));
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
