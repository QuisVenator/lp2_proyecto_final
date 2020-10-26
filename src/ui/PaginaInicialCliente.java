
package ui;

import java.awt.*;
import java.text.MessageFormat;
import javax.swing.*;

/**
 *
 * @author Manuel René Pauls Toews
 */
public final class PaginaInicialCliente extends JPanel {
    private final JPanel[] lineas = new JPanel[8];
    private String nombre = "Jane Doe";
    private JLabel titulo, titulo2;
    private JButton transferenciaBtn, pagarServicioBtn, informeBtn, saldoBtn;
    private final App app;
    
    public PaginaInicialCliente(App app) {
        this.app = app;
        
        //preparar lineas
        for(int i = 0; i < lineas.length; i++) {
            lineas[i] = new JPanel();
            lineas[i].setBackground(Color.decode("#f8f8ff"));
        }
        for(int i = 1; i < lineas.length-1; i++) 
            lineas[i].setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        
        //cuando se tienen que pasar variables como nombre a la cadena disponible en varios
        //formatos usamos MessageFormat, porque el orden puede variar por lenguaje
        titulo = new JLabel(MessageFormat.format(app.getLanguage().getString("bienvenidoNombre"), "NOMBRE"), JLabel.CENTER);
        titulo.setFont(new Font(titulo.getName(), Font.PLAIN, 36));
        lineas[1].add(titulo);
        titulo2 = new JLabel(app.getLanguage().getString("queDeseaHacer"), JLabel.CENTER);
        titulo2.setFont(new Font(titulo2.getName(), Font.PLAIN, 30));
        lineas[2].add(titulo2);
        informeBtn = new JButton(app.getLanguage().getString("informe"));
        informeBtn.setPreferredSize(new Dimension(400, 100));
        informeBtn.setFont(new Font(titulo.getName(), Font.PLAIN, 30));
        informeBtn.setIcon(new ImageIcon("iconos/informe_32.png"));
        lineas[3].add(informeBtn);
        pagarServicioBtn = new JButton(app.getLanguage().getString("pagarServicio"));
        pagarServicioBtn.setPreferredSize(new Dimension(400, 100));
        pagarServicioBtn.setFont(new Font(titulo.getName(), Font.PLAIN, 30));
        pagarServicioBtn.setActionCommand(App.FORMULARIO_PAGAR_SERVICIO);
        pagarServicioBtn.addActionListener(app);
        pagarServicioBtn.setIcon(new ImageIcon("iconos/pagar_servicio_32.png"));
        lineas[4].add(pagarServicioBtn);
        saldoBtn = new JButton(app.getLanguage().getString("saldo"));
        saldoBtn.setPreferredSize(new Dimension(400, 100));
        saldoBtn.setFont(new Font(titulo.getName(), Font.PLAIN, 30));
        saldoBtn.setActionCommand(App.GUI_SALDO);
        saldoBtn.addActionListener(app);
        saldoBtn.setIcon(new ImageIcon("iconos/saldo_32.png"));
        lineas[5].add(saldoBtn);
        transferenciaBtn = new JButton(app.getLanguage().getString("transferencia"));
        transferenciaBtn.setPreferredSize(new Dimension(400, 100));
        transferenciaBtn.setFont(new Font(titulo.getName(), Font.PLAIN, 30));
        transferenciaBtn.setActionCommand(App.FORMULARIO_TRANSFERENCIA);
        transferenciaBtn.addActionListener(app);
        transferenciaBtn.setIcon(new ImageIcon("iconos/transferencia_32.png"));
        lineas[6].add(transferenciaBtn);
        
        //a cada linea agregar elementos necesarios
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        //agregar lineas a ventana
        for(int i = 0; i < lineas.length; i++) 
            this.add(lineas[i]);
    }
}
