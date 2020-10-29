
package logic;

/**
 *
 * @author Manuel René Pauls Toews
 */
public class CuentaEmpleado extends Cuenta {
    public CuentaEmpleado(Empleado titular, String contrasenha, int nroCuenta) {
        super(titular, contrasenha, nroCuenta);
    }
    public CuentaEmpleado(Empleado titular, int nroCuenta) {
        super(titular, "", nroCuenta); //este constructor solo debera ser para cuentas leídas y por lo tanto no tiene contrasenha a guardar
    }
}
