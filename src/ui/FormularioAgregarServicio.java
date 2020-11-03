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
public final class FormularioAgregarServicio extends InnerGui {
    private final JButton agregarBtn;
    private final JTextField cuentaText, montoText, descripcionText, nombreText, iconoText;
    private final JLabel cuentaLabel, montoLabel, titulo, descripcionLabel, nombreLabel, iconoLabel;
    private final JPanel[] lineas = new JPanel[9];
    private App app;
    public FormularioAgregarServicio(App app) {
        this.app = app;
        //crear elementos
        agregarBtn = new JButton(app.getLanguage().getString("agregar"));
        cuentaLabel = new JLabel(app.getLanguage().getString("nroCuenta"));
        cuentaLabel.setPreferredSize(new Dimension(100, 20));
        montoLabel = new JLabel(app.getLanguage().getString("monto"));
        montoLabel.setPreferredSize(new Dimension(100, 20));
        nombreLabel = new JLabel(app.getLanguage().getString("nombreServicio"));
        nombreLabel.setPreferredSize(new Dimension(100, 20));
        descripcionLabel = new JLabel(app.getLanguage().getString("descripcion"));
        descripcionLabel.setPreferredSize(new Dimension(100, 20));
        iconoLabel = new JLabel(app.getLanguage().getString("icono"));
        iconoLabel.setPreferredSize(new Dimension(100, 20));
        cuentaText = new JTextField(20);
        cuentaText.setPreferredSize(new Dimension(150, 20));
        montoText = new JTextField(20);
        montoText.setPreferredSize(new Dimension(150, 20));
        nombreText = new JTextField(20);
        nombreText.setPreferredSize(new Dimension(150, 20));
        descripcionText = new JTextField(20);
        descripcionText.setPreferredSize(new Dimension(150, 20));
        iconoText = new JTextField(20);
        iconoText.setPreferredSize(new Dimension(150, 20));
        iconoText.setText("default");
        titulo = new JLabel(app.getLanguage().getString("agregarServicio"));
        titulo.setFont(new Font(titulo.getName(), Font.PLAIN, 20));
        
        agregarBtn.addActionListener((ActionEvent e) ->{
            try {
                if(nombreText.getText().equals("")) {
                    Mensaje.crearMensajeError("inputNoCorrectoTitulo", "inputNoCorrecto");
                    return;
                }
                Servicio servicio = new Servicio(Integer.parseInt(cuentaText.getText()), Double.parseDouble(montoText.getText()),
                        nombreText.getText(), descripcionText.getText(), iconoText.getText());
                if(((SesionEmpleado)app.sesion).agregarServicio(servicio) == 0) {
                    Mensaje.crearMensajeConfirmacion("servicioCreadoTitulo", "servicioCreado", 
                            new Object[] {servicio.getCuentaNr(), servicio.getNombre(), servicio.getMonto()});
                }
            } catch (NumberFormatException ex) {
                Mensaje.crearMensajeError("inputNoCorrectoTitulo", "inputNoCorrecto");
            } catch (SesionExpiradaException ex) {
                app.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, App.CERRAR_SESION));
            } catch(IllegalArgumentException ex) {
                Mensaje.crearMensajeError("montoNegativoTitulo", "montoNegativo");
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
        lineas[3].add(montoLabel);
        lineas[3].add(montoText);
        lineas[4].add(nombreLabel);
        lineas[4].add(nombreText);
        lineas[5].add(descripcionLabel);
        lineas[5].add(descripcionText);
        lineas[6].add(iconoLabel);
        lineas[6].add(iconoText);
        lineas[7].add(agregarBtn);
        
        //agregar lineas a ventana
        for(int i = 0; i < lineas.length; i++) 
            this.add(lineas[i]);
    }

    @Override
    public void languageReload() {
        agregarBtn.setText(app.getLanguage().getString("agregar"));
        cuentaLabel.setText(app.getLanguage().getString("nroCuenta"));
        cuentaLabel.setPreferredSize(new Dimension(100, 20));
        montoLabel.setText(app.getLanguage().getString("monto"));
        montoLabel.setPreferredSize(new Dimension(100, 20));
        nombreLabel.setText(app.getLanguage().getString("nombreServicio"));
        nombreLabel.setPreferredSize(new Dimension(100, 20));
        descripcionLabel.setText(app.getLanguage().getString("descripcion"));
        descripcionLabel.setPreferredSize(new Dimension(100, 20));
        iconoLabel.setText(app.getLanguage().getString("icono"));
        titulo.setText(app.getLanguage().getString("agregarServicio"));
    }
}
