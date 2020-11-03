
package ui;

import java.awt.*;
import javax.swing.*;

/**
 *
 * @author Manuel René Pauls Toews
 */
public final class HeaderGui extends InnerGui {
    public static final int HEADER_VACIO = 0;
    public static final int HEADER_CLIENTE = 1;
    public static final int HEADER_ADMIN = 2;
    
    private JLabel nameLabel, nombre;
    private JButton cerrarSesion, informeBtn, pagarServicioBtn, saldoBtn, transferenciaBtn;
    private JButton agregarServicioBtn, eliminarServicioBtn, desbloquarCuentaBtn, agregarCuentaBtn, eliminarCuentaBtn, depositoBtn;
    private JPanel sesion, menu;
    private JComboBox idiomasSelect;
    private int currentHeader = 0;
    private App app;
    public HeaderGui(App app) {
        this.app = app;
        this.setLayout(new BorderLayout());
        setHeader(HEADER_VACIO);
    }
    
    public void setHeader(int option) {
        currentHeader = option;
        if(sesion != null) this.remove(sesion);
        if(menu != null) this.remove(menu);
        sesion = new JPanel();
        menu = new JPanel();

        menu.setLayout(new FlowLayout(FlowLayout.LEADING));
        nombre = new JLabel("WEB BANKING", new ImageIcon("iconos/bank_32.png"), JLabel.CENTER);
        nombre.setFont(new Font(nombre.getName(), Font.PLAIN, 20));
        menu.add(nombre);  
        switch(option) {
            case HEADER_VACIO:
                cerrarSesion = new JButton(app.getLanguage().getString("iniciarSesion"));
                cerrarSesion.setActionCommand(App.FORMULARIO_INICIO_SESION);
                break;
            case HEADER_ADMIN:
                agregarServicioBtn = new JButton(app.getLanguage().getString("agregarServicio"));
                agregarServicioBtn.setActionCommand(App.FORMULARIO_AGREGAR_SERVICIO);
                agregarServicioBtn.addActionListener(app);
                agregarServicioBtn.setIcon(new ImageIcon("iconos/agregarServicio_16.png"));
                menu.add(agregarServicioBtn);
                eliminarServicioBtn = new JButton(app.getLanguage().getString("eliminarServicio"));
                eliminarServicioBtn.setActionCommand(App.FORMULARIO_ELIMINAR_SERVICIO);
                eliminarServicioBtn.addActionListener(app);
                eliminarServicioBtn.setIcon(new ImageIcon("iconos/borrarServicio_16.png"));
                menu.add(eliminarServicioBtn);
                agregarCuentaBtn = new JButton(app.getLanguage().getString("agregarCuenta"));
                agregarCuentaBtn.setActionCommand(App.FORMULARIO_AGREGAR_CUENTA);
                agregarCuentaBtn.addActionListener(app);
                agregarCuentaBtn.setIcon(new ImageIcon("iconos/agregarUsuario_16.png"));
                menu.add(agregarCuentaBtn);
                eliminarCuentaBtn = new JButton(app.getLanguage().getString("eliminarCuenta"));
                eliminarCuentaBtn.setActionCommand(App.FORMULARIO_ELIMINAR_CUENTA);
                eliminarCuentaBtn.addActionListener(app);
                eliminarCuentaBtn.setIcon(new ImageIcon("iconos/borrarUsuario_16.png"));
                menu.add(eliminarCuentaBtn);
                desbloquarCuentaBtn = new JButton(app.getLanguage().getString("desbloquearCuenta"));
                desbloquarCuentaBtn.setActionCommand(App.FORMULARIO_DESBLOQUEAR_CUENTA);
                desbloquarCuentaBtn.addActionListener(app);
                desbloquarCuentaBtn.setIcon(new ImageIcon("iconos/desbloquear_16.png"));
                menu.add(desbloquarCuentaBtn);
                depositoBtn = new JButton(app.getLanguage().getString("deposito"));
                depositoBtn.setActionCommand(App.FORMULARIO_DEPOSITO);
                depositoBtn.addActionListener(app);
                depositoBtn.setIcon(new ImageIcon("iconos/deposito_16.png"));
                menu.add(depositoBtn);
                
                cerrarSesion = new JButton(app.getLanguage().getString("cerrarSesion"));
                cerrarSesion.setActionCommand(App.CERRAR_SESION);
                break;
            case HEADER_CLIENTE:
                informeBtn = new JButton(app.getLanguage().getString("informe"));
                informeBtn.setIcon(new ImageIcon("iconos/informe_16.png"));
                menu.add(informeBtn);
                pagarServicioBtn = new JButton(app.getLanguage().getString("pagarServicio"));
                pagarServicioBtn.setActionCommand(App.FORMULARIO_PAGAR_SERVICIO);
                pagarServicioBtn.addActionListener(app);
                pagarServicioBtn.setIcon(new ImageIcon("iconos/pagar_servicio_16.png"));
                menu.add(pagarServicioBtn);
                saldoBtn = new JButton(app.getLanguage().getString("saldo"));
                saldoBtn.setActionCommand(App.GUI_SALDO);
                saldoBtn.addActionListener(app);
                saldoBtn.setIcon(new ImageIcon("iconos/saldo_16.png"));
                menu.add(saldoBtn);
                transferenciaBtn = new JButton(app.getLanguage().getString("transferencia"));
                transferenciaBtn.setActionCommand(App.FORMULARIO_TRANSFERENCIA);
                transferenciaBtn.addActionListener(app);
                transferenciaBtn.setIcon(new ImageIcon("iconos/transferencia_16.png"));
                menu.add(transferenciaBtn);
                
                nameLabel = new JLabel();
                nameLabel.setText("Cuenta: "+app.sesion.getCuenta().getNroCuenta());
                cerrarSesion = new JButton(app.getLanguage().getString("cerrarSesion"));
                cerrarSesion.setActionCommand(App.CERRAR_SESION);
                sesion.add(nameLabel);
                JButton tutorialBtn = new JButton(app.getLanguage().getString("ayuda"));
                tutorialBtn.setActionCommand(App.TUTORIAL);
                tutorialBtn.addActionListener(app);
                tutorialBtn.setIcon(new ImageIcon("iconos/tutorial_16.png"));
                sesion.add(tutorialBtn);
                
                break;
        }
        cerrarSesion.setIcon(new ImageIcon("iconos/login_16.png"));
        cerrarSesion.addActionListener(app);
        sesion.setLayout(new FlowLayout(FlowLayout.TRAILING));
        sesion.add(cerrarSesion);
        
        idiomasSelect = new JComboBox(app.getIdiomasDisponibles());
        for(int i = 0; i < app.getIdiomasDisponibles().length; i++) {
            if(app.getIdiomasDisponibles()[i].equals(app.getLanguage().getString("idiomaNombre")))
                idiomasSelect.setSelectedIndex(i);
        }
        idiomasSelect.setActionCommand(App.CAMBIAR_LENGUAJE);
        idiomasSelect.addActionListener(app);
        sesion.add(idiomasSelect);
        this.add(menu, BorderLayout.LINE_START);
        this.add(sesion, BorderLayout.LINE_END);
        this.revalidate();
        this.repaint();
    }        

    @Override
    public void languageReload() {
        setHeader(currentHeader);
    }
}
