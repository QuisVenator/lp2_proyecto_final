
package ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;

/**
 *
 * @author Manuel René Pauls Toews
 */
public final class HeaderGui extends JPanel {
    private String name;
    private JLabel nameLabel;
    private JButton cerrarSesion;
    private JPanel sesion, menu;
    private App app;
    public HeaderGui(App app) {
        this.app = app;
        sesion = new JPanel();
        menu = new JPanel();
        this.setLayout(new BorderLayout());
        
        menu.setLayout(new FlowLayout(FlowLayout.LEADING));
        JLabel nombre = new JLabel("WEB BANKING", new ImageIcon("iconos/bank_32.png"), JLabel.CENTER);
        nombre.setFont(new Font(nombre.getName(), Font.PLAIN, 20));
        menu.add(nombre);
        JButton informeBtn = new JButton("Informe");
        informeBtn.setIcon(new ImageIcon("iconos/informe_16.png"));
        menu.add(informeBtn);
        JButton pagarServicioBtn = new JButton("Pagar Servicio");
        pagarServicioBtn.setActionCommand(App.FORMULARIO_PAGAR_SERVICIO);
        pagarServicioBtn.addActionListener(app);
        pagarServicioBtn.setIcon(new ImageIcon("iconos/pagar_servicio_16.png"));
        menu.add(pagarServicioBtn);
        menu.add(informeBtn);
        JButton saldoBtn = new JButton("Saldo");
        saldoBtn.setActionCommand(App.GUI_SALDO);
        saldoBtn.addActionListener(app);
        saldoBtn.setIcon(new ImageIcon("iconos/saldo_16.png"));
        menu.add(saldoBtn);
        menu.add(informeBtn);
        JButton transferenciaBtn = new JButton("Transferencia");
        transferenciaBtn.setActionCommand(App.FORMULARIO_TRANSFERENCIA);
        transferenciaBtn.addActionListener(app);
        transferenciaBtn.setIcon(new ImageIcon("iconos/transferencia_16.png"));
        menu.add(transferenciaBtn);
        //menu.setBackground(Color.decode("#295fa6"));
                
        //sesion.setBackground(Color.decode("#295fa6"));
        nameLabel = new JLabel();
        nameLabel.setText("NR CUENTA");
        cerrarSesion = new JButton("Cerrar Sesión");
        cerrarSesion.setIcon(new ImageIcon("iconos/login_16.png"));
        cerrarSesion.setActionCommand(App.FORMULARIO_INICIO_SESION);
        cerrarSesion.addActionListener(app);
        sesion.setLayout(new FlowLayout(FlowLayout.TRAILING));
        sesion.add(nameLabel);
        JButton tutorialBtn = new JButton("Tutorial");
        tutorialBtn.setIcon(new ImageIcon("iconos/tutorial_16.png"));
        sesion.add(tutorialBtn);
        
        sesion.add(cerrarSesion);
        this.add(menu, BorderLayout.LINE_START);
        this.add(sesion, BorderLayout.LINE_END);
    }
    
    public void toggleSesionBtn() {
        if(cerrarSesion.getActionCommand().equals(App.FORMULARIO_INICIO_SESION)) {
            cerrarSesion.setText("Cerrar Sesion");
            cerrarSesion.setActionCommand(App.CERRAR_SESION);
        } else {
            cerrarSesion.setText("Iniciar Sesion");
            cerrarSesion.setActionCommand(App.CERRAR_SESION);
        }
    }
}
