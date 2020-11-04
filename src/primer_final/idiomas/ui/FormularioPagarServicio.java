
package primer_final.idiomas.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.util.LinkedList;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import primer_final.logic.Servicio;
import primer_final.logic.SesionCliente;
import primer_final.logic.excepciones.SesionExpiradaException;

public final class FormularioPagarServicio extends InnerGui {   
    private final List<JPanel> lineas = new LinkedList<>();
    private List<Servicio> serviciosDisponibles;
    private JLabel titulo;
    
    private final App app;
    
    public FormularioPagarServicio(App app) {
        this.app = app;
        
        //traer servicios de base de datos
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
        
        //para cada servicio crear un elemento en ventana y darle funcionalidad
        for(Servicio servicio : serviciosDisponibles) {
            JButton btn = new JButton(String.format("%10s%-10s - %-15s", "", servicio.getMonto(), servicio.getNombre()));
            btn.setIcon(new ImageIcon("iconos/"+servicio.getIconoPath()));
            btn.setContentAreaFilled(false);
            btn.setFont(new Font("Courier New", Font.PLAIN, 24));
            btn.setHorizontalAlignment(SwingConstants.LEFT);
            btn.setPreferredSize(new Dimension(600, 40));
            btn.addActionListener((ActionEvent e) -> {
                String pin = Mensaje.crearMensajeInput("pinTransferencia", "pinTransferencia");
                if(pin == null) return;
                try {
                    ((SesionCliente)app.sesion).pagarServicio(
                            servicio,
                            pin
                    );
                } catch (SesionExpiradaException ex) {
                    app.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, App.CERRAR_SESION));
                }
            });
            
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
