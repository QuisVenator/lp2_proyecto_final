
package logic;

import java.util.Date;

/**
 *
 * @author Manuel René Pauls Toews
 */
public class Log extends DBObject {
    private String tipo, descripcion;
    private Cuenta cuenta;
    private Date hora;
    
    public Log(String tipo, Cuenta cuenta) {
        this(tipo, cuenta, "-");
    }
    public Log(String tipo, Cuenta cuenta, String descripcion) {
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.hora = new Date();
        this.cuenta = cuenta;
    }
    
    @Override
    public int guardar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
