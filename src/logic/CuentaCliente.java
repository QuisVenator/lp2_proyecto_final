
package logic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import password_hashing.PasswordStorage;
import password_hashing.PasswordStorage.CannotPerformOperationException;
import password_hashing.PasswordStorage.InvalidHashException;
import ui.Mensaje;

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
        pinTransHash = transHash;
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
        return saldo = monto;
    }
    public double getSaldo() {
        return saldo;
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
        ConexionDB dbc = getSesion().getConexion();
        Connection conn = dbc.getConnection();
        String queryString = "INSERT INTO Cuenta (titular, contrasenha, pinTransferencia, saldo) VALUES (?, ?, ?, ?);";
        try(PreparedStatement stmt = conn.prepareStatement(queryString)) {
            stmt.setString(2, this.getAccHash());
            stmt.setInt(1, this.getTitular().getCI());
            stmt.setString(3, pinTransHash);
            stmt.setDouble(4, 0.0);
            stmt.execute();
            return 0;
        } catch(SQLException e) {
            Mensaje.crearMensajeError("dbErrorTitulo", "dbErrorMensaje");
            return -1;
        }
    }
}
