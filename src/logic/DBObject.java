
package logic;

/**
 *
 * @author Manuel René Pauls Toews
 */
public abstract class DBObject {
    protected Sesion sesion;
    
    public void setSesion(Sesion sesion) {
        this.sesion = sesion;
    }
    public Sesion getSesion() {
        return sesion;
    }
    public abstract int guardar();
}
