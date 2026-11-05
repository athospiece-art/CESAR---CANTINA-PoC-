/** A entrada acabou (Ctrl+D / Ctrl+Z): encerra com educação em vez de quebrar. */
public class EntradaEncerradaException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public EntradaEncerradaException() { super("Entrada encerrada."); }
}
