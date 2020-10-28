
package logic;

/**
 *
 * @author Manuel René Pauls Toews
 */
public class Cliente extends Persona {
    private int numCliente;
    
    public Cliente(int num, String nombre, String apellido, int ci) {
        super(nombre, apellido, ci);
    }
    public int getNumCliente() {
        return numCliente;
    }
}
