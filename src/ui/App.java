package ui;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ListResourceBundle;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import logic.Cuenta;
import logic.CuentaEmpleado;
import logic.Empleado;
import logic.Sesion;
import logic.SesionEmpleado;
import logic.excepciones.AuthentificationException;

/**
 *
 * @author Manuel René Pauls Toews
 */
public class App implements ActionListener {
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
    
    private final OuterGui outer;
    private ResourceBundle languages;
    private final String[] idiomasDisponibles = {"Español", "English", "Deutsch"};
    private final String[] languageTag = {"es", "en", "de"};
    protected Sesion sesion;
    
    public App() {
        outer = new OuterGui(this);
    }
    
    public ResourceBundle getLanguage() {
        if(languages == null) {
            languages = ListResourceBundle.getBundle("idiomas.language");
        }
        
        return languages;
    }
    private void setLanguage(int languageTagIndex) {
        languages = ListResourceBundle.getBundle("idiomas.language", Locale.forLanguageTag(languageTag[languageTagIndex]));
    }
    public String[] getIdiomasDisponibles() {
        return idiomasDisponibles;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()) {
            case CERRAR_SESION:
                sesion.destruirSesion();
            case FORMULARIO_INICIO_SESION:
                outer.mostrarContenido(new FormularioIniciarSesion(this));
                outer.getHeader().setHeader(HeaderGui.HEADER_VACIO);
                break;
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
            case CAMBIAR_LENGUAJE:
                if(!languages.getString("idiomaNombre").equals(((JComboBox)e.getSource()).getSelectedItem())) {
                    setLanguage(((JComboBox)e.getSource()).getSelectedIndex());
                    outer.changeLanguage();
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
        } catch(NumberFormatException | AuthentificationException e) {
            Mensaje.crearMensajeError("loginNoExitoso", "loginErrorMessage");
        }
    }
}
