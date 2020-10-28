
package logic;

/**
 *
 * @author Manuel René Pauls Toews
 */
public abstract class Cuenta extends DBObject {
    private int nrCuenta;
    private Persona titular;
    private String contrasenhaHash;
    private int estado;
    
    public Cuenta(Persona titular, String contrasenha, int nroCuenta) {
        this.nrCuenta = nrCuenta;
        this.contrasenhaHash = contrasenha;
        this.titular = titular;
    }
    public String getAccHash() {
        return contrasenhaHash;
    }
    public Persona getTitular() {
        return titular;
    }
    public int getEstado() {
        return estado;
    }
    public int setEstado(int nuevoEstado) {
        estado = nuevoEstado;
        return estado;
    }
    public int getNroCuenta() {
        return nrCuenta;
    }
    
    @Override
    public int guardar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
