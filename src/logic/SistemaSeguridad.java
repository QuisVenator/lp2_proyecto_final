
package logic;

import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Manuel René Pauls Toews
 */
public class SistemaSeguridad {
    //TODO inicializar con programa
    public static int T_MAX_SESION_CLIENTE;
    public static int T_MAX_SESION_EMPLEADO;
    
    private static HashMap<String, Sesion> sesiones;
    
    public static HashMap<String, Sesion> getSesiones() {
        if(sesiones == null) sesiones = new HashMap<>();
        return sesiones;
    }
    public static void agregarSesion(Sesion sesion) {
        if(sesion == null) return;
        getSesiones().put(sesion.toString(), sesion);
    }
    public static void eliminarSesion(Sesion sesion) {
        if(sesion == null) return;
        getSesiones().remove(sesion.toString());
    }
}
