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
        this.setBackground(Color.decode("#595959"));
        terminarSimulacion = new JButton("Terminar Simulación");
        this.setLayout(new FlowLayout(FlowLayout.CENTER));
        this.add(terminarSimulacion);
    }
    
}
