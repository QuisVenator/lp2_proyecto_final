package primer_final.idiomas.ui;


import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ListResourceBundle;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import primer_final.logic.Cuenta;
import primer_final.logic.CuentaEmpleado;
import primer_final.logic.Empleado;
import primer_final.logic.Sesion;
import primer_final.logic.SesionCliente;
import primer_final.logic.SesionEmpleado;
import primer_final.logic.excepciones.AuthentificationException;
import primer_final.logic.excepciones.BlockedAccountException;
import primer_final.logic.excepciones.SesionExpiradaException;

public class App implements ActionListener {
    //constatentes de acciones
    public static final String FORMULARIO_INICIO_SESION = "incicio sesion";
    public static final String FORMULARIO_PAGAR_SERVICIO = "pago servicio";
    public static final String FORMULARIO_TRANSFERENCIA = "transferencia";
    public static final String FORMULARIO_DEPOSITO = "deposito";
    public static final String FORMULARIO_ELIMINAR_CUENTA = "eliminar cuenta";
    public static final String FORMULARIO_AGREGAR_CUENTA = "agregar cuenta";
    public static final String FORMULARIO_ELIMINAR_SERVICIO = "eliminar servicio";
    public static final String FORMULARIO_AGREGAR_SERVICIO = "agregar servicio";
    public static final String FORMULARIO_DESBLOQUEAR_CUENTA = "desbloquear cuenta";
    public static final String GUI_SALDO = "gui saldo";
    public static final String CERRAR_SESION = "cerrar sesion";
    public static final String CAMBIAR_LENGUAJE = "cambiar lenguaje";
    public static final String TUTORIAL = "mostrar tutorial";
    public static final String REPORTE = "generar reporte";
    
    private final OuterGui outer;
    private ResourceBundle languages;
    private final String[] idiomasDisponibles = {"Español", "English", "Deutsch"};
    private final String[] languageTag = {"es", "en", "de"};
    protected Sesion sesion;
    
    public App() {
        outer = new OuterGui(this);
    }
    
    /**
     * Retorna idioma utilizado.<br>
     * Detecta idioma de sistema si no esta configurado todavía y lo intenta configurar. Si no es soportado usa español
     * @return 
     */
    public ResourceBundle getLanguage() {
        if(languages == null) {
            languages = ListResourceBundle.getBundle("primer_final.idiomas.language");
        }
        
        return languages;
    }
    private void setLanguage(int languageTagIndex) {
        languages = ListResourceBundle.getBundle("primer_final.idiomas.language", Locale.forLanguageTag(languageTag[languageTagIndex]));
    }
    public String[] getIdiomasDisponibles() {
        return idiomasDisponibles;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()) {
            case CERRAR_SESION:
                sesion.destruirSesion();
                sesion = null;
            case FORMULARIO_INICIO_SESION:
                outer.mostrarContenido(new FormularioIniciarSesion(this));
                outer.getHeader().setHeader(HeaderGui.HEADER_VACIO);
                break;
            case CAMBIAR_LENGUAJE:
                if(!languages.getString("idiomaNombre").equals(((JComboBox)e.getSource()).getSelectedItem())) {
                    setLanguage(((JComboBox)e.getSource()).getSelectedIndex());
                    outer.changeLanguage();
                }
                break;
        }
        
        if(sesion != null && !sesion.marcarActividad()){ //marcar actividad para que no se cierre la sesión por inactividad y verificar si no se cerró antes
            actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, App.CERRAR_SESION));
            return;
        }
        switch(e.getActionCommand()) {
            case FORMULARIO_PAGAR_SERVICIO:
                outer.mostrarContenido(new FormularioPagarServicio(this));
                break;
            case FORMULARIO_TRANSFERENCIA:
                outer.mostrarContenido(new FormularioTransferencia(this));
                break;
            case GUI_SALDO:
                outer.mostrarContenido(new GuiSaldo(this));
                break;
            case FORMULARIO_DEPOSITO:
                outer.mostrarContenido(new FormularioDeposito(this));
                break;
            case FORMULARIO_ELIMINAR_CUENTA:
                outer.mostrarContenido(new FormularioEliminarCuenta(this));
                break;
            case FORMULARIO_AGREGAR_CUENTA:
                outer.mostrarContenido(new FormularioAgregarCuenta(this));
                break;
            case FORMULARIO_ELIMINAR_SERVICIO:
                outer.mostrarContenido(new FormularioEliminarServicio(this));
                break;
            case FORMULARIO_AGREGAR_SERVICIO:
                outer.mostrarContenido(new FormularioAgregarServicio(this));
                break;
            case FORMULARIO_DESBLOQUEAR_CUENTA:
                outer.mostrarContenido(new FormularioDesbloquearCuenta(this));
                break;
            case TUTORIAL:
                if (Desktop.isDesktopSupported()) {
                    try {
                        File myFile = new File("./tutorial/"+getLanguage().getString("tutorialArchivo"));
                        Desktop.getDesktop().open(myFile);
                    } catch (IOException ex) {
                        Mensaje.crearMensajeError("pdfOpenErrorTitulo", "pdfOpenError");
                    }
                }
                break;
            case REPORTE:
                Reporte report = new Reporte(this);
                try {
                    ((SesionCliente)sesion).generarReporte(report);
                }  catch (SesionExpiradaException ex) {
                    actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, App.CERRAR_SESION));
                    return;
                }
                if (Desktop.isDesktopSupported()) {
                    try {
                        Desktop.getDesktop().open(report.file);
                    } catch (IOException ex) {
                        Mensaje.crearMensajeError("pdfOpenErrorTitulo", "pdfOpenError");
                    }
                }
                break;
        }
    }

    public void login(String cuenta, String password) {
        try {
            sesion = Sesion.iniciarSesion(password, Integer.parseInt(cuenta));
            if(sesion instanceof SesionEmpleado) {
                outer.getHeader().setHeader(HeaderGui.HEADER_ADMIN);
                outer.mostrarContenido(null);
            } else {
                outer.mostrarContenido(new PaginaInicialCliente(this));
                outer.getHeader().setHeader(HeaderGui.HEADER_CLIENTE);
            }
        } catch(BlockedAccountException e) {
            Mensaje.crearMensajeError("loginNoExitoso", "cuentaBloqueadaError");
        } catch(NumberFormatException | AuthentificationException e) {
            Mensaje.crearMensajeError("loginNoExitoso", "loginErrorMessage");
        }
    }
}