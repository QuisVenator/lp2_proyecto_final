package ui;

import java.awt.*;
import javax.swing.*;

/**
 *
 * @author Manuel René Pauls Toews
 */
public final class FooterGui extends JPanel {
    private JButton terminarSimulacion;
    public FooterGui() {
        this.setBackground(Color.decode("#00263F"));
        terminarSimulacion = new JButton("Terminar Simulación");
        terminarSimulacion.setIcon(new ImageIcon("iconos/cancelar_16.png"));
        terminarSimulacion.addActionListener(e -> System.exit(0));
        this.setLayout(new FlowLayout(FlowLayout.CENTER));
        this.add(terminarSimulacion);
    }
    
}
