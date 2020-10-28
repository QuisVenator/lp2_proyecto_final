
package logic;

/**
 *
 * @author Manuel René Pauls Toews
 */
public class Persona {
    private String nombre, apellido, correo, direccion, numeroTel;
    private int ci;
    
    public Persona(String nombre, String apellido, int ci) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.ci = ci;
    } 
    
    public String getNombre() {
        return nombre;
    }
    public String getApellido() {
        return apellido;
    }
    public String getNombreCompleto() {
        return nombre + " " + apellido;
    }
    public int getCI() {
        return ci;
    }
    public String getCorreo() {
        return correo;
    }
    public String getNumTel() {
        return numeroTel;
    }
    public String getDireccion() {
        return direccion;
    }
    public void setCorreo(String correo) {
        this.correo = correo;
    }
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    public void setNumTel(String numTel) {
        this.numeroTel = numTel;
    }
    public int guardar() {
        //TODO implementar parte de base de datos (ver table generada de prueba)
        throw new UnsupportedOperationException("Not supported yet.");
    }
}