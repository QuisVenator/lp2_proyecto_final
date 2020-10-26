
package ui;

import java.awt.*;
import javax.swing.*;

/**
 *
 * @author Manuel René Pauls Toews
 */
public class GuiSaldo extends JPanel {
    JPanel[] lineas = new JPanel[6];
    private final App app;
    
    public GuiSaldo(App app) {
        this.app = app;
        
        //preparar lineas
        for(int i = 0; i < lineas.length; i++) {
            lineas[i] = new JPanel();
            lineas[i].setBackground(Color.decode("#f8f8ff"));
        }
        for(int i = 1; i < lineas.length-1; i++) 
            lineas[i].setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        
        //crear elementos
        JLabel titulo = new JLabel(app.getLanguage().getString("informacionCuenta"));
        titulo.setPreferredSize(new Dimension(350, 80));
        titulo.setFont(new Font(titulo.getName(), Font.PLAIN, 28));
        JLabel titularLabel = new JLabel(app.getLanguage().getString("titularNombre"));
        titularLabel.setPreferredSize(new Dimension(200, 30));
        titularLabel.setFont(new Font("Courier New", Font.PLAIN, 20));
        JLabel titularText = new JLabel("Nombre Titular");
        titularText.setPreferredSize(new Dimension(200, 30));
        titularText.setFont(new Font("Courier New", Font.PLAIN, 20));
        JLabel nrCuentaLabel = new JLabel(app.getLanguage().getString("nroCuenta"));
        nrCuentaLabel.setPreferredSize(new Dimension(200, 30));
        nrCuentaLabel.setFont(new Font("Courier New", Font.PLAIN, 20));
        JLabel nrCuentaText = new JLabel("133742069");
        nrCuentaText.setPreferredSize(new Dimension(200, 30));
        nrCuentaText.setFont(new Font("Courier New", Font.PLAIN, 20));
        JLabel saldoLabel = new JLabel(app.getLanguage().getString("saldoActual"));
        saldoLabel.setPreferredSize(new Dimension(200, 30));
        saldoLabel.setFont(new Font("Courier New", Font.PLAIN, 20));
        JLabel saldoText = new JLabel("5.000.000");
        saldoText.setPreferredSize(new Dimension(200, 30));
        saldoText.setFont(new Font("Courier New", Font.PLAIN, 20));
        
        //agregar lineas a ventana
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        lineas[1].add(titulo);
        lineas[2].add(titularLabel);
        lineas[2].add(titularText);
        lineas[3].add(nrCuentaLabel);
        lineas[3].add(nrCuentaText);
        lineas[4].add(saldoLabel);
        lineas[4].add(saldoText);
        
        for(int i = 0; i < lineas.length; i++) 
            this.add(lineas[i]);
    }
}
