
package ui;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.Border;

/**
 *
 * @author Manuel René Pauls Toews
 */
public final class FormularioPagarServicio extends InnerGui {   
    private final JPanel[] lineas = new JPanel[6];
    private final JButton[] servicioBtn = new JButton[3];
    private final String[] path = {"servicio_ejemplo1_32.png", "servicio_ejemplo2_32.png", "servicio_ejemplo3_32.png"};
    private final String[] nombre = {"Servicio 1", "Servicio 2", "Servicio 3"};
    private final String[] monto = {"100.000", "20.000", "7.000"};
    private final JLabel titulo;
    
    private final App app;
    
    public FormularioPagarServicio(App app) {
        this.app = app;
        
        for(int i = 0; i < servicioBtn.length; i++) {
            servicioBtn[i] = new JButton(String.format("%10s%-10s - %-15s", "", monto[i], nombre[i]));
            servicioBtn[i].setIcon(new ImageIcon("iconos/"+path[i]));
            servicioBtn[i].setContentAreaFilled(false);
            servicioBtn[i].setFont(new Font("Courier New", Font.PLAIN, 24));
            servicioBtn[i].setHorizontalAlignment(SwingConstants.LEFT);
            servicioBtn[i].setPreferredSize(new Dimension(600, 40));
        }
        //preparar lineas
        for(int i = 0; i < lineas.length; i++) {
            lineas[i] = new JPanel();
            if(i > 1 && i <= nombre.length+1) {
                //lineas[i].setLayout(new FlowLayout(FlowLayout.LEADING));
                lineas[i].add(servicioBtn[i-2]);
            }
            lineas[i].setBackground(Color.decode("#f8f8ff"));
        }
        titulo = new JLabel(app.getLanguage().getString("serviciosDisponibles"));
        titulo.setFont(new Font(titulo.getName(), Font.PLAIN, 32));
        titulo.setPreferredSize(new Dimension(350, 100));
        lineas[1].add(titulo);
        for(int i = 1; i < lineas.length-1; i++) 
            lineas[i].setMaximumSize(new Dimension(1920, 80));
        
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
