
package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import logic.Servicio;
import logic.SesionCliente;
import logic.excepciones.SesionExpiradaException;

/**
 *
 * @author Manuel René Pauls Toews
 */
public final class FormularioPagarServicio extends InnerGui {   
    private final List<JPanel> lineas = new LinkedList<>();
    private final List<JButton> servicioBtn = new LinkedList<>();
    private List<Servicio> serviciosDisponibles;
    private final String[] path = {"servicio_ejemplo1_32.png", "servicio_ejemplo2_32.png", "servicio_ejemplo3_32.png"};
    private final String[] nombre = {"Servicio 1", "Servicio 2", "Servicio 3"};
    private final String[] monto = {"100.000", "20.000", "7.000"};
    private JLabel titulo;
    
    private final App app;
    
    public FormularioPagarServicio(App app) {
        this.app = app;
        
        try {
            serviciosDisponibles = ((SesionCliente)app.sesion).obtenerListaServicio();
        } catch (SesionExpiradaException ex) {
            app.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, App.CERRAR_SESION));
            return;
        }
        
        //preparar lineas
        lineas.add(new JPanel());
        lineas.get(0).setBackground(Color.decode("#f8f8ff"));
        lineas.add(new JPanel());
        lineas.get(1).setBackground(Color.decode("#f8f8ff"));
        
        titulo = new JLabel(app.getLanguage().getString("serviciosDisponibles"));
        titulo.setFont(new Font(titulo.getName(), Font.PLAIN, 32));
        titulo.setPreferredSize(new Dimension(350, 100));
        lineas.get(1).setBackground(Color.decode("#f8f8ff"));
        lineas.get(1).setMaximumSize(new Dimension(1920, 80));
        lineas.get(1).add(titulo);
        
        for(Servicio servicio : serviciosDisponibles) {
            JButton btn = new JButton(String.format("%10s%-10s - %-15s", "", servicio.getMonto(), servicio.getNombre()));
            btn.setIcon(new ImageIcon("iconos/"+servicio.getIconoPath()));
            btn.setContentAreaFilled(false);
            btn.setFont(new Font("Courier New", Font.PLAIN, 24));
            btn.setHorizontalAlignment(SwingConstants.LEFT);
            btn.setPreferredSize(new Dimension(600, 40));
            btn.addActionListener((ActionEvent e) -> {
                try {
                    ((SesionCliente)app.sesion).pagarServicio(
                            servicio, 
                            servicio.getMonto(), 
                            Mensaje.crearMensajeInput("pinTransferencia", "pinTransferencia")
                    );
                } catch (SesionExpiradaException ex) {
                    app.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, App.CERRAR_SESION));
                }
            });
            servicioBtn.add(btn);
            
            JPanel linea = new JPanel();
            linea.add(btn);
            linea.setBackground(Color.decode("#f8f8ff"));
            linea.setMaximumSize(new Dimension(1920, 80));
            lineas.add(linea);
        }
        JPanel espacioAbajo = new JPanel();
        espacioAbajo.setBackground(Color.decode("#f8f8ff"));
        lineas.add(espacioAbajo);
        
        //agregar lineas a ventana
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        for(JPanel linea : lineas)
            this.add(linea);
    }

    @Override
    public void languageReload() {
        titulo.setText(app.getLanguage().getString("serviciosDisponibles"));
    }
}
