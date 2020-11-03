
package logic;

public abstract class DBObject {
    protected Sesion sesion;
    
    public void setSesion(Sesion sesion) {
        this.sesion = sesion;
    }
    public Sesion getSesion() {
        return sesion;
    }
    /**
     * Guarda objeto en base de datos.
     * @return 0 en caso de éxito, diferentes códigos de error en caso contrario
     */
    public abstract int guardar();
}
