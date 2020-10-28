
package logic;

/**
 *
 * @author Manuel René Pauls Toews
 */
public class Empleado extends Persona {
    private int nivAcceso;
    
    public Empleado(int nivel, String nombre, String apellido, int ci) {
        super(nombre, apellido, ci);
    }
    
    public int getNivelAcceso() {
        return nivAcceso;
    }
    public void setNivelAcceso(int nivel) {
        if(nivel >= nivAcceso) return; //no se permite inrementar nivel de acceso, en estos casos se debe crear nueva cuenta
        if(nivel <= 0) return; //no existen niveles negativos y no se permite cambiar admin a nivel cliente
        nivAcceso = nivel;
    }
}
