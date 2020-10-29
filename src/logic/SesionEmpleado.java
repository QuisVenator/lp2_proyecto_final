
package logic;

import java.awt.event.ActionEvent;

/**
 *
 * @author Manuel René Pauls Toews
 */
public class SesionEmpleado extends Sesion {

    
    @Override
    protected void verificarTiempoSesion() {
        if(viva && tiempoCreacion.getTime() - System.currentTimeMillis() > SistemaSeguridad.T_MAX_SESION_CLIENTE) {
            destruirSesion();
        }
    }
}
