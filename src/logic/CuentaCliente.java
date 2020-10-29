
package logic;

import password_hashing.PasswordStorage;
import password_hashing.PasswordStorage.CannotPerformOperationException;
import password_hashing.PasswordStorage.InvalidHashException;

/**
 *
 * @author Manuel René Pauls Toews
 */
public class CuentaCliente extends Cuenta {
    private double saldo;
    private String pinTransHash;
    
    public CuentaCliente(String transHash, String contrasenhaHash, Cliente titular, int nroCuenta) {
        super(titular, contrasenhaHash, nroCuenta);
        pinTransHash = transHash;
        saldo = 0.0;
    }
    public CuentaCliente(String transHash, Cliente titular, int nroCuenta) {
        super(titular, "", nroCuenta); //este constructor solo debera ser para cuentas leídas y por lo tanto no tiene contrasenha a guardar
    }
    
    public double incrementarSaldo(double monto) {
        //TODO ver si es necesario
        throw new UnsupportedOperationException();
    }
    public double decrementarSaldo(double monto) {
        //TODO ver si es necesario
        throw new UnsupportedOperationException();
    }
    public double setSaldo(double monto) {
        //TODO ver si es necesario
        throw new UnsupportedOperationException();
    }
    public double getSaldo() {
        //TODO implementar comandos base de datos
        throw new UnsupportedOperationException();
    }
    public String getTransHash() {
        return pinTransHash;
    }
    public boolean validarPinTransferencia(String pin) {
        try {
            return PasswordStorage.verifyPassword(pin, pinTransHash);
        } catch(CannotPerformOperationException | InvalidHashException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }
    
    @Override
    public int guardar() {
        //TODO implementar
        throw new UnsupportedOperationException();
    }
}
