package ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;
import logic.SesionCliente;
import logic.excepciones.SesionExpiradaException;

/**
 *
 * @author Manuel René Pauls Toews
 */
public final class FormularioTransferencia extends InnerGui {
    private final JButton efectuarTransferencia;
    private final JTextField cuentaText, montoText;
    private final JPasswordField pinText;
    private final JLabel cuentaLabel, pinLabel, titulo, montoLabel;
    private final JPanel[] lineas = new JPanel[7];
    private App app;
    public FormularioTransferencia(App app) {
        this.app = app;
        //crear elementos
        efectuarTransferencia = new JButton(app.getLanguage().getString("efectuarTransferencia"));
        cuentaLabel = new JLabel(app.getLanguage().getString("nroCuenta"));
        cuentaLabel.setPreferredSize(new Dimension(100, 20));
        montoLabel = new JLabel(app.getLanguage().getString("monto"));
        montoLabel.setPreferredSize(new Dimension(100, 20));
        pinLabel = new JLabel(app.getLanguage().getString("pinTransferencia"));
        pinLabel.setPreferredSize(new Dimension(100, 20));
        cuentaText = new JTextField(20);
        cuentaText.setPreferredSize(new Dimension(150, 20));
        montoText = new JTextField(20);
        montoText.setPreferredSize(new Dimension(150, 20));
        pinText = new JPasswordField(20);
        pinText.setPreferredSize(new Dimension(150, 20));
        titulo = new JLabel(app.getLanguage().getString("transferencia"));
        titulo.setFont(new Font(titulo.getName(), Font.PLAIN, 20));
        
        efectuarTransferencia.addActionListener((ActionEvent e) -> {
            try {
                ((SesionCliente)app.sesion).generarTransferencia(Integer.parseInt(cuentaText.getText()), 
                        Double.parseDouble(montoText.getText()), new String(pinText.getPassword()));
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
        lineas[4].add(pinLabel);
        lineas[4].add(pinText);
        lineas[5].add(efectuarTransferencia);
        
        //agregar lineas a ventana
        for(int i = 0; i < lineas.length; i++) 
            this.add(lineas[i]);
    }

    @Override
    public void languageReload() {
        efectuarTransferencia.setText(app.getLanguage().getString("efectuarTransferencia"));
        cuentaLabel.setText(app.getLanguage().getString("nroCuenta"));
        montoLabel.setText(app.getLanguage().getString("monto"));
        pinLabel.setText(app.getLanguage().getString("pinTransferencia"));
        titulo.setText(app.getLanguage().getString("transferencia"));
    }
}
