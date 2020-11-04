package primer_final.ui;

import java.awt.*;
import javax.swing.*;

public final class FooterGui extends InnerGui {
    private final App app;
    
    private JButton terminarSimulacion;
    public FooterGui(App app) {
        this.app = app;
        
        this.setBackground(Color.decode("#00263F"));
        terminarSimulacion = new JButton(app.getLanguage().getString("terminarSimulacion"));
        terminarSimulacion.setIcon(new ImageIcon("iconos/cancelar_16.png"));
        terminarSimulacion.addActionListener(e -> System.exit(0));
        terminarSimulacion.setFocusable(false);
        this.setLayout(new FlowLayout(FlowLayout.CENTER));
        this.add(terminarSimulacion);
    }

    @Override
    public void languageReload() {
        terminarSimulacion.setText(app.getLanguage().getString("terminarSimulacion"));
    }
}
