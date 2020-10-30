
package logic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import ui.Mensaje;

/**
 *
 * @author Manuel René Pauls Toews
 */
public final class Transferencia extends DBObject {
    public static final int ENTRE_CUENTAS = 1;
    public static final int SERVICIO = 2;
    
    private CuentaCliente cuentaReceptora, cuentaEmisora;
    private double monto;
    private int tipo;
    
    public Transferencia(CuentaCliente envia, CuentaCliente recibe, int tipo) {
        cuentaEmisora = envia;
        cuentaReceptora = recibe;
        this.tipo = tipo;
    }
    public Transferencia(CuentaCliente envia, CuentaCliente recibe, int tipo, double monto) {
        this(envia, recibe, tipo);
        setMonto(monto);
    }
    
    public int setMonto(double monto) {
        if(monto < 0) return -1;
        else {
            this.monto = monto;
            return 0;
        }
    }
    
    /**
     * Este metodo verifica si el saldo dado en cuenta es suficiente para hacer la transferencia.<br>
     * Como el saldo en base de datos podría haber cambiado despues de crear este objeto, no garantiza que
     * la transacción no falla.
     * @return true si fue posible hacer la transferencia en el momento que se creo el objeto
     */
    public boolean posible() {
        return cuentaEmisora.getSaldo() >= monto;
    }
    
    /**
     * Efectua la transferencia si es posible.<br>
     * Usa transacciones SQL para garantizar que nunca se crea ni se borra saldo y solo es movido.<br>
     * NO guarda la transferencia en si en la base de datos. Ver <code>guardar</code> para eso.
     * @return 0 si fue exitoso, -2 si no fue posible por saldo en objeto cuenta dada y -1 si falló la transacción
     */
    public int efectuar() {
        if(!posible()) {
            Mensaje.crearMensajeError("saldoInsuficienteTitulo", "saldoInsuficiente");
            return -2;
        }
        
        ConexionDB dbc = getSesion().getConexion();
        Connection conn = dbc.getConnection();
        String queryString = "UPDATE Cuenta SET saldo = saldo - ? WHERE nrCuenta = ?;";
        
        PreparedStatement stmt, stmt2;
        stmt = stmt2 = null;
        try {
            //Usar transacciones ACID
            conn.setAutoCommit(false);
            
            //primero disminuir saldo en una cuenta
            stmt = conn.prepareStatement(queryString);
            stmt.setInt(2, cuentaEmisora.getNroCuenta());
            stmt.setDouble(1, monto);
            int disminuido = stmt.executeUpdate();
            
            //ahora incrementar en la otra
            queryString = "UPDATE Cuenta SET saldo = saldo + ? WHERE nrCuenta = ?;";
            stmt2 = conn.prepareStatement(queryString);
            stmt2.setInt(2, cuentaReceptora.getNroCuenta());
            stmt2.setDouble(1, monto);
            int incrementado = stmt2.executeUpdate();
            
            //si una operacion no hizo nada, algo esta mal y cancelamos todo
            if(disminuido != 1 || incrementado != 1) {
                conn.rollback();
                Mensaje.crearMensajeError("transaccionFallidaTitulo", "transaccionFallida");
                return -1;
            } else { //operaciones exitosas, los guardamos
                conn.commit();
                return 0;
            }
        } catch(SQLException e) {
            Mensaje.crearMensajeError("dbErrorTitulo", "dbErrorMensaje");
            return -1;
        } finally { 
            try {
                if(stmt != null) stmt.close();
                if(stmt2 != null) stmt2.close();
                conn.setAutoCommit(true);
            } catch (SQLException ex) {
                //en caso de que no pudimos restablece autocommit cerramos la sesion
                //asi queremos evitar que se hagan cambios que no se guardan
                getSesion().destruirSesion();
            }
        }
    }

    @Override
    public int guardar() {
        ConexionDB dbc = getSesion().getConexion();
        Connection conn = dbc.getConnection();
        String queryString = "INSERT INTO Transferencia (monto, envia, recibe, tipo) VALUES (?, ?, ?, ?);";
        try(PreparedStatement stmt = conn.prepareStatement(queryString)) {
            stmt.setDouble(1, monto);
            stmt.setInt(2, cuentaEmisora.getNroCuenta());
            stmt.setInt(3, cuentaReceptora.getNroCuenta());
            stmt.setInt(4, tipo);
            stmt.execute();
            return 0;
        } catch(SQLException e) {
            Mensaje.crearMensajeError("dbErrorTitulo", "dbErrorMensaje");
            return -1;
        }
    }

}
