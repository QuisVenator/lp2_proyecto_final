
package logic;

/**
 *
 * @author Manuel René Pauls Toews
 */
public class Servicio extends DBObject {
    private String nombre, descripcion, iconoPath;
    private int cuentaNr;
    
    public Servicio(int cuentaNr) {
        
    }
    public Servicio(int cuentaNr, String nombre) {
        
    }
    public Servicio(int cuentaNr, String nombre, String descripcion) {
        
    }
    public Servicio(int cuentaNr, String nombre, String descripcion, String iconoPath) {
        
    }
    
    public String getNombre() {
        return nombre;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public int getCuentaNr() {
        return cuentaNr;
    }
    public void setIconoLoc(String location) {
        iconoPath = location;
    }
    public int pagar(double monto) {
        
    }
    
    @Override
    public int guardar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
