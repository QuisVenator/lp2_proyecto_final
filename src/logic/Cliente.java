
package logic;

//Sirve para forzar a siempre saber si se estan manejando Clientes o Empleados
public class Cliente extends Persona {
    public Cliente(String nombre, String apellido, int ci) {
        super(nombre, apellido, ci);
    }
}
