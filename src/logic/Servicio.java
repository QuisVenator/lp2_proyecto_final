
package logic;

import java.util.Random;

/**
 *
 * @author Manuel René Pauls Toews
 */
public class Servicio extends DBObject {
    private String nombre, descripcion, iconoPath;
    private int cuentaNr;
    private double monto;
    
    public Servicio(int cuentaNr, double monto) {
        this(cuentaNr, monto, "Servicio sin nombre "+(new Random()).nextLong());
    }
    public Servicio(int cuentaNr, double monto, String nombre) {
        this(cuentaNr, monto, nombre, "-");
    }
    public Servicio(int cuentaNr, double monto, String nombre, String descripcion) {
        this(cuentaNr, monto, nombre, descripcion, "default");
    }
    public Servicio(int cuentaNr, double monto, String nombre, String descripcion, String iconoPath) {
        if(monto < 0) throw new IllegalArgumentException("Monto negativo no permitido!");
        this.cuentaNr = cuentaNr;
        this.monto = monto;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.iconoPath = iconoPath;
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
    public double getMonto() {
        return monto;
    }
    public int pagar(Cuenta cuenta) {
        //TODO implementar
        throw new UnsupportedOperationException();
    }
    
    @Override
    public int guardar() {
        //TODO implementar parte de base de datos (ver table generada de prueba)
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
