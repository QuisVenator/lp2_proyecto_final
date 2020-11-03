
package logic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import password_hashing.PasswordStorage;
import password_hashing.PasswordStorage.CannotPerformOperationException;
import password_hashing.PasswordStorage.InvalidHashException;
import ui.Mensaje;

/**
 * Específicamente para Cuentas de clientes
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
    
    /**
     * Incrementa el saldo de este objeto.<br>
     * NO cambia base de datos
     * @param monto cantidad a agregar
     * @return nuevo saldo en este objeto
     */
    public double incrementarSaldo(double monto) {
        return this.saldo += monto;
    }
    
    /**
     * Decrementa el saldo de este objeto.<br>
     * NO cambia base de datos
     * @param monto cantidad a restar
     * @return nuevo saldo en este objeto
     */
    public double decrementarSaldo(double monto) {
        return this.saldo -= monto;
    }
    public double setSaldo(double monto) {
        return saldo = monto;
    }
    /**
     * Retorna saldo en este objeto, NO se comunica con base de datos.<br>
     * Ver <code>SesionCliente.obtenerSaldo()</code> para obtener de base de datos.
     * @return 
     */
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
