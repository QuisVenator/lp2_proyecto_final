package ui;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 *
 * @author Manuel René Pauls Toews
 */
public final class OuterGui extends JFrame {
    private final JPanel contentPanel;
    private InnerGui innerGui;
    private final FooterGui footer;
    private final HeaderGui header;
    private App app;
    public OuterGui(App app) {
        super(app.getLanguage().getString("tituloVentana"));
        this.app = app;
        this.setSize(1600, 900);
        contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        header = new HeaderGui(app);
        footer = new FooterGui(app);
        
        innerGui = new InnerGui();
        innerGui.setLayout(new BoxLayout(innerGui, BoxLayout.Y_AXIS));
        innerGui.setBackground(Color.decode("#f8f9fa"));
        
        contentPanel.add(footer, BorderLayout.PAGE_END);
        contentPanel.add(header, BorderLayout.PAGE_START);
        contentPanel.add(innerGui, BorderLayout.CENTER);
        
        this.add(contentPanel);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        
        try {
            setIconImage(ImageIO.read(new File("iconos/bank.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.setVisible(true);
    }
    
    public void mostrarContenido(InnerGui panel) {
        if(innerGui != null)
            contentPanel.remove(innerGui);
        
        if(panel != null) {
            innerGui = panel;
            contentPanel.add(innerGui, BorderLayout.CENTER);
        }
        this.revalidate();
        this.repaint();
    }
    
    public HeaderGui getHeader() {
        return header;
    }
    
    public void changeLanguage() {
        footer.languageReload();
        header.languageReload();
        innerGui.languageReload();
    }
}