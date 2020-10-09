package lp2_proyecto_final;

/**
 *
 * @author Manuel René Pauls Toews
 */
public class Sesion {
    //configura como se generan las contrasenhas
    public static final int SALT_BYTES = 24;
    public static final int HASH_BYTES = 24;
    public static final int PBKDF2_ITERATIONS = 1000;
}
