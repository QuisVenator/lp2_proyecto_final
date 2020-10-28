
package logic;

/**
 *
 * @author Manuel René Pauls Toews
 */
public abstract class DBObject {
    protected Sesion sesion;
    
    protected void setConexion(Sesion sesion) {
        this.sesion = sesion;
    }
    protected Sesion getConexion() {
        return sesion;
    }
    public abstract int guardar();
    }
}
