package ui;

import java.awt.*;
import javax.swing.*;

/**
 *
 * @author Manuel René Pauls Toews
 */
public final class OuterGui extends JFrame {
    private final JPanel contentPanel;
    private JPanel innerGui;
    private final FooterGui footer;
    private final HeaderGui header;
    private App app;
    public OuterGui(App app) {
        super("Simulación web banking");
        this.app = app;
        this.setSize(1600, 900);
        contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        header = new HeaderGui(app);
        footer = new FooterGui();
        
        innerGui = new JPanel();
        innerGui.setLayout(new BoxLayout(innerGui, BoxLayout.Y_AXIS));
        innerGui.setBackground(Color.decode("#f8f9fa"));
        
        //contentPanel.add(header, BorderLayout.PAGE_START);
        contentPanel.add(footer, BorderLayout.PAGE_END);
        contentPanel.add(header, BorderLayout.PAGE_START);
        contentPanel.add(innerGui, BorderLayout.CENTER);
        //contentPanel.add(new FormularioIniciarSesion(), BorderLayout.CENTER);
        this.add(contentPanel);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }
    
    public void mostrar(JPanel panel) {
        if(panel == null) return;
        
        if(innerGui != null)
            contentPanel.remove(innerGui);
        innerGui = panel;
        contentPanel.add(innerGui, BorderLayout.CENTER);
        this.revalidate();
        this.repaint();
    }
    
    public HeaderGui getHeader() {
        return header;
    }
}